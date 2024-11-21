/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import Utils.Threads.Thread_Assignments;
import Utils.Threads.Thread_groups;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nacho
 */
public class EvaluarController implements Initializable {
    public static int id_response ;

    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSubmit;
    @FXML
    private TextArea fieldComment;
    @FXML
    private TextField fieldGrade;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleCancel(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close(); // Cierra el Stage
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        String comment = fieldComment.getText();
        String grade = fieldGrade.getText();
        //String action,int id_response, String method,String comment, String grade
        Thread_Assignments thread = new Thread_Assignments( "evaluate",id_response , "PUT", comment,grade);

        thread.start();
        try {
            thread.join();
            
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close(); // Cierra el Stage
    }
    
}
