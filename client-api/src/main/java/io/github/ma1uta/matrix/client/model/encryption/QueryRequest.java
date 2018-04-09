package io.github.ma1uta.matrix.client.model.encryption;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class QueryRequest {

    private Long timeout;
    private Map<String, List<String>> deviceKeys;
}