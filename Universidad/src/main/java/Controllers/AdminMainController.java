package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;



public class AdminMainController implements Initializable {
    //Variables
    public static int option;
    
    
    @FXML
    private Button btn_backLogin;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //
    }    

    @FXML
    private void universityAdministration(MouseEvent event) throws IOException {
        option = 1;
        App.App.changeScene("SUPadministration", "Administrar Universidades");
    }

    @FXML
    private void professorAdministration(MouseEvent event) throws IOException {
        option = 2;
        App.App.changeScene("SUPadministration", "Administrar Profesores");
    }

    @FXML
    private void studentAdministration(MouseEvent event) throws IOException {
        option = 3;
        App.App.changeScene("SUPadministration", "Administrar Estudiantes");
    }

    @FXML
    private void adminAdministration(MouseEvent event) throws IOException {
        option = 4;
        App.App.changeScene("SUPadministration", "Gestionar Administradores");
    }

    @FXML
    private void closeSesion(ActionEvent event) throws IOException {
        App.App.changeScene("login", "Iniciar Sesión");
    }


    
}
