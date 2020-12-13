package biblioteca.Lib;

import biblioteca.Exceptions.ClientLimitExceededException;
import biblioteca.Exceptions.IdNotFoundException;
import biblioteca.Dados.Status;
import javafx.collections.FXCollections;
import java.util.List;


public class Biblioteca {
    private List<Item> inventario = FXCollections.observableArrayList();
    private List<Cliente> clientes = FXCollections.observableArrayList();

    public List<Item> getInventario() { return inventario; }
    public void excluirItem(Item item) { inventario.remove(item); }
    public void addItem(Item item) { inventario.add(item); }

    public List<Cliente> getClientes() { return clientes; }

    public void excluirCliente(Cliente cliente) { clientes.remove(cliente); }
    public void addCliente(Cliente cliente) { clientes.add(cliente); }

    private Cliente encontrarCliente(int clienteID) {
        Cliente cliente = null;
        for (Cliente c : clientes) {
            if (c.getId() == clienteID) {
                cliente = c;
                break;
            }
        }
        return cliente;
    }

    public boolean emprestar(Item item, int clienteID) throws IdNotFoundException, ClientLimitExceededException {
        if (item.getDisponibilidade() == Status.DISPONIVEL) {
            item.setClienteEmPosse(encontrarCliente(clienteID));
            if (item.getClienteEmPosse() == null) {
                throw new IdNotFoundException("Cliente de id \""+clienteID+"\" não encontrado!");
            } else if (item.getClienteEmPosse().getId() != clienteID) {
                item.setClienteEmPosse(null);
                throw new IdNotFoundException("Falha ao encontrar ID do Cliente desejado!");
            } else {
                item.getClienteEmPosse().pegar(false);
                item.setDisponibilidade(Status.EMPRESTADO);
                return true;
            }
        }
        return false;
    }

    public boolean emprestar(Item item) throws ClientLimitExceededException {
        if (item.getDisponibilidade() == Status.RESERVADO) {
            item.getClienteEmPosse().pegar(true);
            item.setDisponibilidade(Status.EMPRESTADO);
            return true;
        }
        return false;
    }

    public void devolver(Item item) {
        item.setDisponibilidade(Status.DISPONIVEL);
        item.getClienteEmPosse().devolver(false);
        item.setClienteEmPosse(null);
    }

    public void reservar(Item item, int clienteID) throws IdNotFoundException, ClientLimitExceededException {
        item.setClienteEmPosse(encontrarCliente(clienteID));
        if (item.getClienteEmPosse() == null) {
            throw new IdNotFoundException("Cliente de id \""+clienteID+"\" não encontrado!");
        } else if (item.getClienteEmPosse().getId() != clienteID) {
            item.setClienteEmPosse(null);
            throw new IdNotFoundException("Falha ao encontrar ID do Cliente desejado!");
        } else {
            item.getClienteEmPosse().reservar();
            item.setDisponibilidade(Status.RESERVADO);
        }
    }

    public void cancelarReserva(Item item) {
        item.setDisponibilidade(Status.DISPONIVEL);
        item.getClienteEmPosse().devolver(true);
        item.setClienteEmPosse(null);
    }
}
