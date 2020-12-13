package biblioteca.Exceptions;

public class InvalidOperationException extends Exception {
    String message;
    public InvalidOperationException(String s) {
        message = s;
    }

    public String getMessage() {
        return message;
    }
}
