package Controllers;

import Utils.Threads.TablesThread;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ShowUniversitiesMenuController implements Initializable {

    @FXML
    private VBox UniversitiesContainer;
    @FXML
    private Button btn_prevPag;
    @FXML
    private Label lbl_numPag;
    @FXML
    private Button btn_nextPag;
    @FXML
    private Button btn_backMenu;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TablesThread thrd = new TablesThread(2, 1, UniversitiesContainer);
        thrd.start();
    }    

    @FXML
    private void backToMenu(ActionEvent event) throws IOException {
        App.App.changeScene("AdminMenu", "Sistema de Administración");
    }
    
}
