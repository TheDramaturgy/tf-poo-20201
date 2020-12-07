package biblioteca.Dados;

import biblioteca.Lib.Funcionario;

public class Options {
    private static Options Instance;

    private static Funcionario funcionarioAtivo;
    private static int itemNewId = 0;
    private static int clienteNewId = 0;
    private static int funcionarioNewId = 0;

    public static final String SAVED_LOCATION = "save.txt";
    public static final String EQUIPE_LOCATION = "equipe.txt";
    public static final String ITEM_LIMIT = "FIM_SAVE";
    public static final String CLIENT_LIMIT = "ITEMS";
    public static final String LIVRO = "LIVRO";
    public static final String REVISTA = "REVISTA";
    public static final String JORNAL = "JORNAL";
    public static final int MAX_LIVROS_EMPRESTADOS = 8;
    public static final int MAX_LIVROS_RESERVADOS = 6;

    private Options() { }

    public static Funcionario getFuncionarioAtivo() { return funcionarioAtivo; }
    public static void setFuncionarioAtivo(Funcionario funcionarioAtivo) {
        Options.funcionarioAtivo = funcionarioAtivo;
    }

    public static int getItemNewId() { return itemNewId++; }
    public static int getClienteNewId() { return clienteNewId++; }
    public static int getFuncionarioNewId() { return funcionarioNewId++; }

    public static void setItemNewId(int itemNewId) { Options.itemNewId = itemNewId; }
    public static void setClienteNewId(int clienteNewId) { Options.clienteNewId = clienteNewId; }
    public static void setFuncionarioNewId(int funcionarioNewId) { Options.funcionarioNewId = funcionarioNewId; }
}
