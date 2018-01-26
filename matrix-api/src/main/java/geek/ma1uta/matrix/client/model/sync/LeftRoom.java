package geek.ma1uta.matrix.client.model.sync;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement
public class LeftRoom {

    private State state;
    private Timeline timeline;
}