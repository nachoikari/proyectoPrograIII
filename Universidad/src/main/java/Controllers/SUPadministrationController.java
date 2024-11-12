package Controllers;

import Models.Administrator;
import Models.Professor;
import Models.Student;
import Utils.Threads.CRUD_Thread;
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

public class SUPadministrationController implements Initializable {

    @FXML
    private Label lbl_tittle;
    @FXML
    private Button btn_add;
    @FXML
    private Button btn_modify;
    @FXML
    private Button btn_delete;
    @FXML
    private Button btn_adminUniversity;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        option = Utils.SelectionModel.getInstance().getOption();
        setForOption();

        //listener
        tbl_object.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            double columnWidth = newWidth.doubleValue() / tbl_object.getColumns().size();
            tbl_object.getColumns().forEach(column -> column.setPrefWidth(columnWidth));
        });
    }

    private void setForOption() {

        if (option == 1) {
            lbl_tittle.setText("Administrar Universidades");
            //setColumnsForUniversities();
            return;
        }
        if (option == 2) {
            lbl_tittle.setText("Administrar Profesores");
            setColumnsForProfessors();
            return;
        }
        if (option == 3) {
            lbl_tittle.setText("Administrar Estudiantes");
            setColumnsForStudents();
            return;
        }
        if (option == 4) {
            lbl_tittle.setText("Gestionar Administradores");
            setColumnsForAdmins();
            CRUD_Thread thrd = new CRUD_Thread(option, "showPage", 1, tbl_object);
            thrd.start();
            return;
        }
    }

    private void setColumnsForStudents() { //Hay que buscar una manera de parsear el campo de facultad
        double columnWidth = tbl_object.getWidth() / 5;

        TableColumn<Object, String> idColumn = new TableColumn<>("Cedula");
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Student) cellData.getValue()).getId()));
        idColumn.setPrefWidth(columnWidth);

        TableColumn<Object, String> nameColumn = new TableColumn<>("Nombre");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Student) cellData.getValue()).getName()));
        nameColumn.setPrefWidth(columnWidth);

        TableColumn<Object, String> emailColumn = new TableColumn<>("Correo");
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Student) cellData.getValue()).getName()));
        emailColumn.setPrefWidth(columnWidth);

        TableColumn<Object, Integer> phoneColumn = new TableColumn<>("Telefono");
        phoneColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(((Student) cellData.getValue()).getPhoneNumber()).asObject());
        phoneColumn.setPrefWidth(columnWidth);

        TableColumn<Object, String> facultyColumn = new TableColumn<>("Facultad");
        facultyColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Student) cellData.getValue()).getFaculty()));
        facultyColumn.setPrefWidth(columnWidth);

        tbl_object.getColumns().addAll(idColumn, nameColumn, emailColumn, phoneColumn, facultyColumn);
    }

    private void setColumnsForProfessors() { //Hay que buscar una manera de parsear el campo de facultad
        TableColumn<Object, String> idColumn = new TableColumn<>("Cedula");
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Professor) cellData.getValue()).getId()));

        TableColumn<Object, String> nameColumn = new TableColumn<>("Nombre");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Professor) cellData.getValue()).getName()));

        TableColumn<Object, String> emailColumn = new TableColumn<>("Correo");
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Professor) cellData.getValue()).getName()));

        TableColumn<Object, Integer> phoneColumn = new TableColumn<>("Telefono");
        phoneColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(((Professor) cellData.getValue()).getPhoneNumber()).asObject());

        TableColumn<Object, Integer> facultyColumn = new TableColumn<>("Facultad");
        facultyColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(((Professor) cellData.getValue()).getFaculty()).asObject());

        tbl_object.getColumns().addAll(idColumn, nameColumn, emailColumn, phoneColumn, facultyColumn);
    }

//private void setColumnsForUniversities() {
//    TableColumn<Object, String> nombreColumn = new TableColumn<>("Nombre");
//    nombreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((University) cellData.getValue()).getNombre()));
//
//    TableColumn<Object, Double> salarioColumn = new TableColumn<>("Salario");
//    salarioColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(((University) cellData.getValue()).getSalario()).asObject());
//
//    tbl_object.getColumns().addAll(nombreColumn, salarioColumn);
//}
    private void setColumnsForAdmins() {
        TableColumn<Object, String> idColumn = new TableColumn<>("Cedula");
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Administrator) cellData.getValue()).getId()));

        TableColumn<Object, String> nameColumn = new TableColumn<>("Nombre");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Administrator) cellData.getValue()).getName()));

        TableColumn<Object, String> emailColumn = new TableColumn<>("Correo");
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Administrator) cellData.getValue()).getName()));

        tbl_object.getColumns().addAll(idColumn, nameColumn, emailColumn);
    }

    @FXML
    private void addObject(ActionEvent event) {
    }

    @FXML
    private void modifyObject(ActionEvent event) {
    }

    @FXML
    private void deleteObject(ActionEvent event) {
    }

    @FXML
    private void backToMenu(ActionEvent event) throws IOException {
        App.App.changeScene("UniversityAdmin", "Sistema de Administración");
    }
}
