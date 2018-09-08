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
import io.github.ma1uta.matrix.events.nested.PublicKeys;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Acts as an m.room.member invite event, where there isn't a target user_id to invite. This event contains a token and a
 * public key whose private key must be used to sign the token. Any user who can present that signature may use this invitation
 * to join the target room.
 */
@ApiModel(description = "Acts as an m.room.member invite event, where there isn't a target user_id to invite."
    + " This event contains a token and a public key whose private key must be used to sign the token."
    + " Any user who can present that signature may use this invitation to join the target room.")
public class RoomThirdPartyInvite implements EventContent {

    /**
     * Required. A user-readable string which represents the user who has been invited. This should not contain the user's
     * third party ID, as otherwise when the invite is accepted it would leak the association between the matrix ID and the third party ID.
     */
    @ApiModelProperty(
        name = "display_name",
        value = "A user-readable string which represents the user who has been invited."
            + " This should not contain the user's third party ID, as otherwise when the invite is accepted it would leak the association"
            + " between the matrix ID and the third party ID.",
        required = true
    )
    @JsonProperty("display_name")
    private String displayName;

    /**
     * Required. A URL which can be fetched, with querystring public_key=public_key, to validate whether the key has been revoked.
     * The URL must return a JSON object containing a boolean property named 'valid'.
     */
    @ApiModelProperty(
        name = "key_validity_url",
        value = "A URL which can be fetched, with querystring public_key=public_key, to"
            + " validate whether the key has been revoked. The URL must return a JSON object containing a boolean property named 'valid'.",
        required = true
    )
    @JsonProperty("key_validity_url")
    private String keyValidityUrl;

    /**
     * Required. A base64-encoded ed25519 key with which token must be signed (though a signature from any entry in public_keys is
     * also sufficient). This exists for backwards compatibility.
     */
    @ApiModelProperty(
        name = "public_key",
        value = "A base64-encoded ed25519 key with which token must be signed"
            + " (though a signature from any entry in public_keys is also sufficient). This exists for backwards compatibility.",
        required = true
    )
    @JsonProperty("public_key")
    private String publicKey;

    /**
     * Keys with which the token may be signed.
     */
    @ApiModelProperty(
        name = "public_keys",
        value = "Keys with which the token may be signed."
    )
    @JsonProperty("public_keys")
    private List<PublicKeys> publicKeys;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getKeyValidityUrl() {
        return keyValidityUrl;
    }

    public void setKeyValidityUrl(String keyValidityUrl) {
        this.keyValidityUrl = keyValidityUrl;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public List<PublicKeys> getPublicKeys() {
        return publicKeys;
    }

    public void setPublicKeys(List<PublicKeys> publicKeys) {
        this.publicKeys = publicKeys;
    }
}
