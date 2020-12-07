package biblioteca;

import biblioteca.GUI.SceneManager;
import biblioteca.GUI.SecondaryWindow;
import biblioteca.Lib.*;
import biblioteca.Exceptions.InvalidStatusException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;

public class Launcher extends Application {

    private static Biblioteca biblioteca;
    private static ObservableList<Funcionario> dadosEquipe;

    public static void main(String[] args) throws IOException, InvalidStatusException {

        biblioteca = new Biblioteca();
        dadosEquipe = FXCollections.observableArrayList();
        IOFunctions.lerDadosEquipe(dadosEquipe);
        try { IOFunctions.carregarRegistro(biblioteca, dadosEquipe); }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            System.exit(2);
        }

        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        SceneManager.getInstance().init(stage, dadosEquipe, biblioteca);

        stage.setTitle("Biblioteca");
        stage.centerOnScreen();
        stage.setResizable(false);

        stage.setScene(SceneManager.getInstance().getLoginScene());

        // Close Event
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                try { IOFunctions.salvarDadosEquipe(dadosEquipe); }
                catch (FileNotFoundException e) {
                    SecondaryWindow.showError("Não foi possível salvar os dados da equipe!");
                    e.printStackTrace();
                }
                if(stage.getScene() != SceneManager.getInstance().getLoginScene())
                    if (!SecondaryWindow.showSaveMessage(biblioteca, "Deseja salvar as alterações antes de sair?"))
                        windowEvent.consume();
            }
        });

        stage.show();
    }


}
