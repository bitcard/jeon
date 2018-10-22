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

package io.github.ma1uta.matrix.identity.model.session;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.ma1uta.matrix.support.DeserializerUtil;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;
import javax.json.bind.annotation.JsonbProperty;

/**
 * JSON body request of the validation.
 */
@Schema(
    description = "JSON body request of the validation."
)
public class SubmitToken {

    /**
     * Required. The session ID, generated by the requestToken call.
     */
    @Schema(
        description = "The session ID, generated by the requestToken call.",
        required = true
    )
    private String sid;

    /**
     * Required. The client secret that was supplied to the requestToken call.
     */
    @Schema(
        description = "The client secret that was supplied to the requestToken call.",
        required = true
    )
    @JsonbProperty("client_secret")
    private String clientSecret;

    /**
     * Required. The token generated by the requestToken call and sent to the user.
     */
    @Schema(
        description = "The token generated by the requestToken call and sent to the user.",
        required = true
    )
    private String token;

    public SubmitToken() {
    }

    public SubmitToken(Map props) {
        this.sid = DeserializerUtil.toString(props, "sid");
        this.clientSecret = DeserializerUtil.toString(props, "client_secret");
        this.token = DeserializerUtil.toString(props, "token");
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    @JsonProperty("client_secret")
    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
