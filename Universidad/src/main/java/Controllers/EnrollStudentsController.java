/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import Models.Course;
import Models.Groups;
import Utils.Threads.Thread_Students;
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

/**
 * FXML Controller class
 *
 * @author nacho
 */
public class EnrollStudentsController implements Initializable {
    private String routeGroup;
    private Groups selectedGroup = null;
    private String nrc = "";
    @FXML
    private TableView<Groups> tableGroups;
    @FXML
    private TableView<Course> tableCourses;
    @FXML
    private ChoiceBox<String> choiceOptions;
    private Course selectedCourse = null;
    @FXML
    private Label labelPage;
    @FXML
    private Button btnDecreasePage;
    @FXML
    private Button btnIncreasePage;
    @FXML
    private Button btnEnroll;
    @FXML
    private Button btnUnenroll;
    private String option;
    @FXML
    private Button btnBack;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        hide();
        loadChoice();
        choiceListener();
        initializaTable();
        clickCourses();
        clickGroups();
    }    
    private void initializaTableGroups(){
        tableGroups.getColumns().clear();
        TableColumn<Groups, String> nrcColumn = new TableColumn<>("NRC");
        nrcColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Groups) cellData.getValue()).getNrc()));
        
        TableColumn<Groups, String>  courseColumn= new TableColumn<>("Curso");
        courseColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Groups) cellData.getValue()).getCode_course()));
        
        TableColumn<Groups, String>  cedColumn= new TableColumn<>("Cedula del profesor");
        cedColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Groups) cellData.getValue()).getCed_professor()));
        
        TableColumn<Groups, Integer> groupNumberColumn = new TableColumn<>("Numero de grupo");
        groupNumberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(((Groups) cellData.getValue()).getGroup_number()).asObject());
        
        tableGroups.getColumns().addAll(nrcColumn, courseColumn, cedColumn,groupNumberColumn);
        //TableView<Groups> groupsTable, int pageToGet, String acction
        
        Thread_groups thread = new Thread_groups(tableGroups,1,"GET",routeGroup);
        thread.setCed(Utils.SelectionModel.getInstance().getStudent().getId());
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void decreasePage(ActionEvent event) {
    }

    @FXML
    private void increasePage(ActionEvent event) {
    }
    private void clickCourses(){
        tableCourses.setOnMouseClicked(event -> {
            // Obtener el objeto seleccionado
             selectedCourse = tableCourses.getSelectionModel().getSelectedItem();

            if (selectedCourse != null && option.equals("Matricular")) {
                routeGroup = "GroupCourse";
                //initializaTableGroups();
                //TableView<Groups> groups, int pageToGet, String code, String acction, String route
                clean();
                System.out.println(selectedCourse.getCode());
                Thread_groups thread = new Thread_groups(tableGroups,1,selectedCourse.getCode(),"GET",routeGroup);
                thread.start();
                 try {
                     thread.join();
                 } catch (InterruptedException ex) {
                     ex.printStackTrace();
                 }
                tableGroups.setVisible(true);
                tableGroups.setDisable(false);
            }
            
        });
    }
    private void clickGroups(){
        tableGroups.setOnMouseClicked(event-> {
            selectedGroup = tableGroups.getSelectionModel().getSelectedItem();
            if(selectedGroup != null){
               nrc = selectedGroup.getNrc();
                System.out.println(nrc);
            }
        });
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
        
        Thread_groups thread = new Thread_groups(tableCourses, 1, Utils.SelectionModel.getInstance().getStudent().getIdCareer(),"GET", "Courses");
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    private void loadChoice(){
        ObservableList<String> options = FXCollections.observableArrayList(
        "Matricular",
        "Desmatricular"     
    );
        choiceOptions.setItems(options);
    }
    private void choiceListener(){
        choiceOptions.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Matricular")) {
                option = newValue;
                tableCourses.setVisible(true);
                tableCourses.setDisable(false);
                tableGroups.setVisible(false);
                tableGroups.setDisable(true);
                btnDecreasePage.setVisible(true);
                btnDecreasePage.setDisable(false);
                btnIncreasePage.setVisible(true);
                btnIncreasePage.setDisable(false);
                btnUnenroll.setVisible(false);
                btnUnenroll.setDisable(true);
                labelPage.setVisible(true);
                btnEnroll.setVisible(true);
                btnEnroll.setDisable(false);
                
            }else if (newValue.equals("Desmatricular")){
                
                routeGroup = "GroupsPerStudent";
                tableCourses.setVisible(false);
                tableCourses.setDisable(true);
                tableGroups.setVisible(true);
                btnDecreasePage.setVisible(false);
                btnDecreasePage.setDisable(true);
                btnIncreasePage.setVisible(false);
                btnIncreasePage.setDisable(true);
                tableGroups.setDisable(false);
                btnUnenroll.setVisible(true);
                btnUnenroll.setDisable(false);
                labelPage.setVisible(false);
                initializaTableGroups();
            }
            
        });
    }

    @FXML
    private void enroll(ActionEvent event) throws InterruptedException {
        System.out.println(nrc);
        if(selectedGroup != null){
            //String action, String route, String nrc_group, String ced_Stu, String grade
            Thread_Students thread = new Thread_Students("POST","Enroll",nrc,Utils.SelectionModel.getInstance().getStudent().getId(),"");
            thread.start();
            thread.join();
        }
    }

    @FXML
    private void unEnroll(ActionEvent event) throws InterruptedException {
        if(selectedGroup != null){
            Thread_Students thread = new Thread_Students("DELETE","Enroll",nrc,Utils.SelectionModel.getInstance().getStudent().getId(),"");
            thread.start();
            thread.join();
        }
    }
    private void hide(){
            // Ocultar y desactivar las tablas
      tableGroups.setVisible(false);
      tableGroups.setDisable(true);

      tableCourses.setVisible(false);
      tableCourses.setDisable(true);

      // Ocultar y desactivar las etiquetas y botones
      labelPage.setVisible(false);
      labelPage.setDisable(true);

      btnDecreasePage.setVisible(false);
      btnDecreasePage.setDisable(true);

      btnIncreasePage.setVisible(false);
      btnIncreasePage.setDisable(true);

      btnEnroll.setVisible(false);
      btnEnroll.setDisable(true);

      btnUnenroll.setVisible(false);
      btnUnenroll.setDisable(true);
    }
    private void clean(){
        tableGroups.getColumns().clear();
        TableColumn<Groups, String> nrcColumn = new TableColumn<>("NRC");
        nrcColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Groups) cellData.getValue()).getNrc()));
        
        TableColumn<Groups, String>  courseColumn= new TableColumn<>("Curso");
        courseColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Groups) cellData.getValue()).getCode_course()));
        
        TableColumn<Groups, String>  cedColumn= new TableColumn<>("Cedula del profesor");
        cedColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Groups) cellData.getValue()).getCed_professor()));
        
        TableColumn<Groups, Integer> groupNumberColumn = new TableColumn<>("Numero de grupo");
        groupNumberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(((Groups) cellData.getValue()).getGroup_number()).asObject());
        
        tableGroups.getColumns().addAll(nrcColumn, courseColumn, cedColumn,groupNumberColumn);
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        App.App.changeScene("studentMenu", "Menu del estudiante");
    }
    
}
