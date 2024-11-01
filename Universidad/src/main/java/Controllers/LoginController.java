package Controllers;

import App.App;
import Utils.Threads.LoginThread;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {

    @FXML
    private TextField txt_user;
    @FXML
    private TextField txt_password;
    @FXML
    private Button btn_continue;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void continueLogin(ActionEvent event) throws InterruptedException, IOException {
        LoginThread thrd = new LoginThread(txt_user, txt_password);
        thrd.start();
    }
    
}
