package biblioteca.Exceptions;

public class IdNotFoundException extends Exception {
    private String message;
    public IdNotFoundException(String s) {
        message = s;
    }

    public String getMessage() {
        return message;
    }
}
