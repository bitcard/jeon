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

package io.github.ma1uta.matrix.application.api;

import io.github.ma1uta.matrix.Secured;
import io.github.ma1uta.matrix.protocol.Protocol;
import io.github.ma1uta.matrix.protocol.ProtocolLocation;
import io.github.ma1uta.matrix.protocol.ProtocolUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 * Application services may declare which protocols they support via their registration configuration for the homeserver.
 * These networks are generally for third party services such as IRC that the application service is managing.
 * Application services may populate a Matrix room directory for their registered protocols, as defined in the Client-Server API Extensions.
 * <br>
 * Each protocol may have several "locations" (also known as "third party locations" or "3PLs"). A location within a protocol is a place
 * in the third party network, such as an IRC channel. Users of the third party network may also be represented by the application service.
 * <br>
 * Locations and users can be searched by fields defined by the application service, such as by display name or other attribute.
 * When clients request the homeserver to search in a particular "network" (protocol), the search fields will be passed along
 * to the application service for filtering.
 */
@Api(
    value = "ThirdPartyProtocol",
    description = "Application services may declare which protocols they support via their registration configuration for the homeserver."
        + " These networks are generally for third party services such as IRC that the application service is managing."
        + " Application services may populate a Matrix room directory for their registered protocols, as defined in the Client-Server"
        + " API Extensions.\nEach protocol may have several \"locations\"(also known as \"third party locations\" or \"3PLs\")."
        + " A location within a protocol is a place in the third party network, such as an IRC channel.Users of the third party network"
        + " may also be represented by the application service.\nLocations and users can be searched by fields defined by the application"
        + " service, such as by display name or other attribute. When clients request the homeserver to search"
        + " in a particular \"network\" (protocol), the search fields will be passed along to the application service for filtering."
)
@Path("/_matrix/app/v1/thirdparty")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ProtocolApi {

    /**
     * This API is called by the homeserver when it wants to present clients with specific information about the various third party
     * networks that an application service supports.
     * <br>
     * <b>Requires Auth</b>: Yes.
     *
     * @param protocol        Required. The name of the protocol.
     * @param servletRequest  Servlet request.
     * @param servletResponse Servlet response.
     * @return <p>Status code 200: The protocol was found and metadata returned.</p>
     * <p>Status code 401: The homeserver has not supplied credentials to the application service. Optional error information can
     * be included in the body of this response.</p>
     * <p>Status code 403: The credentials supplied by the homeserver were rejected.</p>
     * <p>Status code 404: No protocol was found with the given path.The protocol is unknown.</p>
     */
    @ApiOperation(
        value = "This API is called by the homeserver when it wants to present clients with specific information about the various"
            + " third party networks that an application service supports."
    )
    @ApiResponses( {
        @ApiResponse(code = 200, message = "The protocol was found and metadata returned."),
        @ApiResponse(code = 401, message = "The homeserver has not supplied credentials to the application service."
            + " Optional error information can be included in the body of this response."),
        @ApiResponse(code = 403, message = "The credentials supplied by the homeserver were rejected."),
        @ApiResponse(code = 404, message = "No protocol was found with the given path.The protocol is unknown.")
    })
    @Secured
    @GET
    @Path("/protocol/{protocol}")
    Protocol protocol(
        @ApiParam(
            value = "the name of the protocol",
            required = true
        ) @PathParam("protocol") String protocol,

        @Context HttpServletRequest servletRequest,
        @Context HttpServletResponse servletResponse
    );

    /**
     * Retrieve a list of Matrix portal rooms that lead to the matched third party location.
     * <br>
     * <b>Requires Auth</b>: Yes.
     *
     * @param protocol        Required. The protocol ID.
     * @param uriInfo         One or more custom fields to help identify the third party location.
     * @param servletRequest  Servlet request.
     * @param servletResponse Servlet response.
     * @return <p>Status code 200: At least one portal room was found.</p>
     * <p>Status code 401: The homeserver has not supplied credentials to the application service. Optional error information can
     * be included in the body of this response.</p>
     * <p>Status code 403: The credentials supplied by the homeserver were rejected.</p>
     * <p>Status code 404: No mappings were found with the given parameters.</p>
     */
    @ApiOperation(value = "Retrieve a list of Matrix portal rooms that lead to the matched third party location.")
    @ApiResponses( {
        @ApiResponse(code = 200, message = "At least one portal room was found."),
        @ApiResponse(code = 401, message = "The homeserver has not supplied credentials to the application service."
            + " Optional error information can be included in the body of this response."),
        @ApiResponse(code = 403, message = "The credentials supplied by the homeserver were rejected."),
        @ApiResponse(code = 404, message = "No mappings were found with the given parameters.")
    })
    @Secured
    @GET
    @Path("/location/{protocol}")
    List<ProtocolLocation> locationProtocol(
        @ApiParam(
            value = "The protocol ID.",
            required = true
        ) @PathParam("protocol") String protocol,
        @Context UriInfo uriInfo,

        @Context HttpServletRequest servletRequest,
        @Context HttpServletResponse servletResponse
    );

    /**
     * This API is called by the homeserver in order to retrieve a Matrix User ID linked to a user on the third party network,
     * given a set of user parameters.
     * <b>Required Auth</b>: Yes.
     *
     * @param protocol        Required. The protocol ID.
     * @param uriInfo         Uri info to retrieve all query params.
     * @param servletRequest  Servlet request.
     * @param servletResponse Servlet response.
     * @return <p>Status code 200: The Matrix User IDs found with the given parameters.</p>
     * <p>Status code 401: The homeserver has not supplied credentials to the application service. Optional error information can
     * be included in the body of this response.</p>
     * <p>Status code 403: The credentials supplied by the homeserver were rejected.</p>
     * <p>Status code 404: No users were found with the given parameters.</p>
     */
    @ApiOperation("Retrieve a Matrix User ID linked to a user on the third party service, given a set of user parameters.")
    @ApiResponses( {
        @ApiResponse(code = 200, message = "The Matrix User IDs found with the given parameters."),
        @ApiResponse(code = 401, message = "The homeserver has not supplied credentials to the application service."
            + " Optional error information can be included in the body of this response."),
        @ApiResponse(code = 403, message = "The credentials supplied by the homeserver were rejected."),
        @ApiResponse(code = 404, message = "No users were found with the given parameters.")
    })
    @Secured
    @GET
    @Path("/user/{protocol}")
    List<ProtocolUser> userProtocol(
        @ApiParam(
            value = "The name of the protocol",
            required = true
        ) @PathParam("protocol") String protocol,
        @Context UriInfo uriInfo,

        @Context HttpServletRequest servletRequest,
        @Context HttpServletResponse servletResponse
    );

    /**
     * Retrieve an array of third party network locations from a Matrix room alias.
     * <br>
     * <b>Requires Auth</b>: Yes.
     *
     * @param alias           Required. The Matrix room alias to look up.
     * @param servletRequest  Servlet request.
     * @param servletResponse Servlet response.
     * @return <p>Status code 200: All found third party locations.</p>
     * <p>Status code 401: The homeserver has not supplied credentials to the application service. Optional error information can
     * be included in the body of this response.</p>
     * <p>Status code 403: The credentials supplied by the homeserver were rejected.</p>
     * <p>Status code 404: No mappings were found with the given parameters.</p>
     */
    @ApiOperation(
        value = "Retrieve an array of third party network locations from a Matrix room alias."
    )
    @ApiResponses( {
        @ApiResponse(code = 200, message = "At least one portal room was found."),
        @ApiResponse(code = 404, message = "No portal rooms were found.")
    })
    @Secured
    @GET
    @Path("/location")
    List<ProtocolLocation> location(
        @ApiParam(
            value = "The Matrix room alias to look up.",
            required = true
        ) @QueryParam("alias") String alias,

        @Context HttpServletRequest servletRequest,
        @Context HttpServletResponse servletResponse
    );

    /**
     * Retrieve an array of third party users from a Matrix User ID.
     * <br>
     * <b>Requires Auth</b>: Yes.
     *
     * @param userId          Required. The Matrix User ID to look up.
     * @param servletRequest  Servlet request.
     * @param servletResponse Servlet response.
     * @return <p>Status code 200: An array of third party users.</p>
     * <p>Status code 401: The homeserver has not supplied credentials to the application service. Optional error information can
     * be included in the body of this response.</p>
     * <p>Status code 403: The credentials supplied by the homeserver were rejected.</p>
     * <p>Status code 404: No mappings were found with the given parameters.</p>
     */
    @ApiOperation("Retrieve an array of third party users from a Matrix User ID.")
    @ApiResponses( {
        @ApiResponse(code = 200, message = "An array of third party users."),
        @ApiResponse(code = 401, message = "The homeserver has not supplied credentials to the application service."
            + " Optional error information can be included in the body of this response."),
        @ApiResponse(code = 403, message = "The credentials supplied by the homeserver were rejected."),
        @ApiResponse(code = 404, message = "No mappings were found with the given parameters.")
    })
    @Secured
    @GET
    @Path("/user")
    List<ProtocolUser> user(
        @ApiParam(
            value = "The Matrix User Id to look up",
            required = true
        ) @QueryParam("userid") String userId,

        @Context HttpServletRequest servletRequest,
        @Context HttpServletResponse servletResponse
    );
}
