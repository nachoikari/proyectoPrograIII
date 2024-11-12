package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
