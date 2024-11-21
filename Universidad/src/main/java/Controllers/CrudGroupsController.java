/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import Models.Course;
import Models.Groups;
import Utils.Threads.Thread_groups;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author nacho
 */
public class CrudGroupsController implements Initializable {

    @FXML
    private Button btnCreate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnGet;
    
    private ObservableList<Course> groupsData = FXCollections.observableArrayList();
    private Course selectedCourse = null;
    private Groups selectedGroup = null;
    @FXML
    private TextField textFNRC;
    @FXML
    private TextField textFNumber;
    @FXML
    private ChoiceBox<String> choiceOptions;
    @FXML
    private TableView<Groups> tableViewGroups;
    @FXML
    private Label labelCourse;
    @FXML
    private Label labelGroups;
    @FXML
    private TableView<Course> tableCourses;
    @FXML
    private Button btnVolver;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //initializaTable();
        loadChoice();
        hideTables();
        choiceListener();
        clickCourses();
        //initializaTableGroups();
        clickGroups();
    }    

    @FXML
    private void create(ActionEvent event) throws InterruptedException {
        if(selectedCourse != null){
            int group_number = Integer.parseInt(textFNumber.getText());
            String ced = Utils.SelectionModel.getInstance().getProfessor().getId();
            String nrc = textFNRC.getText();
            String code_course = selectedCourse.getCode();
            Groups group = new Groups(nrc,group_number,ced,code_course);
            Thread_groups thread = new Thread_groups("POST",group,"Groups");
            thread.start();
            thread.join();
        }
    }

    @FXML
    private void delete(ActionEvent event) throws InterruptedException {
        if(selectedGroup != null){
            Thread_groups thread = new Thread_groups("DELETE",selectedGroup,"Groups");
            thread.start();
            thread.join();
        }
    }

    @FXML
    private void getGroups(ActionEvent event) {
        
    }
    private void initializaTable(){
        tableCourses.getColumns().clear();
        TableColumn<Course, String> nrcColumn = new TableColumn<>("Nombre");
        nrcColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Course) cellData.getValue()).getName()));

        TableColumn<Course, Integer> groupNumberColumn = new TableColumn<>("Career id");
        groupNumberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(((Course) cellData.getValue()).getId_career()).asObject());

        TableColumn<Course, String> cedProfessorColumn = new TableColumn<>("Codigo del curso");
        cedProfessorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Course) cellData.getValue()).getCode()));

        tableCourses.getColumns().addAll(nrcColumn, groupNumberColumn, cedProfessorColumn);
        
        Thread_groups thread = new Thread_groups(tableCourses, 1, Utils.SelectionModel.getInstance().getProfessor().getIdCareer(),"GET", "Courses");
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    private void initializaTableGroups(){
        tableViewGroups.getColumns().clear();
        TableColumn<Groups, String> nrcColumn = new TableColumn<>("NRC");
        nrcColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Groups) cellData.getValue()).getNrc()));
        
        TableColumn<Groups, String>  courseColumn= new TableColumn<>("Curso");
        courseColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Groups) cellData.getValue()).getCode_course()));
        
        TableColumn<Groups, String>  cedColumn= new TableColumn<>("Cedula del profesor");
        cedColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Groups) cellData.getValue()).getCed_professor()));
        
        TableColumn<Groups, Integer> groupNumberColumn = new TableColumn<>("Numero de grupo");
        groupNumberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(((Groups) cellData.getValue()).getGroup_number()).asObject());
        
        tableViewGroups.getColumns().addAll(nrcColumn, courseColumn, cedColumn,groupNumberColumn);
        //TableView<Groups> groups, int pageToGet, String code, String acction, String route
        Thread_groups thread = new Thread_groups(tableViewGroups,1,Utils.SelectionModel.getInstance().getProfessor().getId(),"GET","Groups");
        
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    private void clickCourses(){
        tableCourses.setOnMouseClicked(event -> {
            // Obtener el objeto seleccionado
             selectedCourse = tableCourses.getSelectionModel().getSelectedItem();

            if (selectedCourse != null) {
                // Aquí puedes manejar el objeto seleccionado como desees
               // System.out.println("NRC: " + selectedCourse.getName());
               // System.out.println("Career ID: " + selectedCourse.getId_career());
               // System.out.println("Professor ID: " + selectedCourse.getCode());
            }
        });
    }
    private void clickGroups(){
        tableViewGroups.setOnMouseClicked(event-> {
            selectedGroup = tableViewGroups.getSelectionModel().getSelectedItem();
            if(selectedGroup != null){
               // System.out.println(selectedGroup.getNrc());
            }
        
        });
    }
    private void loadChoice(){
        ObservableList<String> options = FXCollections.observableArrayList(
        "Ver grupos",
        "Ver cursos",
        "Agregar grupo",
        "Eliminar grupo"        
    );
        choiceOptions.setItems(options);
    }
    private void hideTables(){
        btnCreate.setVisible(false);
        btnCreate.setDisable(true);
        btnDelete.setVisible(false);
        btnDelete.setDisable(true);
        btnGet.setDisable(true);
        btnGet.setVisible(false);
        labelCourse.setVisible(false);
        labelGroups.setVisible(false);
        tableCourses.setVisible(false);
        tableViewGroups.setVisible(false);
       
        
        tableCourses.setDisable(true);
        tableViewGroups.setDisable(true);
        
        textFNRC.setDisable(true);
        textFNRC.setVisible(false);
        
        textFNumber.setDisable(true);
        textFNumber.setVisible(false);
    }
    private void choiceListener(){
        choiceOptions.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            //hideTables(); // Oculta todas las tablas primero

            // Muestra la tabla correspondiente dependiendo de la opción seleccionada
            if (newValue.equals("Ver grupos")) {
                tableCourses.setVisible(false);
                tableCourses.setDisable(true);
                tableViewGroups.setVisible(true);
                tableViewGroups.setDisable(false);
            } else if (newValue.equals("Ver cursos")) {
                tableViewGroups.setVisible(false);
                tableViewGroups.setDisable(true);
                tableCourses.setVisible(true);
                tableCourses.setDisable(false);
            }  else if (newValue.equals("Agregar grupo")){
                tableCourses.setVisible(true);
                tableCourses.setDisable(false);
                tableViewGroups.setVisible(true);
                tableViewGroups.setDisable(false);
                btnCreate.setVisible(true);
                btnCreate.setDisable(false);
                textFNRC.setVisible(true);
                textFNRC.setDisable(false);
                textFNumber.setVisible(true);
                textFNumber.setDisable(false);
                initializaTable();
                initializaTableGroups();
            }else if (newValue.equals("Eliminar grupo")){
                tableViewGroups.setVisible(true);
                tableViewGroups.setDisable(false);
                btnDelete.setVisible(true);
                btnDelete.setDisable(false);
                initializaTableGroups();
            }
            
        });
    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.App.changeScene("professorMenu", "Grupos");
    }
}
