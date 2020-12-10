package biblioteca.Lib;

import biblioteca.Exceptions.ClientLimitExceededException;
import biblioteca.Dados.Options;

public class Cliente {
    private int id;
    private String nome;
    private int livrosEmprestados;
    private int livrosReservados;

    public Cliente(String nome, int id, int livrosEmprestados, int livrosReservados) {
        this.nome = nome;
        this.id = id;
        this.livrosEmprestados = livrosEmprestados;
        this.livrosReservados = livrosReservados;
    }

    public Cliente(String nome) {
        this.nome = nome;
        id = Options.getClienteNewId();
        livrosEmprestados = 0;
        livrosReservados = 0;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getLivrosEmprestados() { return livrosEmprestados; }
    public int getLivrosReservados() { return livrosReservados; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setLivrosEmprestados(int livrosEmprestados) { this.livrosEmprestados = livrosEmprestados; }
    public void setLivrosReservados(int livrosReservados) { this.livrosReservados = livrosReservados; }

    public int pegar(boolean reserva) throws ClientLimitExceededException {
        if (livrosEmprestados < Options.MAX_LIVROS_EMPRESTADOS){
            livrosEmprestados++;
            if (reserva) livrosReservados--;
            return getLivrosEmprestados();
        } else {
            throw new ClientLimitExceededException("EMPRESTIMOS", id);
        }
    }

    public int reservar() throws ClientLimitExceededException {
        if (livrosReservados < Options.MAX_LIVROS_RESERVADOS){
            livrosReservados++;
            return getLivrosReservados();
        } else {
            throw new ClientLimitExceededException("RESERVAS", id);
        }
    }

    public void devolver(boolean reserva) {
        if (reserva) livrosReservados--;
        else livrosEmprestados--;
        if(livrosReservados < 0) livrosReservados = 0;
        if(livrosEmprestados < 0) livrosReservados = 0;
    }

    @Override
    public String toString() {
        return  nome + Options.DELIMITADOR +
                id + Options.DELIMITADOR +
                livrosEmprestados + Options.DELIMITADOR +
                livrosReservados;
    }

}
