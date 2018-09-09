/*
 * Copyright sablintolya@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.ma1uta.matrix.client.api;

import io.github.ma1uta.matrix.RateLimit;
import io.github.ma1uta.matrix.Secured;
import io.github.ma1uta.matrix.client.model.content.ContentConfig;
import io.github.ma1uta.matrix.client.model.content.ContentUri;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * This module allows users to upload content to their homeserver which is retrievable from other homeservers.
 * Its' purpose is to allow users to share attachments in a room. Key locations are represented as Matrix Key (MXC) URIs.
 * They look like:
 * <pre>
 * mxc://(server-name)/(media-id)
 *
 * (server-name) : The name of the homeserver where this content originated, e.g. matrix.org
 * (media-id) : An opaque ID which identifies the content.
 * </pre>
 * Uploads are POSTed to a resource on the user's local homeserver which returns a token which is used to GET the download.
 * Key is downloaded from the recipient's local homeserver, which must first transfer the content from the origin homeserver
 * using the same API (unless the origin and destination homeservers are the same).
 */
@Api(
    value = "Content",
    description = "This module allows users to upload content to their homeserver which is retrievable from other homeservers."
        + " Its' purpose is to allow users to share attachments in a room. Key locations are represented as Matrix Key (MXC) URIs."
)
@Path("/_matrix/media/r0")
public interface ContentApi {

    /**
     * The desired resizing method.
     */
    class Method {

        protected Method() {
        }

        /**
         * Crop.
         */
        public static final String CROP = "crop";

        /**
         * Scale.
         */
        public static final String SCALE = "scale";
    }

    /**
     * Upload some content to the content repository.
     * <br>
     * <b>Rate-limited</b>: Yes.
     * <br>
     * <b>Requires auth</b>: Yes.
     *
     * @param inputStream     The file content.
     * @param filename        The name of the file being uploaded.
     * @param contentType     The content type of the file being uploaded.
     * @param servletRequest  Servlet request.
     * @param servletResponse Servlet response.
     * @param securityContext Security context.
     * @return <b>Required</b>. The MXC URI to the uploaded content.
     * <p>Status code 200: The MXC URI for the uploaded content.</p>
     * <p>Status code 429: This request was rate-limited.</p>
     */
    @ApiOperation(
        value = "Upload some content to the content repository."
    )
    @ApiResponses( {
        @ApiResponse(code = 200, message = "The MXC URI for the uploaded content."),
        @ApiResponse(code = 429, message = "This request was rate-limited.")
    })
    @POST
    @RateLimit
    @Secured
    @Path("/upload")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    ContentUri upload(
        @ApiParam(
            value = "The file content"
        ) InputStream inputStream,
        @ApiParam(
            value = "The name of the file being uploaded."
        ) @QueryParam("filename") String filename,
        @ApiParam(
            value = "The content type of the file being uploaded"
        ) @HeaderParam("Content-Type") String contentType,

        @Context HttpServletRequest servletRequest,
        @Context HttpServletResponse servletResponse,
        @Context SecurityContext securityContext
    );

    /**
     * Download content from the content repository.
     * <br>
     * <b>Rate-limited</b>: Yes.
     *
     * @param serverName      Required. The server name from the mxc:// URI (the authoritory component).
     * @param mediaId         Required. The media ID from the mxc:// URI (the path component).
     * @param allowRemote     Indicates to the server that it should not attempt to fetch the media if it is deemed remote.
     *                        This is to prevent routing loops where the server contacts itself. Defaults to true if not provided.
     * @param servletRequest  Servlet request.
     * @param servletResponse Servlet response.
     * @return <p>Response headers:</p>
     * <table summary="Response header">
     * <tr>
     * <th>Parameter</th>
     * <th>Type</th>
     * <th>Description</th>
     * </tr>
     * <tr>
     * <td>Content-Type</td>
     * <td>string</td>
     * <td>The content type of the file that was previously uploaded.</td>
     * </tr>
     * <tr>
     * <td>Content-Disposition</td>
     * <td>string</td>
     * <td>The name of the file that was previously uploaded, if set.</td>
     * </tr>
     * </table>
     * <p>Status code 200: The content that was previously uploaded.</p>
     * <p>Status code 429: This request was rate-limited.</p>
     */
    @ApiOperation(
        value = "Download content from the content repository."
    )
    @ApiResponses( {
        @ApiResponse(code = 200, message = "The content that was previously uploaded."),
        @ApiResponse(code = 429, message = "This request was rate-limited.")
    })
    @GET
    @RateLimit
    @Path("/download/{serverName}/{mediaId}")
    Response download(
        @ApiParam(
            value = "The server name from the mxc:// URI (the authoritory component).",
            required = true
        ) @PathParam("serverName") String serverName,
        @ApiParam(
            value = "The media ID from the mxc:// URI (the path component).",
            required = true
        ) @PathParam("mediaId") String mediaId,
        @ApiParam(
            value = "Indicates to the server that it should not attempt to fetch the media if it is deemed remote. "
                + "This is to prevent routing loops where the server contacts itself. Defaults to true if not provided."
        ) @QueryParam("allow_remote") Boolean allowRemote,

        @Context HttpServletRequest servletRequest,
        @Context HttpServletResponse servletResponse
    );

