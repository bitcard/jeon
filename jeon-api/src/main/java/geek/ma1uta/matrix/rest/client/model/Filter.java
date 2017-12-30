package geek.ma1uta.matrix.rest.client.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement
public class Filter {

    private Long limit;
    private List<String> notSenders;
    private List<String> notTypes;
    private List<String> senders;
    private List<String> types;
}