/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Model.Admin;
import Utils.ThreadAdmin;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private void logIn(ActionEvent event) throws InterruptedException {
        String ced = this.textCed.getText();
        String pass = this.textPass.getText();
        ThreadAdmin thread = new ThreadAdmin();
        thread.method="POST";
        thread.acction="LOGIN";
        thread.ced=ced;
        thread.password=pass;
        thread.start();
        thread.join();
        Admin admin = new Admin();
        if(admin.isIsLogged() == true){
            try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AdminCRUD.fxml"));
            Parent newWindow = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(newWindow));
            stage.setTitle("Select role");
            stage.show();
            Stage currentStage = (Stage) btnLogIn.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }else{
            System.out.println("Admin not logged");
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage currentStage = (Stage) this.btnLogIn.getScene().getWindow();
        currentStage.close();
    }
    
}