    /**
     * Download content from the content repository as a given filename.
     * <br>
     * <b>Rate-limited</b>: Yes.
     *
     * @param serverName      Required. The server name from the mxc:// URI (the authoritory component).
     * @param mediaId         Required. The media ID from the mxc:// URI (the path component).
     * @param filename        Required. The filename to give in the Content-Disposition.
     * @param allowRemote     Indicates to the server that it should not attempt to fetch the media if it is deemed remote.
     *                        This is to prevent routing loops where the server contacts itself. Defaults to true if not provided.
     * @param servletRequest  Servlet request.
     * @param servletResponse Servlet response.
     * @return <p>Response headers:</p>
     * <table summary="Response headers">
     * <tr>
     * <th>Parameter</th>
     * <th>Type</th>
     * <th>Description</th>
     * </tr>
     * <tr>
     * <td>Content-Type</td>
     * <td>string</td>
     * <td>The content type of the file that was previously uploaded.</td>
     * </tr>
     * <tr>
     * <td>Content-Disposition</td>
     * <td>string</td>
     * <td>The name of the file that was previously uploaded, if set.</td>
     * </tr>
     * </table>
     * <p>Status code 200: The content that was previously uploaded.</p>
     * <p>Status code 429: This request was rate-limited.</p>
     */
    @ApiOperation(
        value = "Download content from the content repository as a given filename."
    )
    @ApiResponses( {
        @ApiResponse(code = 200, message = "The content that was previously uploaded."),
        @ApiResponse(code = 429, message = "This request was rate-limited.")
    })
    @GET
    @RateLimit
    @Path("/download/{serverName}/{mediaId}/{fileName}")
    Response downloadFile(
        @ApiParam(
            value = "The server name from the mxc:// URI (the authoritory component).",
            required = true
        ) @PathParam("serverName") String serverName,
        @ApiParam(
            value = "The media ID from the mxc:// URI (the path component).",
            required = true
        ) @PathParam("mediaId") String mediaId,
        @ApiParam(
            value = "The filename to give in the Content-Disposition.",
            required = true
        ) @PathParam("fileName") String filename,
        @ApiParam(
            value = "Indicates to the server that it should not attempt to fetch the media if it is deemed remote. "
                + "This is to prevent routing loops where the server contacts itself. Defaults to true if not provided."
        ) @QueryParam("allow_remote") Boolean allowRemote,

        @Context HttpServletRequest servletRequest,
        @Context HttpServletResponse servletResponse
    );

    /**
     * Download a thumbnail of the content from the content repository.
     * <br>
     * <b>Rate-limited</b>: Yes.
     *
     * @param serverName      Required. The server name from the mxc:// URI (the authoritory component).
     * @param mediaId         Required. The media ID from the mxc:// URI (the path component)
     * @param width           The desired width of the thumbnail. The actual thumbnail may not match the size specified.
     * @param height          The desired height of the thumbnail. The actual thumbnail may not match the size specified.
     * @param method          The desired resizing method. One of: ["crop", "scale"].
     * @param allowRemote     Indicates to the server that it should not attempt to fetch the media if it is deemed remote.
     *                        This is to prevent routing loops where the server contacts itself. Defaults to true if not provided.
     * @param servletRequest  Servlet request.
     * @param servletResponse Servlet response.
     * @return <p>Response headers:</p>
     * <table summary="Response headers">
     * <tr>
     * <th>Parameter</th>
     * <th>Type</th>
     * <th>Description</th>
     * </tr>
     * <tr>
     * <td>Key-Type</td>
     * <td>string</td>
     * <td>The content type of the file that was previously uploaded.</td>
     * </tr>
     * </table>
     * <p>Status code 200: The content that was previously uploaded.</p>
     * <p>Status code 429: This request was rate-limited.</p>
     */
    @ApiOperation(value = "Download a thumbnail of the content from the content repository.", response = OutputStream.class)
    @ApiResponses( {
        @ApiResponse(code = 200, message = "The content that was previously uploaded."),
        @ApiResponse(code = 429, message = "This request was rate-limited.")
    })
    @GET
    @RateLimit
    @Path("/thumbnail/{serverName}/{mediaId}")
    Response thumbnail(@ApiParam(value = "The server name from the mxc:// URI (the authoritory component).", required = true)
                       @PathParam("serverName") String serverName,
                       @ApiParam(value = "The media ID from the mxc:// URI (the path component)", required = true)
                       @PathParam("mediaId") String mediaId,
                       @ApiParam("he desired width of the thumbnail. The actual thumbnail may not match the size specified.")
                       @QueryParam("width") Long width,
                       @ApiParam("The desired height of the thumbnail. The actual thumbnail may not match the size specified.")
                       @QueryParam("height") Long height,
                       @ApiParam(value = "The desired resizing method.", allowableValues = "['crop','scale']")
                       @QueryParam("method") String method,
                       @ApiParam("Indicates to the server that it should not attempt to fetch the media if it is deemed remote. "
                           + "This is to prevent routing loops where the server contacts itself. Defaults to true if not provided.")
                       @QueryParam("allow_remote") Boolean allowRemote,
                       @Context HttpServletRequest servletRequest, @Context HttpServletResponse servletResponse);

