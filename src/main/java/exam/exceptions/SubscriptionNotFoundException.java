package exam.exceptions;

public class SubscriptionNotFoundException extends Exception {

    private long subId;

    public SubscriptionNotFoundException(long subId) {
        this.subId = subId;
    }

    @Override
    public String getMessage() {
        return "The subscription with id "+subId+" could not be found.";
    }
}
