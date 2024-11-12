package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;



public class UniversityAdminController implements Initializable {
    @FXML
    private Button btn_backLogin;
    @FXML
    private Label lbl_universityName;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //
    }    

    @FXML
    private void universityAdministration(MouseEvent event) throws IOException {
        Utils.SelectionModel.getInstance().setOption(1);
        App.App.changeScene("SUPadministration", "Administrar Universidades");
    }

    @FXML
    private void professorAdministration(MouseEvent event) throws IOException {
        Utils.SelectionModel.getInstance().setOption(2);
        App.App.changeScene("SUPadministration", "Administrar Profesores");
    }

    private void studentAdministration(MouseEvent event) throws IOException {
        Utils.SelectionModel.getInstance().setOption(3);
        App.App.changeScene("SUPadministration", "Administrar Estudiantes");
    }

    @FXML
    private void adminAdministration(MouseEvent event) throws IOException {
        Utils.SelectionModel.getInstance().setOption(4);
        App.App.changeScene("SUPadministration", "Gestionar Administradores");
    }

    @FXML
    private void closeSesion(ActionEvent event) throws IOException {
        App.App.changeScene("login", "Iniciar Sesión");
    }


    
}