    /**
     * Get information about a PATH for a client.
     * <br>
     * <b>Rate-limited</b>: Yes.
     * <br>
     * <b>Requires auth</b>: Yes.
     *
     * @param url             Required. The PATH to get a preview of.
     * @param ts              The preferred point in time to return a preview for. The server may return a newer version if it does not
     *                        have the requested version available.
     * @param servletRequest  Servlet request.
     * @param servletResponse Servlet response.
     * @param securityContext Security context.
     * @return <p>Response headers:</p>
     * <table summary="Response headers">
     * <tr>
     * <th>Parameter</th>
     * <th>Type</th>
     * <th>Description</th>
     * </tr>
     * <tr>
     * <td>matrix:image:size</td>
     * <td>number</td>
     * <td>The byte-size of the image. Omitted if there is no image attached.</td>
     * </tr>
     * <tr>
     * <td>og:image</td>
     * <td>string</td>
     * <td>An MXC URI to the image. Omitted if there is no image.</td>
     * </tr>
     * </table>
     * <p>Status code 200: The content that was previously uploaded.</p>
     * <p>Status code 429: This request was rate-limited.</p>
     */
    @ApiOperation(
        value = "Get information about a PATH for a client."
    )
    @ApiResponses( {
        @ApiResponse(code = 200, message = "The content that was previously uploaded."),
        @ApiResponse(code = 429, message = "This request was rate-limited.")
    })
    @GET
    @RateLimit
    @Secured
    @Path("/preview_url")
    @Produces(MediaType.APPLICATION_JSON)
    Map<String, String> previewUrl(
        @ApiParam(
            value = "The PATH to get a preview of.",
            required = true
        ) @QueryParam("url") String url,
        @ApiParam(
            value = "The preferred point in time to return a preview for. The server may return a newer "
                + "version if it does not have the requested version available."
        ) @QueryParam("ts") String ts,

        @Context HttpServletRequest servletRequest,
        @Context HttpServletResponse servletResponse,
        @Context SecurityContext securityContext
    );

    /**
     * This endpoint allows clients to retrieve the configuration of the content repository, such as upload limitations.
     * Clients SHOULD use this as a guide when using content repository endpoints. All values are intentionally left optional.
     * Clients SHOULD follow the advice given in the field description when the field is not available.
     * <br>
     * NOTE: Both clients and server administrators should be aware that proxies between the client and the server may affect
     * the apparent behaviour of content repository APIs, for example, proxies may enforce a lower upload size limit than is
     * advertised by the server on this endpoint.
     * <br>
     * <b>Rate-limited</b>: Yes.
     * <b>Requires auth</b>: Yes.
     *
     * @param servletRequest  Servlet request.
     * @param servletResponse Servlet response.
     * @param securityContext Security context.
     * @return <p>Status code 200: The public content repository configuration for the matrix server.</p>
     * <p>Status code 429: This request was rate-limited.</p>
     */
    @ApiOperation(
        value = "his endpoint allows clients to retrieve the configuration of the content repository, such"
            + "as upload limitations.",
        notes = "Clients SHOULD use this as a guide when using content repository endpoints. All values are intentionally left optional."
            + " Clients SHOULD follow the advice given in the field description when the field is not available."
            + " NOTE: Both clients and server administrators should be aware that proxies between the client and the server may affect"
            + " the apparent behaviour of content repository APIs, for example, proxies may enforce a lower upload size limit than is"
            + " advertised by the server on this endpoint."
    )
    @ApiResponses( {
        @ApiResponse(code = 200, message = "The public content repository configuration for the matrix server."),
        @ApiResponse(code = 429, message = "This request was rate-limited.")
    })
    @GET
    @RateLimit
    @Secured
    @Path("/config")
    @Produces(MediaType.APPLICATION_JSON)
    ContentConfig config(
        @Context HttpServletRequest servletRequest,
        @Context HttpServletResponse servletResponse,
        @Context SecurityContext securityContext
    );
}
