package io.github.ma1uta.matrix.client.model.room;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PublicRoomsChunk {

    private List<String> aliases;
    private String canonicalAlias;
    private String name;
    private Long numJoinedMembers;
    private String roomId;
    private String topic;
    private Boolean worldReadable;
    private Boolean guestCanJoin;
    private String avatarUrl;
}