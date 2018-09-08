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

package io.github.ma1uta.matrix.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.ma1uta.matrix.EventContent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * This event controls whether guest users are allowed to join rooms. If this event is absent, servers should act as if it is present
 * and has the guest_access value "forbidden".
 */
@ApiModel(description = "This event controls whether guest users are allowed to join rooms. If this event is absent,"
    + " servers should act as if it is present and has the guest_access value \"forbidden\".")
public class RoomGuestAccess implements EventContent {

    /**
     * Whether guests can join the room. One of: ["can_join", "forbidden"].
     */
    @ApiModelProperty(
        name = "guest_access",
        value = "Whether guests can join the room.",
        allowableValues = "can_join, forbidden"
    )
    @JsonProperty("guest_access")
    private String guestAccess;

    public String getGuestAccess() {
        return guestAccess;
    }

    public void setGuestAccess(String guestAccess) {
        this.guestAccess = guestAccess;
    }
}
