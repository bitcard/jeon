package io.github.ma1uta.matrix.client.api;

import io.github.ma1uta.matrix.client.model.EmptyResponse;
import io.github.ma1uta.matrix.client.model.auth.LoginRequest;
import io.github.ma1uta.matrix.client.model.auth.LoginResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * A client can obtain access tokens using the /login API.
 * <p/>
 * Note that this endpoint does not currently use the user-interactive authentication API.
 *
 * @author ma1uta
 */
@Path("/_matrix/client/r0")
public interface AuthApi extends ClientApi {

    /**
     * Authenticates the user, and issues an access token they can use to authorize themself in subsequent requests.
     * <p/>
     * If the client does not supply a device_id, the server must auto-generate one.
     * <p/>
     * The returned access token must be associated with the device_id supplied by the client or generated by the server.
     * The server may invalidate any access token previously associated with that device.
     *
     * @param loginRequest {@link LoginRequest}
     * @return {@link LoginResponse}
     * @see <a href="https://matrix.org/docs/spec/client_server/r0.3.0.html#relationship-between-access-tokens-and-devices>
     * See Relationship between access tokens and devices.</a>
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    LoginResponse login(LoginRequest loginRequest);

    /**
     * Invalidates an existing access token, so that it can no longer be used for authorization.
     * <p/>
     * <b>Requires auth</b>: Yes.
     * <p/>
     * The access token used in the request was successfully invalidated.
     *
     * @return emtpy response.
     */
    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    EmptyResponse logout();
}