package exam.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import exam.util.SubscriptionSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumMap;
import java.util.concurrent.atomic.AtomicInteger;

@JsonSerialize(using = SubscriptionSerializer.class)
public class Subscription {
    private static final Logger LOGGER = LoggerFactory.getLogger(Subscription.class);
    private final int id;

    /**
     * Holds the message types that this is subscribed to and
     * the number of times it's received a message for each type.
     */
    private EnumMap<MessageType, AtomicInteger> typeMap;
    public Subscription(int id, MessageType[] types) {
        this.id = id;
        typeMap = new EnumMap<>(MessageType.class);

        setMessageTypes(types);
    }

    public int getId() {
        return id;
    }

    /**
     * Clears the subscribed types and sets new types.
     * @param types The new types to be subscribed to.
     */
    public void setMessageTypes(MessageType[] types) {
        typeMap.clear();
        for(MessageType t : types) {
            typeMap.put(t, new AtomicInteger(0));
        }
    }

    /**
     * Gets the count of how many times a certain message type
     * has been received by this subscription.
     * @param type The type of message to check.
     * @return The count of message received by the type or -1 if type is not subscribed to.
     */
    public int getNumberOfTimesReceived(final MessageType type) {
        if(typeMap.containsKey(type)) {
            return typeMap.get(type).get();
        }
        return -1;
    }

    /**
     * Ask the subscription to receive a message.
     * Does nothing if message's type is not subscribed to.
     * @param msg The message being received.
     */
    public void receiveMessage(Message msg) {
        if(typeMap.containsKey(msg.getType())) {
            typeMap.get(msg.getType()).incrementAndGet();

            // Print out that we received a message
            LOGGER.info("Subscription{"+id+"} received: "+msg.getContent());
        }
    }
}
