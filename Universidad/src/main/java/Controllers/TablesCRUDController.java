package Controllers;

import Models.Administrator;
import Models.Professor;
import Models.Student;
import Models.University;
import Utils.Threads.CRUD_Thread;
import Utils.Threads.TablesThread;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TablesCRUDController implements Initializable {
    @FXML
    private Label lbl_tittle;
    @FXML
    private Button btn_add;
    @FXML
    private Button btn_modify;
    @FXML
    private Button btn_delete;
    @FXML
    private Button btn_prevPag;
    @FXML
    private Label lbl_numPag;
    @FXML
    private Button btn_nextPag;
    @FXML
    private Button btn_backMenu;
    @FXML
    private TableView<Object> tbl_object;
    private int option;
    private int currentPage;
    private Object selection = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        option = Utils.SelectionModel.getInstance().getOption();
        currentPage = 1;
        setForOption();
        //listener
        tbl_object.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            saveSelection(newSelection);
            selection = newSelection;
        });
    }

    private void setForOption() {

        if (option == 1) {
            lbl_tittle.setText("Gestionar Administradores");
            setColumnsForAdmins();
            ejectThread();
            return;
        }
        if (option == 3) {
            lbl_tittle.setText("Administrar Profesores");
            setColumnsForProfessors();
            ejectThread();
            return;
        }
        if (option == 4) {
            lbl_tittle.setText("Administrar Estudiantes");
            setColumnsForStudents();
            ejectThread();
            return;
        }
        if (option == 8) {
            lbl_tittle.setText("Administrar Universidades");
            setColumnsForUniversities();
            ejectThread();
        }
    }

    private void setColumnsForStudents() { //Hay que buscar una manera de parsear el campo de facultad
        // tableWidth = 720;

        TableColumn<Object, String> idColumn = new TableColumn<>("Cedula");
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Student) cellData.getValue()).getId()));
        idColumn.setPrefWidth(72);

        TableColumn<Object, String> nameColumn = new TableColumn<>("Nombre");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Student) cellData.getValue()).getName()));
        nameColumn.setPrefWidth(216);

        TableColumn<Object, String> emailColumn = new TableColumn<>("Correo");
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Student) cellData.getValue()).getEmail()));
        emailColumn.setPrefWidth(248);

        TableColumn<Object, String> phoneColumn = new TableColumn<>("Telefono");
        phoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Student) cellData.getValue()).getPhoneNumber()));
        phoneColumn.setPrefWidth(92);

        TableColumn<Object, Integer> facultyColumn = new TableColumn<>("Facultad");
        facultyColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(((Student) cellData.getValue()).getIdCareer()).asObject());
        facultyColumn.setPrefWidth(92);

        tbl_object.getColumns().addAll(idColumn, nameColumn, emailColumn, phoneColumn, facultyColumn);
    }

    private void setColumnsForProfessors() { //Hay que buscar una manera de parsear el campo de facultad
        TableColumn<Object, String> idColumn = new TableColumn<>("Cedula");
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Professor) cellData.getValue()).getId()));
        idColumn.setPrefWidth(72);

        TableColumn<Object, String> nameColumn = new TableColumn<>("Nombre");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Professor) cellData.getValue()).getName()));
        nameColumn.setPrefWidth(216);

        TableColumn<Object, String> emailColumn = new TableColumn<>("Correo");
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Professor) cellData.getValue()).getEmail()));
        emailColumn.setPrefWidth(248);

        TableColumn<Object, String> phoneColumn = new TableColumn<>("Telefono");
        phoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Professor) cellData.getValue()).getPhoneNumber()));
        phoneColumn.setPrefWidth(92);

        TableColumn<Object, Integer> facultyColumn = new TableColumn<>("Facultad");
        facultyColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(((Professor) cellData.getValue()).getIdCareer()).asObject());
        facultyColumn.setPrefWidth(92);

        tbl_object.getColumns().addAll(idColumn, nameColumn, emailColumn, phoneColumn, facultyColumn);
    }

    private void setColumnsForAdmins() {
        TableColumn<Object, String> idColumn = new TableColumn<>("Cedula");
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Administrator) cellData.getValue()).getId()));
        idColumn.setPrefWidth(240);

        TableColumn<Object, String> nameColumn = new TableColumn<>("Nombre");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Administrator) cellData.getValue()).getName()));
        nameColumn.setPrefWidth(240);

        TableColumn<Object, String> emailColumn = new TableColumn<>("Correo");
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Administrator) cellData.getValue()).getName()));
        emailColumn.setPrefWidth(240);

        tbl_object.getColumns().addAll(idColumn, nameColumn, emailColumn);
    }

    private void setColumnsForUniversities() {
        //tableWidth = 720;
        TableColumn<Object, Integer> idColumn = new TableColumn<>("id");
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(((University) cellData.getValue()).getId()).asObject());
        idColumn.setPrefWidth(72);

        TableColumn<Object, String> nameColumn = new TableColumn<>("Nombre");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((University) cellData.getValue()).getName()));
        nameColumn.setPrefWidth(216);

        TableColumn<Object, String> emailColumn = new TableColumn<>("Correo");
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((University) cellData.getValue()).getEmail()));
        emailColumn.setPrefWidth(248);

        TableColumn<Object, String> addressColumn = new TableColumn<>("Direccion");
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((University) cellData.getValue()).getAddress()));
        addressColumn.setPrefWidth(92);

        TableColumn<Object, String> urlColumn = new TableColumn<>("Imagen");
        urlColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((University) cellData.getValue()).getUrl()));
        urlColumn.setPrefWidth(92);

        tbl_object.getColumns().addAll(idColumn, nameColumn, emailColumn, addressColumn, urlColumn);
    }

    private void ejectThread() {
        TablesThread thrd = new TablesThread(btn_prevPag, btn_nextPag, lbl_numPag, tbl_object);
        thrd.setOption(option);
        thrd.setPageToFind(currentPage);
        thrd.start();
        try {
            thrd.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void addObject(ActionEvent event) throws IOException {
        Utils.SelectionModel.getInstance().setModifying(false);
        App.App.changeScene("CRUDWindow", "Agregar");
    }

    @FXML
    private void modifyObject(ActionEvent event) throws IOException {
        if (selection != null) {
            Utils.SelectionModel.getInstance().setModifying(true);
            App.App.changeScene("CRUDWindow", "Modificar");
        }
    }

    @FXML
    private void deleteObject(ActionEvent event) {
        if(selection == null){
            return;
        }
        String id;
        if (option == 1) {//admin
            id = ((Administrator)selection).getId();
            deleteObjectThread(id);
            return;
        }
        if (option == 3) {//profes
            id = ((Professor)selection).getId();
            deleteObjectThread(id);
            return;
        }
        if (option == 4) {//Estudiantes
            id = ((Student)selection).getId();
            deleteObjectThread(id);
            return;
        }
        if (option == 8) {//Universidades
            int numID = ((University)selection).getId();
            id = Integer.toString(numID);
            deleteObjectThread(id);
        }
    }
    
    @FXML
    private void backToMenu(ActionEvent event) throws IOException {
        if (option == 1 || option == 8) {
            App.App.changeScene("AdminMenu", "Sistema de Administración");
            return;
        } else {
            String name = Utils.SelectionModel.getInstance().getUniversity().getName();
            App.App.changeScene("UniversityAdmin", "Universidad " + name);
        }
    }

    private void saveSelection(Object object) {
        if (option == 1) {
            Utils.SelectionModel.getInstance().setAdministrator((Administrator) object);
            return;
        }
        if (option == 3) {
            Utils.SelectionModel.getInstance().setProfessor((Professor) object);
            return;
        }
        if (option == 4) {
            Utils.SelectionModel.getInstance().setStudent((Student) object);
            return;
        }
        if (option == 8) {
            Utils.SelectionModel.getInstance().setUniversity((University) object);
        }
    }
    
    private void deleteObjectThread(String id) {
        Utils.SelectionModel.getInstance().setDeleting(true);
        CRUD_Thread thrd = new CRUD_Thread();
            thrd.setId(id);
            thrd.start();
        try {
            thrd.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        Utils.SelectionModel.getInstance().setDeleting(false);
            if(thrd.isDeleted()){
                ejectThread();
                selection = null;
            }
    }

    @FXML
    private void prevPag(ActionEvent event) {
        currentPage--;
        ejectThread();
        lbl_numPag.setText(Integer.toString(currentPage));
    }

    @FXML
    private void nextPag(ActionEvent event) {
        currentPage++;
        ejectThread();
        lbl_numPag.setText(Integer.toString(currentPage));
    }
}
