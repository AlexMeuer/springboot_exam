### Microservice programming test

## Spec
A simple message subscription service that exposes the following functionality: 

* create a subscription 
     (Would have at least one parameter, which would be a list of messageTypes the subscription wants to keep track of) 
* read the subscription 
* update the subscription 
* post a message 
  
The message would have at least a ‘messageType’ property. 

In the response to the 'read subscription’ we would like also see how many times a particular event type has been received by a subscription. There may be more than one subscription listening for the same event type(s). 

Please provide a runnable service written in a Java 8 technology (can be based on spring-boot or any other of your preference). We would like clean, maintainable code following good programming practices to be returned.

# Endpoints
The program has two endpoitns:
* **MessageController** `/msg`
    * Only `POST` is accepted.
    * Expects a Json body in the format:
        `{
          "type": "regular",
          "content": "foo-bar"
        }`
* **SubscriptionController** `/sub`
    * `GET`, `POST` and `PUT` are accepted.
    * `GET` requires an `id` as a param (e.g. `GET /sub?id=0`) and returns a json object representing the subscription.
    * `POST` requires only a json array of `MessageType`s. The `id` of the new `Subscription` is returned as a http header (`SubscriptionId`).
    * `PUT`requires both an `id` param **and** an array of `MessageType`s.
    
# Services
There is a service implementation to match each endpoint:
* **MessageService**
    * Takes the data from the `POST`body and puts it in the `EventBus`.
* **SubscriptionService**
    * Accepts `Message` events from the `EventBus` and offers them to the `Subscription`s.
    * Handles `GET` requests by searching for the `Subscription` by the given `id`.
        * Throws `SubscriptionNotFoundException` if none was founding matching the `id`, resulting in a `404` response.
    * Handles `POST` requests by creating a `Subscription` from the given enum array.
    * Handles `PUT` requests by updating the `Subscription` with the given `id`, setting it's subscribed types (omitted types are unsubbed from).
        * Throws a `404 Not Found` similar to `GET` request.
