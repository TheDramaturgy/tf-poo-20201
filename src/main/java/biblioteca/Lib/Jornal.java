package biblioteca.Lib;

import biblioteca.Dados.Options;
import biblioteca.Dados.Status;

import java.time.LocalDate;

public class Jornal extends Item {
    private LocalDate data;
    private int numero;
    private String diretor;

    public Jornal(String nome, int id, String tipo, Status disponibilidade, Cliente clienteEmPosse, Funcionario ultimoAEditar, LocalDate data, int numero, String diretor) {
        super(nome, id, tipo, disponibilidade, clienteEmPosse, ultimoAEditar);
        this.data = data;
        this.numero = numero;
        this.diretor = diretor;
    }

    public Jornal(String nome, LocalDate data, int numero, String diretor) {
        super(nome, "JORNAL");
        this.data = data;
        this.numero = numero;
        this.diretor = diretor;
    }

    public LocalDate getData() { return data; }
    public int getNumero() { return numero; }
    public String getDiretor() { return diretor; }

    public void setData(LocalDate data) { this.data = data; atualizarUltimoAEditar(); }
    public void setNumero(int numero) { this.numero = numero; atualizarUltimoAEditar(); }
    public void setDiretor(String diretor) { this.diretor = diretor; atualizarUltimoAEditar(); }

    @Override
    public String toString() {
        return  super.toString() + Options.DELIMITADOR +
                data + Options.DELIMITADOR +
                numero + Options.DELIMITADOR +
                diretor;
    }
}
