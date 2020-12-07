import biblioteca.Exceptions.ClientLimitExceededException;
import biblioteca.Exceptions.IdNotFoundException;
import biblioteca.Lib.Biblioteca;
import biblioteca.Lib.Cliente;
import biblioteca.Lib.Item;
import biblioteca.Lib.Livro;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BibliotecaTest {
    @Test
    public void deveriaEmprestar() throws IdNotFoundException, ClientLimitExceededException {
        Biblioteca bib = new Biblioteca();
        Item i = new Livro();
        bib.getInventario().add(i);
        bib.getClientes().add(new Cliente("nome", 0, 0, 0));

        boolean retornado = bib.emprestar(i, 0);

        assertTrue(retornado);
    }
}