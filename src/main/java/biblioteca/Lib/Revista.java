package biblioteca.Lib;

import biblioteca.Dados.Options;
import biblioteca.Dados.Status;

import java.time.LocalDate;

public class Revista extends Item {
    private LocalDate data;
    private int edicao;
    private String editora;

    public Revista(String nome, int id, String tipo, Status disponibilidade, Cliente clienteEmPosse, Funcionario ultimoAEditar, LocalDate data, int edicao, String editora) {
        super(nome, id, tipo, disponibilidade, clienteEmPosse, ultimoAEditar);
        this.data = data;
        this.edicao = edicao;
        this.editora = editora;
    }

    public Revista(String nome, LocalDate data, int edicao, String editora) {
        super(nome, "REVISTA");
        this.data = data;
        this.edicao = edicao;
        this.editora = editora;
    }

    public LocalDate getData() { return data; }
    public int getEdicao() { return edicao; }
    public String getEditora() { return editora; }

    public void setData(LocalDate data) { this.data = data; atualizarUltimoAEditar(); }
    public void setEdicao(int edicao) { this.edicao = edicao; atualizarUltimoAEditar(); }
    public void setEditora(String editora) { this.editora = editora; atualizarUltimoAEditar(); }

    @Override
    public String toString() {
        return  super.toString() + Options.DELIMITADOR +
                data + Options.DELIMITADOR +
                edicao + Options.DELIMITADOR +
                editora;
    }
}
