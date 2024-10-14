/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Model.Admin;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nacho
 */
public class SelectRoleController implements Initializable {
    @FXML
    private Button btnAdminRole;
    @FXML
    private Button btnExit;
    @FXML
    private Button btnLogIn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    



    @FXML
    private void exit(ActionEvent event) {
        
    }

    @FXML
    private void logIn(ActionEvent event) {
        cambiarVentana();
    }
    
    public void cambiarVentana(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/logIn.fxml"));
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
    }
    public void logInAdmin(){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AdminLogIn.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Admin Login");
                stage.setScene(new Scene(root));
                stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void adminOptions(ActionEvent event) {
        logInAdmin();
    }
    
}
