package io.github.ma1uta.matrix.client.model.push;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement
public class PushRulesResponse {

    private Ruleset global;
}