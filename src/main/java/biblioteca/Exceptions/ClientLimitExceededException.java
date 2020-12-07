package biblioteca.Exceptions;

public class ClientLimitExceededException extends Exception{
    String operacao;
    int id;
    public ClientLimitExceededException(String s, int i) {
        operacao = s;
        id = i;
    }

    public String getMessage() {
        return new String("O limite do cliente \""+id+"\" para " + operacao + " esgotou!");
    }
}
