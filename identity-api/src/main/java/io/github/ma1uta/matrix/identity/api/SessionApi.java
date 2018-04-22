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

package io.github.ma1uta.matrix.identity.api;

import io.github.ma1uta.matrix.identity.model.associations.SessionResponse;
import io.github.ma1uta.matrix.identity.model.associations.ValidationResponse;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * The flow for creating an association is session-based.
 */
@Path("/_matrix/identity/api/v1")
public interface SessionApi extends IdentityApi {

    /**
     * The flow for creating an association is session-based.
     * <p/>
     * Within a session, one may prove that one has ownership of a 3pid. Once this has been established, the user can form
     * an association between that 3pid and a Matrix user ID. Note that this association is only proved one way; a user can
     * associate any Matrix user ID with a validated 3pid, i.e. I can claim that any email address I own is associated
     * with @billg:microsoft.com.
     * <p/>
     * Sessions are time-limited; a session is considered to have been modified when it was created, and then when a validation
     * is performed within it. A session can only be checked for validation, and validation can only be performed within
     * a session, within a 24 hour period since its most recent modification. Any attempts to perform these actions after
     * the expiry will be rejected, and a new session should be created and used instead.
     *
     * @param clientSecret client secret code.
     * @param email        email.
     * @param sendAttempt  sending attempts.
     * @param nextLink     when the validation is completed, the identity service will redirect the user to that URL.
     * @return the sid generated for this session to the caller, in a JSON object containing the sid key.
     */
    @POST
    @Path("/validate/email/requestToken")
    @Produces(MediaType.APPLICATION_JSON)
    SessionResponse create(@FormParam("client_secret") String clientSecret, @FormParam("email") String email,
                           @FormParam("send_attempt") Long sendAttempt, @FormParam("next_link") String nextLink);

    /**
     * A user may make a GET  request to /_matrix/identity/api/v1/validate/email/submitToken with the following
     * parameters (either as query parameters or URL-encoded POST parameters):
     * <ul>
     * <li>sid the sid for the session, generated by the requestToken call.</li>
     * <li>client_secret the client secret which was supplied to the requestToken call.</li>
     * <li>token the token generated by the requestToken call, and emailed to the user.</li>
     * </ul>
     * <p/>
     * If these three values are consistent with a set generated by a requestToken call, ownership of the email address is
     * considered to have been validated. This does not publish any information publicly, or associate the email address
     * with any Matrix user ID. Specifically, calls to /lookup will not show a binding.
     * <p/>
     * Otherwise, an error will be returned.
     *
     * @param sid          the sid for the session, generated by the requestToken call.
     * @param clientSecret the client secret which was supplied to the requestToken call.
     * @param token        the token generated by the requestToken call, and emailed to the user.
     * @return result of the validation or error.
     */
    @GET
    @Path("/validate/email/submitToken")
    @Produces(MediaType.APPLICATION_JSON)
    ValidationResponse getValidate(@QueryParam("sid") String sid, @QueryParam("client_secret") String clientSecret,
                                   @QueryParam("token") String token);

    /**
     * A user may make a POST request to /_matrix/identity/api/v1/validate/email/submitToken with the following
     * parameters (either as query parameters or URL-encoded POST parameters):
     * <ul>
     * <li>sid the sid for the session, generated by the requestToken call.</li>
     * <li>client_secret the client secret which was supplied to the requestToken call.</li>
     * <li>token the token generated by the requestToken call, and emailed to the user.</li>
     * </ul>
     * <p/>
     * If these three values are consistent with a set generated by a requestToken call, ownership of the email address is
     * considered to have been validated. This does not publish any information publicly, or associate the email address
     * with any Matrix user ID. Specifically, calls to /lookup will not show a binding.
     * <p/>
     * Otherwise, an error will be returned.
     *
     * @param sid          the sid for the session, generated by the requestToken call.
     * @param clientSecret the client secret which was supplied to the requestToken call.
     * @param token        the token generated by the requestToken call, and emailed to the user.
     * @return result of the validation or error.
     */
    @POST
    @Path("/validate/email/submitToken")
    @Produces(MediaType.APPLICATION_JSON)
    ValidationResponse postValidate(@FormParam("sid") String sid, @FormParam("client_secret") String clientSecret,
                                    @FormParam("token") String token);
}
