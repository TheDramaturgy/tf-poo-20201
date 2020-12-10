package testes;

import biblioteca.Exceptions.ClientLimitExceededException;
import biblioteca.Lib.Cliente;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ClienteTest {

    @Test
    public void deveriaPegar() throws ClientLimitExceededException {
        Cliente cliente = new Cliente("nome", 1, 6, 2);
        int valorEsperado = 7;
        int valorRetornado = cliente.pegar(false);
        assertEquals(valorEsperado, valorRetornado, 0);
    }

    @Test
    public void deveriaReservar() throws ClientLimitExceededException {
        Cliente cliente = new Cliente("nome", 1, 6, 2);
        int valorEsperado = 3;
        int valorRetornado = cliente.reservar();
        assertEquals(valorEsperado, valorRetornado, 0);
    }
}