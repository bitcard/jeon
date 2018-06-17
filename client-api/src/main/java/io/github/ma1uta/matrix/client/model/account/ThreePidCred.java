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

package io.github.ma1uta.matrix.client.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 3Pid credentials.
 */
@ApiModel(description = "3pid credentials")
public class ThreePidCred {

    /**
     * Identity server id.
     */
    @ApiModelProperty("Identity server id")
    private String sid;

    /**
     * Identity server client secret.
     */
    @ApiModelProperty(name = "client_secret", value = "Identity server client secret")
    @JsonProperty("client_secret")
    private String clientSecret;

    /**
     * Url of identity server auth with.
     */
    @ApiModelProperty(name = "id_server", value = "Url of identity server auth with")
    @JsonProperty("id_server")
    private String idServer;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getIdServer() {
        return idServer;
    }

    public void setIdServer(String idServer) {
        this.idServer = idServer;
    }
}
