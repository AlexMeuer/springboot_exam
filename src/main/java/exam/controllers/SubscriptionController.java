package exam.controllers;

import exam.model.MessageType;
import exam.services.SubscriptionService;
import exam.exceptions.SubscriptionNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/sub")
public class SubscriptionController {

    @Autowired
    SubscriptionService subscriptionService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> readSubscription(@RequestParam(name = "id") int id) throws SubscriptionNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(
                subscriptionService.serialize(subscriptionService.getSubscription(id)),
                headers,
                HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> createSubscription(@RequestBody MessageType[] types) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("SubscriptionId", String.valueOf(subscriptionService.createSubscription(types)));
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<String> updateSubscription(@RequestParam(name = "id") int id, @RequestBody MessageType[] types) throws SubscriptionNotFoundException {
        subscriptionService.updateSubscription(id, types);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Handles exceptions for when a subscription is not found.
     * @param ex The exception that caused this.
     */
    @ExceptionHandler(SubscriptionNotFoundException.class)
    void handleSubNotFound(HttpServletResponse response, SubscriptionNotFoundException ex) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }
}
