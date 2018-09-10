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

import java.util.List;

/**
 * This event type is used to forward keys for end-to-end encryption. Typically it is encrypted as an m.room.encrypted event,
 * then sent as a to-device event.
 */
@ApiModel(description = "This event type is used to forward keys for end-to-end encryption."
    + " Typically it is encrypted as an m.room.encrypted event, then sent as a to-device event.")
public class ForwardedRoomKey implements EventContent {

    /**
     * Required. The encryption algorithm the key in this event is to be used with.
     */
    @ApiModelProperty(
        value = "Required. The encryption algorithm the key in this event is to be used with.",
        required = true
    )
    private String algorithm;

    /**
     * Required. The room where the key is used.
     */
    @ApiModelProperty(
        name = "room_id",
        value = "The room where the key is used.",
        required = true
    )
    @JsonProperty("room_id")
    private String roomId;

    /**
     * Required. The Curve25519 key of the device which initiated the session originally.
     */
    @ApiModelProperty(
        name = "sender_key",
        value = "Required. The Curve25519 key of the device which initiated the session originally.",
        required = true
    )
    @JsonProperty("sender_key")
    private String senderKey;

    /**
     * Required. The ID of the session that the key is for.
     */
    @ApiModelProperty(
        name = "session_id",
        value = "The ID of the session that the key is for.",
        required = true
    )
    @JsonProperty("session_id")
    private String sessionId;

    /**
     * Required. The key to be exchanged.
     */
    @ApiModelProperty(
        name = "session_key",
        value = "The key to be exchanged.",
        required = true
    )
    @JsonProperty("session_key")
    private String sessionKey;

    /**
     * Required. The Ed25519 key of the device which initiated the session originally. It is 'claimed' because the receiving device
     * has no way to tell that the original room_key actually came from a device which owns the private part of this key unless they
     * have done device verification.
     */
    @ApiModelProperty(
        name = "sender_claimed_ed25519_key",
        value = "The Ed25519 key of the device which initiated the session originally. It is 'claimed' because"
            + " the receiving device has no way to tell that the original room_key actually came from a device which owns the private part"
            + "of this key unless they have done device verification.",
        required = true
    )
    @JsonProperty("sender_claimed_ed25519_key")
    private String senderClaimedEd25519Key;

    /**
     * Required. Chain of Curve25519 keys. It starts out empty, but each time the key is forwarded to another device,
     * the previous sender in the chain is added to the end of the list. For example, if the key is forwarded from A to B to C,
     * this field is empty between A and B, and contains A's Curve25519 key between B and C.
     */
    @ApiModelProperty(
        name = "forwarding_curve25519_key_chain",
        value = "Chain of Curve25519 keys. It starts out empty, but each time the key is forwarded to another device,"
            + " the previous sender in the chain is added to the end of the list. For example, if the key is forwarded from A to B to C,"
            + " this field is empty between A and B, and contains A's Curve25519 key between B and C.",
        required = true
    )
    @JsonProperty("forwarding_curve25519_key_chain")
    private List<String> forwardingCurve25519KeyChain;

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getSenderKey() {
        return senderKey;
    }

    public void setSenderKey(String senderKey) {
        this.senderKey = senderKey;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getSenderClaimedEd25519Key() {
        return senderClaimedEd25519Key;
    }

    public void setSenderClaimedEd25519Key(String senderClaimedEd25519Key) {
        this.senderClaimedEd25519Key = senderClaimedEd25519Key;
    }

    public List<String> getForwardingCurve25519KeyChain() {
        return forwardingCurve25519KeyChain;
    }

    public void setForwardingCurve25519KeyChain(List<String> forwardingCurve25519KeyChain) {
        this.forwardingCurve25519KeyChain = forwardingCurve25519KeyChain;
    }
}