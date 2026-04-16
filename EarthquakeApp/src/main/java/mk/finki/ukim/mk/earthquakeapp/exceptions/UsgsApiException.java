package mk.finki.ukim.mk.earthquakeapp.exceptions;

public class UsgsApiException extends RuntimeException {
    public UsgsApiException(String message) {
        super(message);
    }
}
