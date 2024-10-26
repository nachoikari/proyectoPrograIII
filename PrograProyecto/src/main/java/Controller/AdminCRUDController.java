package Controller;

import Utils.ThreadAdmin;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AdminCRUDController implements Initializable {

    @FXML
    private TextField textFCed;
    @FXML
    private TextField textFName;
    @FXML
    private TextField textFEmail;
    @FXML
    private PasswordField passFPass;
    @FXML
    private ChoiceBox<String> choiceOptions;
    @FXML
    private Button btnSend;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        choiceOptions.getItems().addAll("CREATE","DELETE","SHOW ALL","SHOW PER CEDULA", "UPDATE" );
    }    

    @FXML
    private void sendRequest(ActionEvent event) throws InterruptedException {
        ThreadAdmin thread = new ThreadAdmin();
        thread.acction="DELETE";
        thread.ced=textFCed.getText();
        thread.email = textFEmail.getText();
        thread.name = textFName.getText();
        thread.password = passFPass.getText();
        thread.token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjZWQiOiIxMjM0IiwicGFzc3dvcmQiOiJwMTIzNCJ9.YLM6E8pP_qeQGaDmJx78oXCUStbDRbbZwdpSOK0L1Fs";
        thread.method = "DELETE";
        thread.start();
        thread.join();
    }
    
}
