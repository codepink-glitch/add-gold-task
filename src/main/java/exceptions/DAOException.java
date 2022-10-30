package exceptions;

public class DAOException extends RuntimeException {

    public DAOException(String exception, Throwable cause) {
        super(exception, cause);
    }

}
