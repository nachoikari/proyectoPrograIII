package Controllers;

import Models.Career;
import Models.Department;
import Models.Faculty;
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

public class DepartFacultyAdminController implements Initializable {

    @FXML
    private Button btn_backMenu;
    @FXML
    private TableView<Object> tbl_faculty;
    @FXML
    private TableView<Object> tbl_department;
    @FXML
    private TableView<Object> tbl_Career;
    @FXML
    private Button btn_addFaculty;
    @FXML
    private Button btn_modifyFaculty;
    @FXML
    private Button btn_deleteFaculty;
    @FXML
    private Button btn_addDepart;
    @FXML
    private Button btn_modifyDepart;
    @FXML
    private Button btn_deleteDepart;
    @FXML
    private Label lbl_pagFaculty;
    @FXML
    private Button btn_prevPagFaculty;
    @FXML
    private Button btn_nextPagFaculty;
    @FXML
    private Button btn_addCareer;
    @FXML
    private Button btn_modifyCareer;
    @FXML
    private Button btn_deleteCareer;
    @FXML
    private Label lbl_pagCareer;
    @FXML
    private Button btn_prevPagCareer;
    @FXML
    private Button btn_nextPagCareer;

    private int currentFacultyPage = 1;
    private int currentCareerPage = 1;
    private Object facultySelect = null;
    private Object departmentSelect = null;
    private Object careerSelect = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTables();
        loadFaculties();
        //listener
        tbl_faculty.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            facultySelect = newSelection;
            Utils.SelectionModel.getInstance().setFaculty((Faculty) facultySelect);
            loadDepartments();
        });

        tbl_department.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            departmentSelect = newSelection;
            Utils.SelectionModel.getInstance().setDepartment((Department) departmentSelect);
            loadCareers();
        });
        
        tbl_Career.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            careerSelect = newSelection;
            Utils.SelectionModel.getInstance().setCareer((Career) careerSelect);
        });
    }

    @FXML
    private void backToMenu(ActionEvent event) throws IOException {
        String name = Utils.SelectionModel.getInstance().getUniversity().getName();
        App.App.changeScene("UniversityAdmin", "Universidad " + name);
    }

    @FXML
    private void addFaculty(ActionEvent event) throws IOException {
        Utils.SelectionModel.getInstance().setModifying(false);
        Utils.SelectionModel.getInstance().setOption(5);
        App.App.changeScene("CRUDWindow", "Agregar");
    }

    @FXML
    private void modifyFaculty(ActionEvent event) throws IOException {
        if (facultySelect != null) {
            Utils.SelectionModel.getInstance().setModifying(true);
            Utils.SelectionModel.getInstance().setOption(5);
            App.App.changeScene("CRUDWindow", "Modificar");
        }
    }

    @FXML
    private void deleteFaculty(ActionEvent event) {
        if (facultySelect == null) {
            return;
        }
        Utils.SelectionModel.getInstance().setOption(5);
        String id;
        id = Integer.toString(((Faculty) facultySelect).getId());
        if(deleteObjectThread(id)){
            loadFaculties();
            tbl_department.getItems().clear();
            tbl_Career.getItems().clear();
            facultySelect = null;
            departmentSelect = null;
            careerSelect = null;
        }
    }

    @FXML
    private void addDepart(ActionEvent event) throws IOException {
        Utils.SelectionModel.getInstance().setModifying(false);
        Utils.SelectionModel.getInstance().setOption(6);
        App.App.changeScene("CRUDWindow", "Agregar");
    }

    @FXML
    private void modifyDepart(ActionEvent event) throws IOException {
        if (departmentSelect != null) {
            Utils.SelectionModel.getInstance().setModifying(true);
            Utils.SelectionModel.getInstance().setOption(6);
            App.App.changeScene("CRUDWindow", "Modificar");
        }
    }

    @FXML
    private void deleteDepart(ActionEvent event) {
        if (departmentSelect == null) {
            return;
        }
        Utils.SelectionModel.getInstance().setOption(6);
        String id;
        id = Integer.toString(((Department) departmentSelect).getId());
        if(deleteObjectThread(id)){
            loadDepartments();
            tbl_Career.getItems().clear();
            departmentSelect = null;
            careerSelect = null;
        }
    }

    private void initTables() {
        //360
        TableColumn<Object, Integer> idColumnF = new TableColumn<>("id");
        idColumnF.setCellValueFactory(cellData -> new SimpleIntegerProperty(((Faculty) cellData.getValue()).getId()).asObject());
        idColumnF.setPrefWidth(60);
        TableColumn<Object, String> nameColumnF = new TableColumn<>("Nombre");
        nameColumnF.setCellValueFactory(cellData -> new SimpleStringProperty(((Faculty) cellData.getValue()).getName()));
        nameColumnF.setPrefWidth(300);
        tbl_faculty.getColumns().addAll(idColumnF, nameColumnF);
        //-------------------------

        TableColumn<Object, Integer> idColumnD = new TableColumn<>("id");
        idColumnD.setCellValueFactory(cellData -> new SimpleIntegerProperty(((Department) cellData.getValue()).getId()).asObject());
        idColumnD.setPrefWidth(60);
        TableColumn<Object, String> nameColumnD = new TableColumn<>("Nombre");
        nameColumnD.setCellValueFactory(cellData -> new SimpleStringProperty(((Department) cellData.getValue()).getName()));
        nameColumnD.setPrefWidth(300);
        tbl_department.getColumns().addAll(idColumnD, nameColumnD);

        //----------------
        TableColumn<Object, Integer> idColumnC = new TableColumn<>("id");
        idColumnC.setCellValueFactory(cellData -> new SimpleIntegerProperty(((Career) cellData.getValue()).getId()).asObject());
        idColumnC.setPrefWidth(60);
        TableColumn<Object, String> nameColumnC = new TableColumn<>("Nombre");
        nameColumnC.setCellValueFactory(cellData -> new SimpleStringProperty(((Career) cellData.getValue()).getName()));
        nameColumnC.setPrefWidth(300);
        tbl_Career.getColumns().addAll(idColumnC, nameColumnC);
    }

    private void loadFaculties() {
        TablesThread thrd = new TablesThread(btn_prevPagFaculty, btn_nextPagFaculty, lbl_pagFaculty, tbl_faculty);
        thrd.setOption(5);
        thrd.setPageToFind(currentFacultyPage);
        thrd.start();
    }

    private void loadDepartments() {
        TablesThread thrd = new TablesThread(null, null, null, tbl_department);
        thrd.setOption(6);
        thrd.setPageToFind(1);
        thrd.start();
    }

    @FXML
    private void addCareer(ActionEvent event) throws IOException {
        Utils.SelectionModel.getInstance().setModifying(false);
        Utils.SelectionModel.getInstance().setOption(7);
        App.App.changeScene("CRUDWindow", "Agregar");
    }

    @FXML
    private void modifyCareer(ActionEvent event) throws IOException {
        if (careerSelect != null) {
            Utils.SelectionModel.getInstance().setModifying(true);
            Utils.SelectionModel.getInstance().setOption(7);
            App.App.changeScene("CRUDWindow", "Modificar");
        }
    }

    @FXML
    private void deleteCareer(ActionEvent event) {
        if (careerSelect == null) {
            return;
        }
        Utils.SelectionModel.getInstance().setOption(7);
        String id;
        id = Integer.toString(((Career) careerSelect).getId());
        if(deleteObjectThread(id)){
            loadCareers();
            careerSelect = null;
        }
    }

    private void loadCareers() {
        TablesThread thrd = new TablesThread(btn_prevPagCareer, btn_nextPagCareer, lbl_pagCareer, tbl_Career);
        thrd.setOption(7);
        thrd.setPageToFind(currentCareerPage);
        thrd.start();
    }
    
    private boolean deleteObjectThread(String id) {
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
        return thrd.isDeleted();
    }
}
