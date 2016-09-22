package exam.services;

import exam.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.bus.EventBus;

@Service
public class MessageServiceImpl implements MessageService{
    private static final Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    EventBus eventBus;

    @Override
    public void broadcastMessage(Message message) {
        log.info("Broadcasting message ("+message.getType()+" - \""+message.getContent()+"\")");
        eventBus.notify("message", Event.wrap(message));
    }
}
