package biblioteca.Dados;

import biblioteca.Exceptions.InvalidStatusException;

public enum Status {
    DISPONIVEL, EMPRESTADO, RESERVADO;

    public static Status parseStatus(String s) throws InvalidStatusException {
        Status status = null;
        if (s.equals("DISPONIVEL")) status = DISPONIVEL;
        else if (s.equals("EMPRESTADO")) status = EMPRESTADO;
        else if (s.equals("RESERVADO")) status = RESERVADO;
        else throw new InvalidStatusException(s);
        return status;
    }

}

