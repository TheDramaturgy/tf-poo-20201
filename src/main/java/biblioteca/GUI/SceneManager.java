package biblioteca.GUI;

import biblioteca.Exceptions.SceneManagerNotInitializedException;
import biblioteca.Lib.Biblioteca;
import biblioteca.Lib.Cliente;
import biblioteca.Lib.Funcionario;
import biblioteca.Lib.Item;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public final class SceneManager {

    private static SceneManager Instance;
    private Stage stage;
    private ObservableList<Funcionario> dadosEquipe;
    private Biblioteca biblioteca;

    private Scene loginScene;
    private Scene mainScene;

    public Scene getLoginScene() throws SceneManagerNotInitializedException {
        if(loginScene != null) return loginScene;
        else {
            throw new SceneManagerNotInitializedException("SceneManager deve ser Inicializado antes de realizar a operação getLoginScene()");
        }
    }
    public Scene getMainScene() throws SceneManagerNotInitializedException {
        if (mainScene != null) return mainScene;
        else {
            throw new SceneManagerNotInitializedException("SceneManager deve ser Inicializado antes de realizar a operação getMainScene()");
        }
    }

    private SceneManager () { }

    public static SceneManager getInstance() {
        if (Instance == null) Instance = new SceneManager();
        return Instance;
    }

    public void init(Stage stage, ObservableList<Funcionario> dadosEquipe, Biblioteca biblioteca) {
        this.stage = stage;
        this.dadosEquipe = dadosEquipe;
        this.biblioteca = biblioteca;
        loginScene = createLoginScene();
        mainScene = createMainScene();
    }

    private Scene createLoginScene() {
        TableView<Funcionario> equipe = ComponentCreator.createFuncionarioTable(dadosEquipe);

        VBox vBox = ComponentCreator.createLoginVBox(stage, equipe);

        Scene loginScene = new Scene(vBox);
        return loginScene;
    }

    protected Scene createRegisterScene(Stage stage, ObservableList<Funcionario> dadosEquipe) {
        VBox mainVBox = ComponentCreator.createRegisterVBox(stage, dadosEquipe);

        Scene registerScene = new Scene(mainVBox);
        return registerScene;
    }

    private Scene createMainScene() {
        TableView<Cliente> clientes = ComponentCreator.createClienteTable((ObservableList<Cliente>) biblioteca.getClientes());
        TableView<Item> inventario = ComponentCreator.createInventarioTable((ObservableList<Item>) biblioteca.getInventario());

        BorderPane mainBorder = ComponentCreator.createMainBorderPane(stage, clientes, inventario, biblioteca);

        Scene cena = new Scene(mainBorder, 1000, 800);
        //cena.getStylesheets().add("biblioteca/GUI/stylesheet.css");
        return cena;
    }

}
