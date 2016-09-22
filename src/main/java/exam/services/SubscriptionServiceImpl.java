package exam.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import exam.exceptions.SubscriptionNotFoundException;
import exam.model.Message;
import exam.model.MessageType;
import exam.model.Subscription;
import exam.util.SubscriptionSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.bus.EventBus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private static final Logger log = LoggerFactory.getLogger(SubscriptionServiceImpl.class);
    private Collection<Subscription> subs = new ArrayList<>();
    private AtomicInteger createdSubs = new AtomicInteger(0);

    @Autowired
    EventBus eventBus;

    // Jackson serialization stuff
    private ObjectMapper objectMapper;
    private SimpleModule simpleModule;

    public SubscriptionServiceImpl() {
        // Setup Subscription objects to be serialized properly.
        objectMapper = new ObjectMapper();
        simpleModule = new SimpleModule("SubscriptionModule");
        simpleModule.addSerializer(new SubscriptionSerializer(Subscription.class));
        objectMapper.registerModule(simpleModule);
    }

    /**
     * Find a subscription by its id.
     * @param id The id of the subscription to look for.
     * @return The subscription with the specified id.
     * @throws SubscriptionNotFoundException If sub with the id does not exist.
     */
    @Override
    public Subscription getSubscription(int id) throws SubscriptionNotFoundException {
        log.debug("Getting sub.");
        for(Subscription sub : subs) {
            if(sub.getId() == id) {
                return sub;
            }
        }
        throw new SubscriptionNotFoundException(id);
    }

    /**
     * Create a new subscription subscribed to the specified types.
     * @param types The message types to subscribe to.
     * @return The id of the newly created subscription
     */
    @Override
    public int createSubscription(MessageType[] types) {
        log.debug("Creating sub.");
        int id = createdSubs.getAndIncrement();
        subs.add(new Subscription(id, types));
        return  id;
    }

    /**
     * Set the MessageTypes subscribed to by a subscription.
     * @param id The id of the sub to update.
     * @param types The new types to be subscribed to. (Omitting a type will unsub from it)
     * @throws SubscriptionNotFoundException If no sub with the id exists.
     */
    @Override
    public void updateSubscription(int id, MessageType[] types) throws SubscriptionNotFoundException {
        log.debug("Updating sub.");
        Subscription sub = getSubscription(id);
        sub.setMessageTypes(types);
    }

    @Override
    public String serialize(Subscription sub) {
        try {
            return objectMapper.writeValueAsString(sub);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * Receives Message events from the event bus and
     * passes them to the subscriptions.
     * @param messageEvent The event being accepted.
     */
    @Override
    public void accept(Event<Message> messageEvent) {
        log.debug("Received message from bus.");
        for(Subscription s : subs) {
            s.receiveMessage(messageEvent.getData());
        }
    }
}
