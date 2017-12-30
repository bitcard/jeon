package geek.ma1uta.matrix.rest.client.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement
public class RequestToken {

    private String idServer;
    private String clientSecret;
    private String email;
    private String sendAttempt;
}