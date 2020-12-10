package biblioteca.Lib;

import biblioteca.Dados.Options;
import biblioteca.Dados.Status;

public class Livro extends Item {
    private String isbn;
    private String autor;
    private String editora;

    public Livro() { }

    protected Livro(String nome, int id, String tipo, Status disponibilidade, Cliente clienteEmPosse, Funcionario ultimoAEditar, String isbn, String autor, String editora) {
        super(nome, id, tipo, disponibilidade, clienteEmPosse, ultimoAEditar);
        this.isbn = isbn;
        this.autor = autor;
        this.editora = editora;
    }

    public String getIsbn() { return isbn; }
    public String getAutor() { return autor; }
    public String getEditora() { return editora; }

    public void setIsbn(String isbn) { this.isbn = isbn; atualizarUltimoAEditar(); }
    public void setAutor(String autor) { this.autor = autor; atualizarUltimoAEditar(); }
    public void setEditora(String editora) { this.editora = editora; atualizarUltimoAEditar(); }

    public Livro(String nome, String isbn, String autor, String editora) {
        super(nome, "LIVRO");
        this.isbn = isbn;
        this.autor = autor;
        this.editora = editora;
    }

    @Override
    public String toString() {
        return  super.toString() + Options.DELIMITADOR +
                isbn + Options.DELIMITADOR +
                autor + Options.DELIMITADOR +
                editora;
    }
}
