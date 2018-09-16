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

import io.github.ma1uta.matrix.Secured;
import io.github.ma1uta.matrix.client.model.encryption.ChangesResponse;
import io.github.ma1uta.matrix.client.model.encryption.ClaimRequest;
import io.github.ma1uta.matrix.client.model.encryption.ClaimResponse;
import io.github.ma1uta.matrix.client.model.encryption.QueryRequest;
import io.github.ma1uta.matrix.client.model.encryption.QueryResponse;
import io.github.ma1uta.matrix.client.model.encryption.UploadRequest;
import io.github.ma1uta.matrix.client.model.encryption.UploadResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

/**
 * Matrix optionally supports end-to-end encryption, allowing rooms to be created whose conversation contents is not decryptable or
 * interceptable on any of the participating homeservers.
 */
@Api(
    value = "Encryption",
    description = "Matrix optionally supports end-to-end encryption, allowing rooms to be created whose "
        + "conversation contents is not decryptable or interceptable on any of the participating homeservers."
)
@Path("/_matrix/client/r0/keys")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface EncryptionApi {

    /**
     * Publishes end-to-end encryption keys for the device.
     * <br>
     * <b>Requires auth</b>: Yes.
     * <br>
     * Return: {@link UploadResponse}.
     * <p>Status code 200: The provided keys were sucessfully uploaded.</p>
     *
     * @param uploadRequest   JSON body parameters.
     * @param servletRequest  Servlet request.
     * @param asyncResponse   Asynchronous response.
     * @param securityContext Security context.
     */
    @ApiOperation(
        value = "Publishes end-to-end encryption keys for the device.",
        response = UploadResponse.class
    )
    @ApiResponses( {
        @ApiResponse(code = 200, message = "The provided keys were sucessfully uploaded.")
    })
    @POST
    @Secured
    @Path("/upload")
    void uploadKey(
        @ApiParam(
            value = "JSON body request"
        ) UploadRequest uploadRequest,

        @Context HttpServletRequest servletRequest,
        @Suspended AsyncResponse asyncResponse,
        @Context SecurityContext securityContext
    );

    /**
     * Returns the current devices and identity keys for the given users.
     * <br>
     * <b>Requires auth</b>: Yes.
     * <br>
     * Return: {@link QueryResponse}.
     * <p>Status code 200: The device information.</p>
     *
     * @param queryRequest    JSON body parameters.
     * @param servletRequest  Servlet request.
     * @param asyncResponse   Asynchronous response.
     * @param securityContext Security context.
     */
    @ApiOperation(
        value = "Returns the current devices and identity keys for the given users.",
        response = QueryResponse.class
    )
    @ApiResponses( {
        @ApiResponse(code = 200, message = "The device information.")
    })
    @POST
    @Secured
    @Path("/query")
    void query(
        @ApiParam(
            value = "JSON body request"
        ) QueryRequest queryRequest,

        @Context HttpServletRequest servletRequest,
        @Suspended AsyncResponse asyncResponse,
        @Context SecurityContext securityContext
    );

    /**
     * Claims one-time keys for use in pre-key messages.
     * <br>
     * <b>Requires auth</b>: Yes.
     * <br>
     * Return: {@link ClaimResponse}.
     * <p>Status code 200: The claimed keys.</p>
     *
     * @param claimRequest    JSON body parameters.
     * @param servletRequest  Servlet request.
     * @param asyncResponse   Asynchronous response.
     * @param securityContext Security context.
     */
    @ApiOperation(
        value = "Claims one-time keys for use in pre-key messages.",
        response = ClaimResponse.class
    )
    @ApiResponses( {
        @ApiResponse(code = 200, message = "The claimed keys.")
    })
    @POST
    @Secured
    @Path("/claim")
    void claim(
        @ApiParam(
            value = "JSON body request."
        ) ClaimRequest claimRequest,

        @Context HttpServletRequest servletRequest,
        @Suspended AsyncResponse asyncResponse,
        @Context SecurityContext securityContext
    );

    /**
     * Gets a list of users who have updated their device identity keys since a previous sync token.
     * <br>
     * The server should include in the results any users who:
     * <ul>
     * <li>currently share a room with the calling user (ie, both users have membership state join); and</li>
     * <li>added new device identity keys or removed an existing device with identity keys, between from and to.</li>
     * </ul>
     * <b>Requires auth</b>: Yes.
     * <br>
     * Return: {@link ChangesResponse}.
     * <p>Status code 200: The list of users who updated their devices.</p>
     *
     * @param from            Required. The desired start point of the list. Should be the next_batch field from a response to an earlier
     *                        call to /sync. Users who have not uploaded new device identity keys since this point, nor deleted existing
     *                        devices with identity keys since then, will be excluded from the results.
     * @param to              Required. The desired end point of the list. Should be the next_batch field from a recent call to /sync -
     *                        typically the most recent such call. This may be used by the server as a hint to check its caches are up to
     *                        date.
     * @param servletRequest  Servlet request.
     * @param asyncResponse   Asynchronous response.
     * @param securityContext Security context.
     */
    @ApiOperation(
        value = "Gets a list of users who have updated their device identity keys since a previous sync token.",
        notes = "The server should include in the results any users who currently share a room with the calling user (ie, both "
            + "users have membership state join); and added new device identity keys or removed an existing device with identity "
            + "keys, between from and to.",
        response = ChangesResponse.class
    )
    @ApiResponses( {
        @ApiResponse(code = 200, message = "The list of users who updated their devices.")
    })
    @GET
    @Secured
    @Path("/changes")
    void changes(
        @ApiParam(
            value = "The desired start point of the list. Should be the next_batch field from a response"
                + " to an earlier call to /sync. Users who have not uploaded new device identity keys since this point, nor deleted"
                + " existing devices with identity keys since then, will be excluded from the results.",
            required = true
        ) @QueryParam("from") String from,
        @ApiParam(
            value = "The desired end point of the list. Should be the next_batch field from a recent call "
                + "to /sync - typically the most recent such call. This may be used by the server as a hint to check "
                + "its caches are up to date.",
            required = true
        ) @QueryParam("to") String to,

        @Context HttpServletRequest servletRequest,
        @Suspended AsyncResponse asyncResponse,
        @Context SecurityContext securityContext
    );
}
