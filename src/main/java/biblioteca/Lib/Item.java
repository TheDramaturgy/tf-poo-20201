package biblioteca.Lib;

import biblioteca.Dados.Options;
import biblioteca.Dados.Status;

public abstract class Item {
    private String nome;
    private int id;
    private String tipo;
    private Status disponibilidade;
    private Cliente clienteEmPosse;
    private Funcionario ultimoAEditar;

    public Item() { this.disponibilidade = Status.DISPONIVEL; }

    public Item(String nome, String tipo) {
        this.nome = nome;
        this.id = Options.getItemNewId();
        this.tipo = tipo;
        this.disponibilidade = Status.DISPONIVEL;
        this.clienteEmPosse = null;
        atualizarUltimoAEditar();
    }

    protected Item(String nome, int id, String tipo, Status disponibilidade, Cliente clienteEmPosse, Funcionario ultimoAEditar) {
        this.nome = nome;
        this.id = id;
        this.tipo = tipo;
        this.disponibilidade = disponibilidade;
        this.clienteEmPosse = clienteEmPosse;
        this.ultimoAEditar = ultimoAEditar;
    }

    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public int getId() { return id; }
    public Status getDisponibilidade() { return disponibilidade; }
    public Cliente getClienteEmPosse() { return clienteEmPosse; }
    public Funcionario getUltimoAEditar() { return ultimoAEditar; }

    public void setNome(String nome) { this.nome = nome; atualizarUltimoAEditar(); }
    public void setDisponibilidade(Status disponibilidade) { this.disponibilidade = disponibilidade; atualizarUltimoAEditar(); }
    public void setClienteEmPosse(Cliente clienteEmPosse) { this.clienteEmPosse = clienteEmPosse; atualizarUltimoAEditar(); }

    protected void atualizarUltimoAEditar () { this.ultimoAEditar = Options.getFuncionarioAtivo(); }

    @Override
    public String toString() {
        String cliente, funcionario;
        if (clienteEmPosse == null) cliente = "-1";
        else cliente = String.valueOf(clienteEmPosse.getId());
        if (ultimoAEditar == null) funcionario = "-1";
        else funcionario = String.valueOf(ultimoAEditar.getId());
        return  tipo + Options.DELIMITADOR +
                nome + Options.DELIMITADOR +
                id + Options.DELIMITADOR +
                String.valueOf(disponibilidade) + Options.DELIMITADOR +
                cliente + Options.DELIMITADOR +
                funcionario;
    }
}