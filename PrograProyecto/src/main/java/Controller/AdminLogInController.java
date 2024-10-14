/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Model.Admin;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nacho
 */
public class AdminLogInController implements Initializable {
    private Admin admin;
    @FXML
    private TextField textCed;
    @FXML
    private PasswordField textPass;
    @FXML
    private Button btnLogIn;
    @FXML
    private Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }    

    @FXML
    private void logIn(ActionEvent event) {
        String ced = this.textCed.getText();
        String pass = this.textPass.getText();
        
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage currentStage = (Stage) this.btnLogIn.getScene().getWindow();
        currentStage.close();
    }
    
}
