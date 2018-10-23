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

import io.github.ma1uta.matrix.support.DeserializerUtil;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

/**
 * JSON response for available api.
 */
@Schema(
    description = "JSON response for available api"
)
public class AvailableResponse {

    /**
     * A flag to indicate that the username is available. This should always be true when the server replies with 200 OK.
     */
    @Schema(
        description = "A flag to indicate that the username is available. This should always be true when the server replies with 200 OK."
    )
    private Boolean available;

    public AvailableResponse() {
    }

    public AvailableResponse(Map props) {
        this.available = DeserializerUtil.toBoolean(props, "available");
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
