package exceptions;

public class ReadingRequestException extends RuntimeException {

    public ReadingRequestException(String exceptionClass, String cause) {
        super(String.format("Exception reading request. Cause: %s.\nException class: %s", exceptionClass, cause));
    }

}
