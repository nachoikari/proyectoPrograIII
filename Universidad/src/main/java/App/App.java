package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class App extends Application {

    private static Scene scene;
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        firstScene("login", "Iniciar sesión");
    }
    
    private static void firstScene(String fxml, String tittle) throws IOException{
        scene = new Scene(loadFXML(fxml));
        stage.setScene(scene);
        stage.setTitle(tittle);
        stage.show();
    }

    public static void changeScene(String fxml, String tittle) throws IOException {
        stage.hide();
        scene.setRoot(loadFXML(fxml));
        stage.setTitle(tittle);
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/" +fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}