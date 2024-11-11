package Controllers;

import Models.Administrator;
import Models.Professor;
import Models.Student;
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
    private TableView<Object> tbl_objectList;

    private int option;
    private int currentPage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        option = AdminMainController.option;
        setTittle();
        setColumnTable();
        //llamamos el metodo para cargar la lista
        
        //listener
        tbl_objectList.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            double columnWidth = newWidth.doubleValue() / tbl_objectList.getColumns().size();
            tbl_objectList.getColumns().forEach(column -> column.setPrefWidth(columnWidth));
        });
    }

    private void setTittle() {
        if (option == 1) {
            lbl_tittle.setText("Administrar Universidades");
            return;
        }
        if (option == 2) {
            lbl_tittle.setText("Administrar Profesores");
            return;
        }
        if (option == 3) {
            lbl_tittle.setText("Administrar Estudiantes");
            return;
        }
        if (option == 4) {
            lbl_tittle.setText("Gestionar Administradores");
            return;
        }
    }

    private void setColumnTable() {
        tbl_objectList.getColumns().clear();
        if (option == 1) {
            //setColumnsForUniversities();
            return;
        }
        if (option == 2) {
            setColumnsForProfessors();
            return;
        }
        if (option == 3) {
            setColumnsForStudents();
            return;
        }
        if (option == 4) {
            setColumnsForAdmins();
            return;
        }
    }

    private void setColumnsForStudents() { //Hay que buscar una manera de parsear el campo de facultad
        double columnWidth = tbl_objectList.getWidth() / 5;

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

        TableColumn<Object, Integer> facultyColumn = new TableColumn<>("Facultad");
        facultyColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(((Student) cellData.getValue()).getFaculty()).asObject());
        facultyColumn.setPrefWidth(columnWidth);

        tbl_objectList.getColumns().addAll(idColumn, nameColumn, emailColumn, phoneColumn, facultyColumn);
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

        tbl_objectList.getColumns().addAll(idColumn, nameColumn, emailColumn, phoneColumn, facultyColumn);
    }

//private void setColumnsForUniversities() {
//    TableColumn<Object, String> nombreColumn = new TableColumn<>("Nombre");
//    nombreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((University) cellData.getValue()).getNombre()));
//
//    TableColumn<Object, Double> salarioColumn = new TableColumn<>("Salario");
//    salarioColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(((University) cellData.getValue()).getSalario()).asObject());
//
//    tbl_objectList.getColumns().addAll(nombreColumn, salarioColumn);
//}
    private void setColumnsForAdmins() {
        TableColumn<Object, String> idColumn = new TableColumn<>("Cedula");
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Administrator) cellData.getValue()).getId()));

        TableColumn<Object, String> nameColumn = new TableColumn<>("Nombre");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Administrator) cellData.getValue()).getName()));

        TableColumn<Object, String> emailColumn = new TableColumn<>("Correo");
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Administrator) cellData.getValue()).getName()));

        tbl_objectList.getColumns().addAll(idColumn, nameColumn, emailColumn);
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
        App.App.changeScene("adminMain", "Sistema de Administración");
    }
}
