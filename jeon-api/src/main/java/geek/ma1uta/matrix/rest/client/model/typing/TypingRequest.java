package geek.ma1uta.matrix.rest.client.model.typing;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement
public class TypingRequest {

    private Boolean typing;
    private Long timeout;
}