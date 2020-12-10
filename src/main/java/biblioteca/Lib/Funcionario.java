package biblioteca.Lib;

import biblioteca.Dados.Options;

public class Funcionario {
    private String nome;
    private int id;

    public Funcionario(String nome, int id) {
        this.nome = nome;
        this.id = id;
    }

    public Funcionario(String nome) {
        this.nome = nome;
        this.id = Options.getFuncionarioNewId();
    }

    public String getNome() { return nome; }
    public int getId() { return id; }

    public void setNome(String nome) { this.nome = nome; }
    public void setId(int id) { this.id = id; }

    @Override
    public String toString() { return nome + Options.DELIMITADOR + id; }
}
