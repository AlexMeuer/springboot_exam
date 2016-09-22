package exam.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The types of messages that can be sent/received/subscribed to.
 */
public enum MessageType {
    @JsonProperty("regular")
    REGULAR,
    @JsonProperty("tall")
    TALL,
    @JsonProperty("short")
    SHORT,
    @JsonProperty("wide")
    WIDE
}
