package exam;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class SubscriptionSerializer extends StdSerializer<Subscription>{

    public SubscriptionSerializer(Class<Subscription> t) {
        super(t);
    }
    public SubscriptionSerializer() {
        super(Subscription.class);
    }

    /**
     * Serializes a Subscription into Json.
     * Example Json output:
     * <code>
     * {
     *     "id": 123,
     *     "types": {
     *         "regular": 12,
     *         "wide": 34
     *     }
     * }
     * </code>
     */
    @Override
    public void serialize(Subscription sub, JsonGenerator jgen, SerializerProvider sp) throws IOException {
        jgen.writeStartObject();

        jgen.writeNumberField("id", sub.getId());

        jgen.writeObjectFieldStart("types");

        // For each MessageType, check if we're subscribed and output the number
        // of times we;ve received that MessageType.
        for(MessageType type : MessageType.values()) {
            int timesReceived = sub.getNumberOfTimesReceived(type);
            if(-1 != timesReceived) {
                jgen.writeNumberField(type.name(), timesReceived);
            }
        }
        jgen.writeEndObject(); // end "types"

        jgen.writeEndObject(); // end json doc
    }
}
