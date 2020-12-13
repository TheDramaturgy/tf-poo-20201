package biblioteca.GUI;

import biblioteca.Exceptions.InvalidItemTypeException;
import biblioteca.IOFunctions;
import biblioteca.Lib.*;
import biblioteca.Dados.Options;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class ComponentCreator {

    protected static BorderPane createClientePane(Stage stage, TableView<Cliente> clientes, Biblioteca biblioteca) {
        Button btnNovo = new Button("Novo");
        btnNovo.setMaxWidth(Double.MAX_VALUE);
        btnNovo.setMinHeight(40);
        btnNovo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SecondaryWindow.showNewClientWindow(stage, biblioteca);
            }
        });

        Button btnExcluir = new Button("Excluir");
        btnExcluir.setMaxWidth(Double.MAX_VALUE);
        btnExcluir.setMinHeight(40);
        btnExcluir.disableProperty().bind(Bindings.isEmpty(clientes.getSelectionModel().getSelectedItems()));
        btnExcluir.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Cliente cliente = clientes.getSelectionModel().getSelectedItem();
                if (cliente.getLivrosEmprestados() > 0) {
                    SecondaryWindow.showWarning("Esse cliente não pode ser excluído pois possui livros emprestados!");
                } else if (cliente.getLivrosReservados() > 0) {
                    SecondaryWindow.showWarning("Esse cliente não pode ser excluído pois possui livros reservados!");
                } else {
                    biblioteca.excluirCliente(cliente);
                    clientes.refresh();
                }
            }
        });
        btnExcluir.setMaxWidth(Double.MAX_VALUE);

        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(btnNovo, btnExcluir);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.prefWidthProperty().bind(stage.widthProperty().multiply(0.15f));
        vBox.setStyle("-fx-background-color: #3D3D3D");

        BorderPane border = new BorderPane();
        border.setCenter(clientes);
        border.setLeft(vBox);

        return border;
    }

    protected static VBox createLoginVBox(Stage stage, TableView<Funcionario> equipe) {

        /*---------- BUTTONS ----------*/
        Button btnNovo = new Button("Novo");
        btnNovo.setMaxWidth(60);
        btnNovo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.setScene(SceneManager.getInstance().createRegisterScene(stage, equipe.getItems()));
            }
        });
        Button btnEntrar = new Button("Entrar");
        btnEntrar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Options.setFuncionarioAtivo(equipe.getSelectionModel().getSelectedItem());
                if (Options.getFuncionarioAtivo() != null) {
                    stage.setScene(SceneManager.getInstance().getMainScene());
                    stage.centerOnScreen();
                } else {
                    SecondaryWindow.showWarning("Um funcionario deve ser selecionado!");
                }
            }
        });
        btnEntrar.setMaxWidth(Double.MAX_VALUE);


        /*---------- CONTAINERS ----------*/
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPadding(new Insets(20,20,20,20));

        HBox hBox = new HBox();
        hBox.getChildren().addAll(btnNovo, btnEntrar);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        hBox.setHgrow(btnNovo, Priority.ALWAYS);
        hBox.setHgrow(btnEntrar, Priority.ALWAYS);

        VBox vBox = new VBox();
        vBox.getChildren().add(equipe);
        vBox.getChildren().add(hBox);
        return vBox;
    }

    protected static VBox createRegisterVBox(Stage stage, ObservableList<Funcionario> dadosEquipe) {
        Text txtNome = new Text("Nome");
        TextField tfNome = new TextField();

        /*---------- BUTTONS ----------*/
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setPrefHeight(40);
        btnCancelar.setMaxWidth(100);
        btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.setScene(SceneManager.getInstance().getLoginScene());
            }
        });

        Button btnRegistrar = new Button("Registrar");
        btnRegistrar.setPrefHeight(40);
        btnRegistrar.setMaxWidth(200);
        btnRegistrar.setMinWidth(200);
        btnRegistrar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!tfNome.getText().equals("")) {
                    dadosEquipe.add(new Funcionario(tfNome.getText()));
                    stage.setScene(SceneManager.getInstance().getLoginScene());
                } else {
                    SecondaryWindow.showWarning("O campo \"Nome\" deve ser preenchido!");
                }
            }
        });

        /*---------- CONTAINERS ----------*/
        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(txtNome, tfNome);

        HBox hBox = new HBox(5);
        hBox.getChildren().addAll(btnCancelar, btnRegistrar);
        hBox.setAlignment(Pos.CENTER);
        hBox.setHgrow(btnRegistrar, Priority.ALWAYS);
        hBox.setHgrow(btnCancelar, Priority.ALWAYS);

        VBox mainVBox = new VBox(20);
        mainVBox.getChildren().addAll(vBox, hBox);
        mainVBox.setPadding(new Insets(20,10,10,10));
        return mainVBox;
    }

    protected static BorderPane createMainBorderPane(Stage stage, TableView<Cliente> clientes, TableView<Item> inventario, Biblioteca biblioteca) {

        /*---------- MENUBAR ----------*/
        MenuBar menuBar = new MenuBar();
        MenuItem menuItemSobre = new MenuItem("Sobre");
        menuItemSobre.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String message = new String("Trabalho Final POO 2020-1" +
                        "\nArthur Cavalcante de Andrade - 201801479" +
                        "\nJoão Pedro Campos Constantino - 201800087" +
                        "\nRoberta Assis de Carvalho - 201904254" +
                        "\nWilliam Teixeira Pires Junior - 201801546"
                );
                SecondaryWindow.showMessage("Sobre", message);
            }
        });
        Menu menuOpcoes = new Menu("Opções");
        MenuItem salvarMenuItem = new MenuItem("Salvar");
        salvarMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    IOFunctions.salvarRegistro(biblioteca);
                } catch (FileNotFoundException e) {
                    SecondaryWindow.showError("Não foi possível salvar registro da biblioteca!");
                    e.printStackTrace();
                }
            }
        });
        MenuItem trocarFuncionarioMenuItem = new MenuItem("Desconectar");
        trocarFuncionarioMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (SecondaryWindow.showSaveMessage(biblioteca, "Deseja salvar as alterações antes de sair?")) {
                    stage.setScene(SceneManager.getInstance().getLoginScene());
                }
            }
        });
        menuOpcoes.getItems().addAll(salvarMenuItem, trocarFuncionarioMenuItem, menuItemSobre);
        menuBar.getMenus().addAll(menuOpcoes);

        /*---------- BUTTONS ----------*/
        Button btnNovo = new Button();
        btnNovo.setText("Novo");
        btnNovo.setMaxWidth(Double.MAX_VALUE);
        btnNovo.setMinHeight(40);
        btnNovo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SecondaryWindow.showNewItemWindow(stage, inventario, biblioteca);
            }
        });

        Button btnExcluir = new Button();
        btnExcluir.setText("Excluir");
        btnExcluir.setMaxWidth(Double.MAX_VALUE);
        btnExcluir.setMinHeight(40);
        btnExcluir.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Item item = inventario.getSelectionModel().getSelectedItem();
                biblioteca.excluirItem(item);
                inventario.refresh();
            }
        });

        Button btnConsultar = new Button();
        btnConsultar.setText("Consultar");
        btnConsultar.setMaxWidth(Double.MAX_VALUE);
        btnConsultar.setMinHeight(40);
        btnConsultar.disableProperty().bind(Bindings.isEmpty(inventario.getSelectionModel().getSelectedItems()));
        btnConsultar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int index = inventario.getSelectionModel().getSelectedIndex();
                try { SecondaryWindow.showDetalhesWindow(stage, inventario.getSelectionModel().getSelectedItem(), inventario, clientes, biblioteca);
                } catch (InvalidItemTypeException e) {
                    System.err.println(e.getMessage());
                }
            }
        });

        /*---------- CONTAINERS ----------*/
        VBox vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.prefWidthProperty().bind(stage.widthProperty().multiply(0.15f));
        vBox.setStyle("-fx-background-color: #3D3D3D");
        vBox.getChildren().addAll(btnNovo, btnExcluir, btnConsultar);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(inventario);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        BorderPane border = new BorderPane();
        border.setCenter(scrollPane);
        border.setRight(vBox);

        TabPane tabPane = new TabPane();
        Tab tabInventario = new Tab("Inventário");
        Tab tabClientes = new Tab("Clientes");
        tabInventario.setContent(border);
        tabInventario.setClosable(false);
        tabClientes.setContent(createClientePane(stage, clientes, biblioteca));
        tabClientes.setClosable(false);

        tabPane.getTabs().addAll(tabInventario, tabClientes);
        BorderPane mainBorder = new BorderPane();
        mainBorder.setCenter(tabPane);
        mainBorder.setTop(menuBar);
        return mainBorder;
    }

    protected static TableView<Cliente> createClienteTable(ObservableList<Cliente> dados) {
        TableView<Cliente> table = new TableView();
        TableColumn col_id = new TableColumn("ID");
        col_id.setMaxWidth(70);
        col_id.setMinWidth(70);
        col_id.setCellValueFactory(new PropertyValueFactory<Cliente, String>("id"));
        TableColumn col_nome = new TableColumn("Nome");
        col_nome.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome"));
        TableColumn col_emprestados = new TableColumn("Qtd em Emprestimo");
        col_emprestados.setCellValueFactory(new PropertyValueFactory<Cliente, String>("livrosEmprestados"));
        TableColumn col_reservados = new TableColumn("Qtd em Reserva");
        col_reservados.setCellValueFactory(new PropertyValueFactory<Cliente, String>("livrosReservados"));

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setItems(dados);
        table.getColumns().addAll(col_id, col_nome, col_emprestados, col_reservados);
        return table;
    }

    protected static TableView<Funcionario> createFuncionarioTable(ObservableList<Funcionario> dados) {
        TableView<Funcionario> table = new TableView();
        TableColumn col_id = new TableColumn("ID");
        col_id.setMaxWidth(50);
        col_id.setMinWidth(50);
        col_id.setCellValueFactory(new PropertyValueFactory<Item, String>("id"));
        TableColumn col_nome = new TableColumn("Nome");
        col_nome.setCellValueFactory(new PropertyValueFactory<Item, String>("nome"));

        table.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        table.prefHeightProperty().set(150);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPadding(new Insets(20,20,20,20));
        table.setItems(dados);
        table.getColumns().addAll(col_id, col_nome);
        return table;
    }

    protected static TableView<Item> createInventarioTable(ObservableList<Item> dados) {
        TableView<Item> table = new TableView();
        TableColumn col_id = new TableColumn("ID");
        col_id.setMaxWidth(70);
        col_id.setMinWidth(70);
        col_id.setCellValueFactory(new PropertyValueFactory<Item, String>("id"));
        TableColumn col_nome = new TableColumn("Nome");
        col_nome.setCellValueFactory(new PropertyValueFactory<Item, String>("nome"));
        TableColumn col_tipo = new TableColumn("Tipo");
        col_tipo.setCellValueFactory(new PropertyValueFactory<Item, String>("tipo"));
        TableColumn col_disp = new TableColumn("Disponibilidade");
        col_disp.setCellValueFactory(new PropertyValueFactory<Item, String>("disponibilidade"));

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setItems(dados);
        table.getColumns().addAll(col_id, col_nome, col_tipo, col_disp);
        return table;
    }
}
