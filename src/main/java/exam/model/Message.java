package exam.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
    private final MessageType type;
    private final String content;

    public Message(@JsonProperty("type") MessageType type, @JsonProperty("content") String content) {
        this.type = type;
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
