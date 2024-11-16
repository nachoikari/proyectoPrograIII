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
public class ProfessorMenuController implements Initializable {

    @FXML
    private Button btnCreateGroups;
    @FXML
    private Button btnEnrollStudents;
    @FXML
    private Button btnCreateAssignments;
    @FXML
    private Button btn_backMenu;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void changeCRUDGroups(ActionEvent event) throws IOException {
        App.App.changeScene("CrudGroups", "Grupos");
    }

    @FXML
    private void changeEnrollStudents(ActionEvent event) throws IOException {
        App.App.changeScene("Enroll", "Matricula del curso");
    }

    @FXML
    private void changeCrudAssignments(ActionEvent event) {
    }

    @FXML
    private void backToMenu(ActionEvent event) throws IOException {
        App.App.changeScene("login", "Iniciar Sesión");
    }
    
}
