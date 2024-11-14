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
    private Button btn_nextPag;
    @FXML
    private Button btn_backMenu;
    @FXML
    private Label lbl_currentPage;
    
    private int currentPage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentPage = 1;
        TablesThread thrd = new TablesThread(btn_prevPag, btn_nextPag, lbl_currentPage, UniversitiesContainer);
        thrd.setOption(2);
        thrd.setPageToFind(currentPage);
        thrd.start();
    }    

    @FXML
    private void backToMenu(ActionEvent event) throws IOException {
        App.App.changeScene("AdminMenu", "Sistema de Administración");
    }
    
}
