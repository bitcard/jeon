package io.github.ma1uta.matrix.client.model.profile;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement
public class AvatarUrl {

    private String avatarUrl;
}