package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;


public class AdminMainController implements Initializable {

    @FXML
    private Button volver;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //
    }    

    @FXML
    private void volverLogin(ActionEvent event) throws IOException {
        App.App.changeScene("login", "Login");
    }
    
}
