package biblioteca.GUI;

import biblioteca.Exceptions.ClientLimitExceededException;
import biblioteca.Exceptions.IdNotFoundException;
import biblioteca.Exceptions.InvalidItemTypeException;
import biblioteca.IOFunctions;
import biblioteca.Lib.*;
import biblioteca.Dados.Options;
import biblioteca.Dados.Status;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SecondaryWindow {

    private static final ObservableList tipos = FXCollections.observableArrayList(
            Options.LIVRO,
            Options.REVISTA,
            Options.JORNAL
    );

    public static void showDetalhesWindow(Stage stage, Item item, TableView<Item> inventario, TableView<Cliente> clientes, Biblioteca biblioteca) throws InvalidItemTypeException {
        Stage newItemWindow = new Stage();

        CheckBox cbEditar = new CheckBox("Editar");
        cbEditar.setSelected(false);
        ArrayList<Control> controls = new ArrayList();
        GridPane grid = null;

        /*---------- TOP GRID PANE ----------*/
        GridPane topGrid = new GridPane();
        topGrid.setHgap(4);
        topGrid.setVgap(3);
        topGrid.setAlignment(Pos.CENTER);
        topGrid.setPadding(new Insets(10, 10, 10, 10));

        Text txtNome = new Text("Nome");
        topGrid.add(txtNome, 0, 0);
        TextField tfNome = new TextField();
        tfNome.setText(item.getNome());
        topGrid.add(tfNome, 0, 1, 1, 1);

        Text txtTipo = new Text("Tipo");
        txtTipo.maxWidth(Double.MAX_VALUE);
        topGrid.add(txtTipo, 2, 0);
        ComboBox cbTipo = new ComboBox(tipos);
        cbTipo.setDisable(true);
        topGrid.add(cbTipo, 2, 1);

        controls.add(tfNome);

        /*---------- LIVRO ----------*/
        if (item.getTipo().equals(tipos.get(0))) {
            GridPane livroGrid = new GridPane();
            livroGrid.setVgap(6);
            livroGrid.setHgap(6);
            livroGrid.setAlignment(Pos.CENTER);
            livroGrid.setPadding(new Insets(10, 10, 10, 10));
            Text txtIsbn = new Text("ISBN");
            livroGrid.add(txtIsbn, 0, 1);
            TextField tfIsbn = new TextField();
            tfIsbn.setText(((Livro) item).getIsbn());
            livroGrid.add(tfIsbn, 1, 1);

            Text txtAutor = new Text("Autor");
            livroGrid.add(txtAutor, 0, 2);
            TextField tfAutor = new TextField();
            tfAutor.setText(((Livro) item).getAutor());
            livroGrid.add(tfAutor, 1, 2);

            Text txtEditoraLivro = new Text("Editora");
            livroGrid.add(txtEditoraLivro, 0, 3);
            TextField tfEditoraLivro = new TextField();
            tfEditoraLivro.setText(((Livro) item).getEditora());
            livroGrid.add(tfEditoraLivro, 1, 3);

            cbTipo.setValue(tipos.get(0));
            controls.add(tfIsbn);
            controls.add(tfAutor);
            controls.add(tfEditoraLivro);
            grid = livroGrid;
        }

        /*---------- REVISTA ----------*/
        if (item.getTipo().equals(tipos.get(1))) {
            GridPane revistaGrid = new GridPane();
            revistaGrid.setVgap(6);
            revistaGrid.setHgap(6);
            revistaGrid.setPadding(new Insets(10, 10, 10, 10));
            Text txtDataRevista = new Text("Data");
            revistaGrid.add(txtDataRevista, 0, 1);
            DatePicker dpDataRevista = new DatePicker();
            dpDataRevista.setValue(((Revista)item).getData());
            dpDataRevista.setConverter(new StringConverter<LocalDate>() {
                DateTimeFormatter dateTimeFormatter =
                        DateTimeFormatter.ofPattern("dd/MM/yyyy");

                @Override
                public String toString(LocalDate localDate) {
                    if(localDate != null) {
                        return dateTimeFormatter.format(localDate);
                    } else {
                        return "null";
                    }
                }

                @Override
                public LocalDate fromString(String s) {
                    if (s != null && !s.equals("null")) {
                        return LocalDate.parse(s, dateTimeFormatter);
                    } else {
                        return null;
                    }
                }
            });
            revistaGrid.add(dpDataRevista, 1, 1);

            Text txtEdicao = new Text("Edição");
            revistaGrid.add(txtEdicao, 0, 2);
            TextField tfEdicao = new TextField();
            tfEdicao.setText(String.valueOf(((Revista) item).getEdicao()));
            tfEdicao.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    if (!t1.matches("\\d*")) {
                        tfEdicao.setText(t1.replaceAll("[^\\d]", ""));
                    }
                }
            });
            revistaGrid.add(tfEdicao, 1, 2);

            Text txtEditoraRevista = new Text("Editora");
            revistaGrid.add(txtEditoraRevista, 0, 3);
            TextField tfEditoraRevista = new TextField();
            tfEditoraRevista.setText(((Revista) item).getEditora());
            revistaGrid.add(tfEditoraRevista, 1, 3);

            cbTipo.setValue(tipos.get(1));
            controls.add(dpDataRevista);
            controls.add(tfEdicao);
            controls.add(tfEditoraRevista);
            grid = revistaGrid;
        }

        /*---------- JORNAL ----------*/
        if (item.getTipo().equals(tipos.get(2))) {
            GridPane jornalGrid = new GridPane();
            jornalGrid.setVgap(6);
            jornalGrid.setHgap(6);
            jornalGrid.setPadding(new Insets(10, 10, 10, 10));
            Text txtDataJornal = new Text("Data");
            jornalGrid.add(txtDataJornal, 0, 1);
            DatePicker dpDataJornal = new DatePicker();
            dpDataJornal.setValue(((Jornal) item).getData());
            jornalGrid.add(dpDataJornal, 1, 1);

            Text txtNumero = new Text("Número");
            jornalGrid.add(txtNumero, 0, 2);
            TextField tfNumero = new TextField();
            tfNumero.setText(String.valueOf(((Jornal) item).getNumero()));
            tfNumero.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    if (!t1.matches("\\d*")) {
                        tfNumero.setText(t1.replaceAll("[^\\d]", ""));
                    }
                }
            });
            jornalGrid.add(tfNumero, 1, 2);

            Text txtDiretor = new Text("Diretor");
            jornalGrid.add(txtDiretor, 0, 3);
            TextField tfDiretor = new TextField();
            tfDiretor.setText(((Jornal) item).getDiretor());
            jornalGrid.add(tfDiretor, 1, 3);

            cbTipo.setValue(tipos.get(2));
            controls.add(dpDataJornal);
            controls.add(tfNumero);
            controls.add(tfDiretor);
            grid = jornalGrid;
        }


        /*---------- BUTTON EDITAR ----------*/
        Button btnEditar = new Button("Editar");
        btnEditar.setMaxWidth(Double.MAX_VALUE);
        btnEditar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                boolean sucesso = true;
                TextField tfNome = (TextField) controls.get(0);
                if (tfNome.getText().equals("")) {
                    sucesso = false;
                    showWarning("Todos os campos devem ser preenchidos!");
                } else {
                    item.setNome(tfNome.getText());
                }
                if (item.getTipo().equals(tipos.get(0)) && sucesso) {
                    TextField tf = ((TextField)controls.get(1));
                    TextField tf2 = ((TextField)controls.get(2));
                    TextField tf3 = ((TextField)controls.get(3));
                    if (tf.getText().equals("") || tf2.getText().equals("") || tf3.getText().equals("")) {
                        sucesso = false;
                        showWarning("Todos os campos devem ser preenchidos!");
                    } else {
                        ((Livro) item).setIsbn(tf.getText());
                        ((Livro) item).setAutor(tf2.getText());
                        ((Livro) item).setEditora(tf3.getText());
                    }
                }
                if (item.getTipo().equals(tipos.get(1)) && sucesso) {
                    DatePicker dp = ((DatePicker)controls.get(1));
                    dp.setValue(dp.getConverter().fromString(dp.getEditor().getText()));
                    TextField tf = ((TextField)controls.get(2));
                    TextField tf2 = ((TextField)controls.get(3));
                    if (dp.getEditor().getText().equals("") || tf.getText().equals("") || tf2.getText().equals("")) {
                        sucesso = false;
                        showWarning("Todos os campos devem ser preenchidos!");
                    } else {
                        ((Revista) item).setData(((DatePicker) controls.get(1)).getValue());
                        ((Revista) item).setEdicao(Integer.parseInt(tf.getText()));
                        ((Revista) item).setEditora(tf2.getText());
                    }
                }
                if (item.getTipo().equals(tipos.get(2)) && sucesso) {
                    DatePicker dp = ((DatePicker)controls.get(1));
                    dp.setValue(dp.getConverter().fromString(dp.getEditor().getText()));
                    TextField tf = ((TextField)controls.get(2));
                    TextField tf2 = ((TextField)controls.get(3));
                    if (dp.getEditor().getText().equals("") || tf.getText().equals("") || tf2.getText().equals("")) {
                        sucesso = false;
                        showWarning("Todos os campos devem ser preenchidos!");
                    } else {
                        ((Jornal) item).setData(dp.getValue());
                        ((Jornal) item).setNumero(Integer.parseInt(tf.getText()));
                        ((Jornal) item).setDiretor(tf2.getText());
                    }
                }

                inventario.refresh();
                if (sucesso) newItemWindow.close();
            }
        });
        controls.add(btnEditar);

        for (Control c : controls) {
            c.setDisable(true);
        }

        /*---------- TEXT FIELDS ----------*/

        TextField tfDisp = new TextField();
        tfDisp.setEditable(false);
        tfDisp.setText(String.valueOf(item.getDisponibilidade()));
        tfDisp.setAlignment(Pos.CENTER);
        tfDisp.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;" +
                "-fx-text-box-border: transparent; -fx-background-insets: 0;");

        Text txtUltimaEdicao = new Text("Ultimo a alterar");
        TextField tfUltimaEdicao = new TextField();
        tfUltimaEdicao.setEditable(false);
        tfUltimaEdicao.setText(new String(String.valueOf(item.getUltimoAEditar().getId()) +
                '_' + item.getUltimoAEditar().getNome()));
        tfUltimaEdicao.setAlignment(Pos.CENTER);
        tfUltimaEdicao.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;" +
                "-fx-text-box-border: transparent; -fx-background-insets: 0;");

        Text txCliente = new Text("Cliente");
        TextField tfCliente = new TextField();
        tfCliente.setAlignment(Pos.CENTER);

        TextField tfPosse = new TextField();
        tfPosse.setEditable(false);
        tfPosse.setAlignment(Pos.CENTER);
        tfPosse.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;" +
                "-fx-text-box-border: transparent; -fx-background-insets: 0;");

        /*---------- BUTTON EMPRESTAR ----------*/
        Button btnEmprestar = new Button("Emprestar");
        btnEmprestar.setMaxWidth(Double.MAX_VALUE);
        btnEmprestar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                boolean sucesso = true;
                if (item.getDisponibilidade() == Status.RESERVADO) {
                    try {
                        biblioteca.emprestar(item);
                    } catch (ClientLimitExceededException e) {
                        showError(e.getMessage());
                        sucesso = false;
                    }
                } else if (tfCliente.getText().equals("")) {
                    showWarning("É necessário informar o ID do cliente para o empréstimo!");
                    sucesso = false;
                } else {
                    try {
                        biblioteca.emprestar(item, Integer.parseInt(tfCliente.getText()));
                    } catch (IdNotFoundException e) {
                        showError(e.getMessage());
                        sucesso = false;
                    } catch (ClientLimitExceededException e) {
                        showError(e.getMessage());
                        sucesso = false;
                    }
                }
                inventario.refresh();
                clientes.refresh();
                if (sucesso) newItemWindow.close();
            }
        });

        /*---------- BUTTON RESERVAR ----------*/
        Button btnReservar = new Button("Reservar");
        btnReservar.setMaxWidth(Double.MAX_VALUE);
        btnReservar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!tfCliente.getText().equals("")) {
                    try {
                        biblioteca.reservar(item, Integer.parseInt(tfCliente.getText()));
                        inventario.refresh();
                        clientes.refresh();
                        newItemWindow.close();
                    } catch (IdNotFoundException e) {
                        showError(e.getMessage());
                    } catch (ClientLimitExceededException e) {
                        showError(e.getMessage());
                    }
                } else {
                    showWarning("É necessário informar o ID do cliente para a reserva!");
                }
            }
        });

        /*---------- BUTTON CANCELAR ----------*/
        Button btnCancelar = new Button("Cancelar Reserva");
        btnCancelar.setMaxWidth(Double.MAX_VALUE);
        btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                biblioteca.cancelarReserva(item);
                inventario.refresh();
                clientes.refresh();
                newItemWindow.close();
            }
        });

        /*---------- BUTTON DEVOLVER ----------*/
        Button btnDevolver = new Button("Devolver");
        btnDevolver.setMaxWidth(Double.MAX_VALUE);
        btnDevolver.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                biblioteca.devolver(item);
                inventario.refresh();
                clientes.refresh();
                newItemWindow.close();
            }
        });

        List<String> aux = new ArrayList();

        /*---------- COMBOBOX EDITAR ACTION LISTENER ----------*/
        cbEditar.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) {
                    aux.clear();
                    for (Control c : controls) {
                        c.setDisable(false);
                        if (c instanceof DatePicker) {
                            aux.add(((DatePicker)c).getEditor().getText());
                        } else if (c instanceof TextField) {
                            aux.add(((TextField)c).getText());
                        }
                    }
                    btnEmprestar.setDisable(true);
                    btnDevolver.setDisable(true);
                    btnCancelar.setDisable(true);
                    btnReservar.setDisable(true);
                    tfCliente.setDisable(true);
                } else {
                    for (Control c : controls) {
                        c.setDisable(true);
                        if (c instanceof DatePicker) {
                            ((DatePicker)c).getEditor().setText(aux.remove(0));
                        } else if (c instanceof TextField){
                            ((TextField)c).setText(aux.remove(0));
                        }
                    }
                    btnEmprestar.setDisable(false);
                    btnDevolver.setDisable(false);
                    btnCancelar.setDisable(false);
                    btnReservar.setDisable(false);
                    tfCliente.setDisable(false);
                }
            }
        });

        /*---------- CONTAINERS ----------*/
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(cbEditar, topGrid);
        if (grid != null) vBox.getChildren().add(grid);
        else throw new InvalidItemTypeException(item.getTipo());
        vBox.getChildren().addAll(tfDisp, txtUltimaEdicao, tfUltimaEdicao);
        if(item.getDisponibilidade() == Status.DISPONIVEL) {
            txCliente.setText("Client ID");
            tfCliente.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    if (!t1.matches("\\d*")) {
                        tfCliente.setText(t1.replaceAll("[^\\d]", ""));
                    }
                }
            });
            vBox.getChildren().addAll(txCliente, tfCliente, btnEmprestar, btnReservar);
        } else if (item.getDisponibilidade() == Status.EMPRESTADO) {
            tfPosse.setText(String.valueOf(item.getClienteEmPosse().getId())+
                    "_"+String.valueOf(item.getClienteEmPosse().getNome()));
            vBox.getChildren().addAll(txCliente, tfPosse, btnDevolver);
        } else if (item.getDisponibilidade() == Status.RESERVADO) {
            tfPosse.setText(String.valueOf(item.getClienteEmPosse().getId())+
                    "_"+String.valueOf(item.getClienteEmPosse().getNome()));
            vBox.getChildren().addAll(txCliente, tfPosse, btnEmprestar, btnCancelar);
        }
        vBox.getChildren().add(btnEditar);
        vBox.setMaxHeight(Double.MAX_VALUE);
        vBox.setPadding(new Insets(10,10,10,10));

        Scene cena = new Scene(vBox);
        newItemWindow.setScene(cena);
        newItemWindow.setTitle("Detalhes");
        newItemWindow.initModality(Modality.WINDOW_MODAL);
        newItemWindow.initOwner(stage);
        newItemWindow.setResizable(false);
        newItemWindow.centerOnScreen();
        newItemWindow.show();
    }

    public static void showNewItemWindow(Stage stage, TableView<Item> inventario, Biblioteca biblioteca) {
        Stage newItemWindow = new Stage();

        VBox vBox = new VBox();
        GridPane topGrid = new GridPane();
        topGrid.setHgap(4);
        topGrid.setVgap(3);
        topGrid.setAlignment(Pos.CENTER);
        topGrid.setPadding(new Insets(10, 10,10,10));

        Text txtNome = new Text("Nome");
        topGrid.add(txtNome, 0, 0);
        TextField tfNome = new TextField();
        topGrid.add(tfNome, 0, 1, 1, 1);

        Text txtTipo = new Text("Tipo");
        txtTipo.maxWidth(Double.MAX_VALUE);
        topGrid.add(txtTipo, 2,0);
        ComboBox cbTipo = new ComboBox(tipos);
        cbTipo.setValue(tipos.get(0));
        topGrid.add(cbTipo, 2, 1);

        //Livro
        GridPane livroGrid = new GridPane();
        livroGrid.setVgap(6);
        livroGrid.setHgap(6);
        livroGrid.setAlignment(Pos.CENTER);
        livroGrid.setPadding(new Insets(10,10,10,10));
        Text txtIsbn = new Text("ISBN");
        livroGrid.add(txtIsbn, 0, 1);
        TextField tfIsbn = new TextField();
        livroGrid.add(tfIsbn, 1, 1);

        Text txtAutor = new Text("Autor");
        livroGrid.add(txtAutor, 0, 2);
        TextField tfAutor = new TextField();
        livroGrid.add(tfAutor, 1, 2);

        Text txtEditoraLivro = new Text("Editora");
        livroGrid.add(txtEditoraLivro, 0, 3);
        TextField tfEditoraLivro = new TextField();
        livroGrid.add(tfEditoraLivro, 1, 3);

        //Revista
        GridPane revistaGrid = new GridPane();
        revistaGrid.setVgap(6);
        revistaGrid.setHgap(6);
        revistaGrid.setPadding(new Insets(10,10,10,10));
        Text txtDataRevista = new Text("Data");
        revistaGrid.add(txtDataRevista, 0, 1);
        DatePicker dpDataRevista = new DatePicker();
        revistaGrid.add(dpDataRevista, 1, 1);

        Text txtEdicao = new Text("Edição");
        revistaGrid.add(txtEdicao, 0, 2);
        TextField tfEdicao = new TextField();
        tfEdicao.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(!t1.matches("\\d*")) {
                    tfEdicao.setText(t1.replaceAll("[^\\d]", ""));
                }
            }
        });
        revistaGrid.add(tfEdicao, 1, 2);

        Text txtEditoraRevista = new Text("Editora");
        revistaGrid.add(txtEditoraRevista, 0, 3);
        TextField tfEditoraRevista = new TextField();
        revistaGrid.add(tfEditoraRevista, 1, 3);

        // Jornal
        GridPane jornalGrid = new GridPane();
        jornalGrid.setVgap(6);
        jornalGrid.setHgap(6);
        jornalGrid.setPadding(new Insets(10,10,10,10));
        Text txtDataJornal = new Text("Data");
        jornalGrid.add(txtDataJornal, 0, 1);
        DatePicker dpDataJornal = new DatePicker();
        jornalGrid.add(dpDataJornal, 1, 1);

        Text txtNumero = new Text("Número");
        jornalGrid.add(txtNumero, 0, 2);
        TextField tfNumero = new TextField();
        tfNumero.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(!t1.matches("\\d*")) {
                    tfNumero.setText(t1.replaceAll("[^\\d]", ""));
                }
            }
        });
        jornalGrid.add(tfNumero, 1, 2);


        Text txtDiretor = new Text("Diretor");
        jornalGrid.add(txtDiretor, 0, 3);
        TextField tfDiretor = new TextField();
        jornalGrid.add(tfDiretor, 1, 3);

        cbTipo.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if (t1 == Options.LIVRO) {
                    vBox.getChildren().remove(1);
                    vBox.getChildren().add(1, livroGrid);
                } else if (t1 == Options.REVISTA) {
                    vBox.getChildren().remove(1);
                    vBox.getChildren().add(1, revistaGrid);
                } else {
                    vBox.getChildren().remove(1);
                    vBox.getChildren().add(1, jornalGrid);
                }
            }
        });


        vBox.setMaxHeight(Double.MAX_VALUE);
        vBox.getChildren().add(topGrid);
        vBox.getChildren().add(livroGrid);

        // Botões
        Button btnCriar = new Button("Criar");
        btnCriar.setMaxWidth(Double.MAX_VALUE);
        btnCriar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Item newItem;
                    if (cbTipo.getValue() == Options.LIVRO) {
                        if (tfNome.getText().equals("") || tfIsbn.getText().equals("") ||
                                tfAutor.getText().equals("") || tfEditoraLivro.equals("")) {
                            showWarning("Todos os campos devem ser preenchidos!");
                        } else {
                            newItem = new Livro(tfNome.getText(), tfIsbn.getText(),
                                    tfAutor.getText(), tfEditoraLivro.getText());
                            biblioteca.addItem(newItem);
                            inventario.refresh();
                            newItemWindow.close();
                        }
                    } else if (cbTipo.getValue() == Options.REVISTA) {
                        if (tfNome.getText().equals("") || dpDataRevista.getEditor().getText().equals("") ||
                                tfEdicao.getText().equals("") || tfEditoraRevista.getText().equals("")) {
                            showWarning("Todos os campos devem ser preenchidos!");
                        } else {
                            newItem = new Revista(tfNome.getText(),
                                    dpDataRevista.getConverter().fromString(dpDataRevista.getEditor().getText()),
                                    Integer.parseInt(tfEdicao.getText()), tfEditoraRevista.getText());
                            biblioteca.addItem(newItem);
                            inventario.refresh();
                            newItemWindow.close();
                        }
                    } else {
                        if (tfNome.getText().equals("") || dpDataJornal.getEditor().getText().equals("") ||
                                tfNumero.getText().equals("") || tfDiretor.getText().equals("")) {
                            showWarning("Todos os campos devem ser preenchidos!");
                        } else {
                            newItem = new Jornal(tfNome.getText(),
                                    dpDataJornal.getConverter().fromString(dpDataJornal.getEditor().getText()),
                                    Integer.parseInt(tfNumero.getText()), tfDiretor.getText());
                            biblioteca.addItem(newItem);
                            inventario.refresh();
                            newItemWindow.close();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("erro: " + e.getMessage());
                }
            }
        });
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(actionEvent -> {
            newItemWindow.close();
        });
        btnCancelar.setMaxWidth(Double.MAX_VALUE);


        HBox hBox = new HBox();
        hBox.getChildren().addAll(btnCancelar, btnCriar);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        hBox.setHgrow(btnCriar, Priority.ALWAYS);
        hBox.setHgrow(btnCancelar, Priority.ALWAYS);

        vBox.getChildren().add(hBox);
        Scene cena = new Scene(vBox);
        newItemWindow.setScene(cena);
        newItemWindow.setTitle("Novo Item");
        newItemWindow.initModality(Modality.WINDOW_MODAL);
        newItemWindow.initOwner(stage);
        newItemWindow.setResizable(false);
        newItemWindow.centerOnScreen();
        newItemWindow.show();
    }

    public static void showNewClientWindow(Stage stage, Biblioteca biblioteca) {
        Stage newClientWindow = new Stage();

        Text txtNome = new Text("Nome");
        TextField tfNome = new TextField();
        Button btnAdicionar = new Button("Adicionar");
        btnAdicionar.setMaxWidth(Double.MAX_VALUE);
        btnAdicionar.setMinHeight(40);
        btnAdicionar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!tfNome.getText().equals("")) {
                    biblioteca.addCliente(new Cliente(tfNome.getText()));
                    newClientWindow.close();
                } else {
                    showWarning("O campo \"Nome\" deve ser preenchido.");
                }
            }
        });

        VBox vBoxNome = new VBox(5);
        vBoxNome.getChildren().addAll(txtNome, tfNome);
        VBox mainVBox = new VBox(20);
        mainVBox.getChildren().addAll(vBoxNome, btnAdicionar);
        mainVBox.setPadding(new Insets(10,10,10,10));

        newClientWindow.setScene(new Scene(mainVBox));
        newClientWindow.setTitle("Novo Cliente");
        newClientWindow.initModality(Modality.WINDOW_MODAL);
        newClientWindow.initOwner(stage);
        newClientWindow.setResizable(false);
        newClientWindow.centerOnScreen();
        newClientWindow.show();
    }

    public static void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Atenção");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRO!");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public static boolean showConfirm(String message) {
        Alert alert = new Alert(Alert.AlertType.NONE, message, ButtonType.NO, ButtonType.YES);
        Optional<ButtonType> result = alert.showAndWait();
        boolean confirm = false;
        if (result.get() == alert.getButtonTypes().get(1)) {
            confirm = true;
        }
        return confirm;
    }

    public static void showMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.NONE, message, ButtonType.CLOSE);
        alert.setTitle(title);
        alert.showAndWait();
    }

    public static boolean showSaveMessage(Biblioteca biblioteca, String message) {
        Alert alert = new Alert(Alert.AlertType.NONE, message, ButtonType.CANCEL, ButtonType.NO, ButtonType.YES);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == alert.getButtonTypes().get(0)) {
            return false;
        } else if (result.get() == alert.getButtonTypes().get(1)) {
            return true;
        } else {
            try {
                IOFunctions.salvarRegistro(biblioteca);
            } catch (FileNotFoundException e) {
                showError("Falha ao salvar dados do inventário!");
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }
}
