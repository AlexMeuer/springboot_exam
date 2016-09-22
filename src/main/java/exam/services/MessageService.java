package exam.services;


import exam.model.Message;

public interface MessageService {

    void broadcastMessage(Message message);
}
