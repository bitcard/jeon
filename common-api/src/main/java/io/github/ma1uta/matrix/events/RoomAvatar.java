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

import io.github.ma1uta.matrix.EventContent;
import io.github.ma1uta.matrix.events.nested.ImageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * A picture that is associated with the room. This can be displayed alongside the room information.
 */
@ApiModel(description = "A picture that is associated with the room. This can be displayed alongside the room information.")
public class RoomAvatar implements EventContent {

    /**
     * Metadata about the image referred to in url.
     */
    @ApiModelProperty("Metadata about the image referred to in url.")
    private ImageInfo info;

    /**
     * Required. The URL to the image.
     */
    @ApiModelProperty(value = "The URL to the image.", required = true)
    private String url;

    public ImageInfo getInfo() {
        return info;
    }

    public void setInfo(ImageInfo info) {
        this.info = info;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}