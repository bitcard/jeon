package io.github.ma1uta.matrix.client.model.content;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement
public class ContentUri {

    private String contentUri;
}