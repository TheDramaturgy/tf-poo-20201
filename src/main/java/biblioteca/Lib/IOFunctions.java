package biblioteca.Lib;

import biblioteca.Exceptions.IdNotFoundException;
import biblioteca.Exceptions.InvalidStatusException;
import biblioteca.Dados.Options;
import biblioteca.Dados.Status;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class IOFunctions {
    public static void carregarRegistro(Biblioteca biblioteca, List<Funcionario> funcionarios) throws IOException, IdNotFoundException, InvalidStatusException {
        biblioteca.getInventario().clear();
        biblioteca.getClientes().clear();
        InputStream is = null;
        try {
            is = new FileInputStream(Options.SAVED_LOCATION);
        } catch (FileNotFoundException e) {
            return;
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        String s = br.readLine();
        if (s != null) {
            Scanner scan = new Scanner(s);
            Options.setClienteNewId(scan.nextInt());
            Options.setItemNewId(scan.nextInt());
            scan.close();
        } else {
            return;
        }

        // INICIALIZA CLIENTES
        for (s = br.readLine(); !s.equals(Options.CLIENT_LIMIT); s = br.readLine()) {
            Scanner scan = new Scanner(s).useDelimiter("\\s*"+Options.DELIMITADOR+"\\s*");
            biblioteca.addCliente(new Cliente(
                    scan.next(),
                    scan.nextInt(),
                    scan.nextInt(),
                    scan.nextInt()
            ));
            scan.close();
        }

        // INICIALIZA INVENTARIO
        for (s = br.readLine(); !s.equals(Options.ITEM_LIMIT); s = br.readLine()) {
            Scanner scan = new Scanner(s).useDelimiter("\\s*"+Options.DELIMITADOR+"\\s*");
            String tipo = scan.next();
            int i = -1;
            if (tipo.equals(Options.LIVRO)) {
                biblioteca.addItem(new Livro(
                        scan.next(),
                        scan.nextInt(),
                        tipo,
                        Status.parseStatus(scan.next()),
                        ((i = scan.nextInt()) != -1 ? encontrarCliente(biblioteca.getClientes(), i) : null),
                        ((i = scan.nextInt()) != -1 ? encontrarFuncionario(funcionarios, i) : null),
                        scan.next(),
                        scan.next(),
                        scan.next()
                ));
            } else if (tipo.equals(Options.REVISTA)) {
                biblioteca.addItem(new Revista(
                        scan.next(),
                        scan.nextInt(),
                        tipo,
                        Status.parseStatus(scan.next()),
                        ((i = scan.nextInt()) != -1 ? encontrarCliente(biblioteca.getClientes(), i) : null),
                        ((i = scan.nextInt()) != -1 ? encontrarFuncionario(funcionarios, i) : null),
                        LocalDate.parse(scan.next()),
                        scan.nextInt(),
                        scan.next()
                ));
            } else if (tipo.equals(Options.JORNAL)) {
                biblioteca.addItem(new Jornal(
                        scan.next(),
                        scan.nextInt(),
                        tipo,
                        Status.parseStatus(scan.next()),
                        ((i = scan.nextInt()) != -1 ? encontrarCliente(biblioteca.getClientes(), i) : null),
                        ((i = scan.nextInt()) != -1 ? encontrarFuncionario(funcionarios, i) : null),
                        LocalDate.parse(scan.next()),
                        scan.nextInt(),
                        scan.next()
                ));
            }
            scan.close();
        }

        br.close();
    }

    public static void salvarRegistro(Biblioteca biblioteca) throws FileNotFoundException {
        OutputStream os = new FileOutputStream(Options.SAVED_LOCATION);

        PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
        String save = new String();
        save += String.valueOf(Options.getClienteNewId()) + ' ' +
                String.valueOf(Options.getItemNewId()) + '\n';
        for (Cliente c : biblioteca.getClientes()) {
            save += c.toString() + '\n';
        }
        save += Options.CLIENT_LIMIT + '\n';
        for (Item i : biblioteca.getInventario()) {
            save += i.toString() + '\n';
        }
        save += Options.ITEM_LIMIT;
        pw.print(save);
        pw.close();
    }

    private static Funcionario encontrarFuncionario(List<Funcionario> funcionarios, int id) throws IdNotFoundException {
        Funcionario f_retorno = null;
        for(Funcionario f : funcionarios) if (f.getId() == id) f_retorno = f;
        if(f_retorno == null) throw new IdNotFoundException("IOFunctions:encontrarFuncionario: Funcionario de id \""+id+"\" não encontrado!");
        return f_retorno;
    }

    private static Cliente encontrarCliente(List<Cliente> clientes, int id) throws IdNotFoundException {
        Cliente c_retorno = null;
        for(Cliente c : clientes) if (c.getId() == id) c_retorno = c;
        if(c_retorno == null) throw new IdNotFoundException("IOFunctions.java:encontrarCliente: Cliente de id \""+id+"\" não encontrado!");
        return c_retorno;
    }

    public static void lerDadosEquipe(ObservableList<Funcionario> dadosEquipe) {
        try {
            InputStream is;
            try { is = new FileInputStream(Options.EQUIPE_LOCATION); }
            catch (FileNotFoundException e) {
                return;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String s = br.readLine();
            if(s != null) {
                Scanner scan = new Scanner(s);
                Options.setFuncionarioNewId(scan.nextInt());
                for (s = br.readLine(); s != null; s = br.readLine()) {
                    scan = new Scanner(s).useDelimiter("\\s*"+Options.DELIMITADOR+"\\s*");
                    dadosEquipe.add(new Funcionario(
                            scan.next(),
                            scan.nextInt()
                    ));
                    scan.close();
                }
                br.close();
            } else {
                return;
            }
        } catch (Exception e) {
            System.err.println("Não foi possível carregar os dados da equipe!");
            e.printStackTrace();
        }
    }

    public static void salvarDadosEquipe(ObservableList<Funcionario> dadosEquipe) throws FileNotFoundException {
        OutputStream os = new FileOutputStream(Options.EQUIPE_LOCATION);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
        String save = new String();
        save += String.valueOf(Options.getFuncionarioNewId()) + '\n';
        for (Funcionario f : dadosEquipe) {
            save += f.toString() + '\n';
        }
        pw.print(save);
        pw.close();
    }
}
