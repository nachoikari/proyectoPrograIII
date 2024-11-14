package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class AdminMenuController implements Initializable {

    @FXML
    private Button btn_backLogin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void adminAdministration(MouseEvent event) throws IOException {
        Utils.SelectionModel.getInstance().setOption(1);
        App.App.changeScene("TablesCRUD", "Gestionar Administradores");
    }

    @FXML
    private void createUniversity(MouseEvent event) {
    }

    @FXML
    private void enterToUniversity(MouseEvent event) throws IOException {
        App.App.changeScene("ShowUniversitiesMenu", "Mostrar Universidades");
    }

    @FXML
    private void closeSesion(ActionEvent event) throws IOException {
        App.App.changeScene("login", "Iniciar Sesión");
    }
}
