package exam.services;

import exam.model.Message;
import exam.model.MessageType;
import exam.model.Subscription;
import exam.exceptions.SubscriptionNotFoundException;
import reactor.bus.Event;
import reactor.fn.Consumer;

public interface SubscriptionService extends Consumer<Event<Message>> {

    Subscription getSubscription(int id) throws SubscriptionNotFoundException;

    /**
     * @return The id of the created subscription.
     */
    int createSubscription(MessageType[] types);

    void updateSubscription(int id, MessageType[] types) throws SubscriptionNotFoundException;

    /**
     * @return The given sub as json.
     */
    String serialize(Subscription sub);
}
