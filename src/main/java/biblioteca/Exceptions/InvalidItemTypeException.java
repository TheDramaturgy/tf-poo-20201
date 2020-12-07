package biblioteca.Exceptions;

public class InvalidItemTypeException extends Exception {
    private String received_parameter;
    public InvalidItemTypeException(String s) {
        received_parameter = s;
    }

    public String getMessage() {
        return new String("\""+received_parameter+"\" não é um tipo de Item válido");
    }
}
