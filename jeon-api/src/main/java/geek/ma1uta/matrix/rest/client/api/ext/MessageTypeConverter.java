package geek.ma1uta.matrix.rest.client.api.ext;

import geek.ma1uta.matrix.rest.client.model.MessageType;

import javax.ws.rs.ext.ParamConverter;

public class MessageTypeConverter implements ParamConverter<MessageType> {
    @Override
    public MessageType fromString(String value) {
        return MessageType.valueOf(value);
    }

    @Override
    public String toString(MessageType value) {
        return value.code();
    }
}