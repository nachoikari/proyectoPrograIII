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
        lbl_universityName.setText(Utils.SelectionModel.getInstance().getUniversity().getName());
    }

    @FXML
    private void professorAdministration(MouseEvent event) throws IOException {
        Utils.SelectionModel.getInstance().setOption(3);
        App.App.changeScene("TablesCRUD", "Administrar Profesores");
    }

    @FXML
    private void studentAdministration(MouseEvent event) throws IOException {
        Utils.SelectionModel.getInstance().setOption(4);
        App.App.changeScene("TablesCRUD", "Administrar Estudiantes");
    }

    @FXML
    private void careerAdministration(MouseEvent event) {
        Utils.SelectionModel.getInstance().setOption(7);
    }

    @FXML
    private void departFacultyAdministration(MouseEvent event) {
        Utils.SelectionModel.getInstance().setOption(5);
    }

    @FXML
    private void closeSesion(ActionEvent event) throws IOException {
        App.App.changeScene("ShowUniversitiesMenu", "Mostrar Universidades");
    }
    
}
