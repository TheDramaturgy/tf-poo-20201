package biblioteca.Exceptions;

public class InvalidStatusException extends Exception {
    private String received_parameter;
    public InvalidStatusException(String s) {
        received_parameter = s;
    }

    public String getMessage() {
        return new String("\""+received_parameter+"\" não é um Status válido");
    }
}
