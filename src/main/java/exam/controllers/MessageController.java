package exam.controllers;

import exam.Message;
import exam.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/msg")
public class MessageController {

    @Autowired
    MessageService messageService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> broadcastMessage(@RequestBody Message message) {
        messageService.broadcastMessage(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
