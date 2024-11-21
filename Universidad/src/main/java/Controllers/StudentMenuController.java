/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author nacho
 */
public class StudentMenuController implements Initializable {

    @FXML
    private Button btnEnroll;
    @FXML
    private Button btnAssignments;
    @FXML
    private Button btnLogOut;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void swapEnroll(ActionEvent event) throws IOException {
        App.App.changeScene("EnrollStudents", "Agregar asignaciones");
    }

    @FXML
    private void swapAssingments(ActionEvent event) throws IOException {
        App.App.changeScene("assignmentStu", "Agregar asignaciones");
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException {
        App.App.changeScene("login", "Iniciar Sesion");
    }
    
}
