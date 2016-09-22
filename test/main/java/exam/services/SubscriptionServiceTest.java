package exam.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import exam.exceptions.SubscriptionNotFoundException;
import exam.model.MessageType;
import exam.model.Subscription;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubscriptionServiceTest {

    SubscriptionService subscriptionService;

    @Before
    public void setUp() throws Exception {
        subscriptionService = new SubscriptionServiceImpl();
        subscriptionService.createSubscription(new MessageType[]{MessageType.REGULAR});
    }

    @Test
    public void createSubscription() throws Exception {
        subscriptionService.createSubscription(new MessageType[]{MessageType.REGULAR});
    }

    @Test
    public void getExistingSubscription() throws Exception {
        subscriptionService.getSubscription(0);
    }

    @Test(expected = SubscriptionNotFoundException.class)
    public void getNonExistingSubscription() throws Exception {
        subscriptionService.getSubscription(5);
    }

    @Test
    public void updateExistingSubscription() throws Exception {
        subscriptionService.updateSubscription(0, new MessageType[]{MessageType.REGULAR});
    }

    @Test(expected = SubscriptionNotFoundException.class)
    public void updateNonExistingSubscription() throws Exception {
        subscriptionService.updateSubscription(5, new MessageType[]{MessageType.REGULAR});
    }

    @Test
    public void serializeValidSubscription() throws Exception {
        final String expectedOutput = "{\"id\":5,\"types\":{\"SHORT\":0,\"WIDE\":0}}";
        Subscription sub = new Subscription(5, new MessageType[]{MessageType.SHORT, MessageType.WIDE});
        String output = subscriptionService.serialize(sub);

        assertTrue(output.equals(expectedOutput));
    }

}