/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import Models.Groups;
import Models.Student;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author nacho
 */
public class EnrollController implements Initializable {

    @FXML
    private Button btnBack;
    @FXML
    private TableView<Groups> tableGroups;
    @FXML
    private TableView<Student> tableStudents;
    @FXML
    private ChoiceBox<String> choiceOptions;
    @FXML
    private Button btnEnroll;
    @FXML
    private Button btnUnenroll;
    private Groups selectedGroup = null;
    private Student selectedStudent = null;
    private String routeStudent;
    private String routeGroup;
    private String nrc;
    private String option;
    @FXML
    private TextField textFGrade;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hideComponents();
        loadChoice();
        clickGroups();
        clickStudents();
        choiceListener();
    }    

    @FXML
    private void backMenu(ActionEvent event) throws IOException {
        App.App.changeScene("professorMenu", "Menu del profesor");
    }

    @FXML
    private void enrollStudent(ActionEvent event) throws InterruptedException {
        System.out.println(nrc);
        if(selectedStudent != null && selectedGroup != null){
            String gradeText = textFGrade.getText();
            double grade = Double.parseDouble(textFGrade.getText());
            if(grade < 0 || grade > 10){
                return;
            }
            //String action, String route, String nrc_group, String ced_Stu, String grade
            Thread_Students thread = new Thread_Students("POST","Enroll",nrc,selectedStudent.getId(),gradeText);
            thread.start();
            thread.join();
        }
    }
    @FXML
    private void unEnroll(ActionEvent event) throws InterruptedException {
        if(selectedStudent != null && selectedGroup!=null){
            Thread_Students thread = new Thread_Students("DELETE","Enroll",nrc,selectedStudent.getId(),"");
            thread.start();
            thread.join();
        }
    
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
        //TableView<Groups> groups, int pageToGet, String code, String acction, String route
        Thread_groups thread = new Thread_groups(tableGroups,1,Utils.SelectionModel.getInstance().getProfessor().getId(),"GET",routeGroup);
        
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    private void initilizableTableStudents() throws InterruptedException{
        tableStudents.getColumns().clear();
        TableColumn<Student, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(((Student) cellData.getValue()).getName()));
        
        TableColumn<Student, String> cedCol = new TableColumn<>("Cedula");
        cedCol.setCellValueFactory(cellData -> new SimpleStringProperty(((Student) cellData.getValue()).getId()));
        TableColumn<Student, String> emailCol = new TableColumn<>("Cedula");
        emailCol.setCellValueFactory(cellData -> new SimpleStringProperty(((Student) cellData.getValue()).getEmail()));
        
        tableStudents.getColumns().addAll(nameCol,cedCol);
        int id_career =Utils.SelectionModel.getInstance().getProfessor().getIdCareer();
        //TableView<Student> studentsTable, String route, int pageToGet, int career_id
        Thread_Students thread = new Thread_Students(tableStudents,routeStudent,1,id_career, "GET",nrc);
        thread.start();
        thread.join();
    }
    private void clickGroups(){
        tableGroups.setOnMouseClicked(event-> {
            selectedGroup = tableGroups.getSelectionModel().getSelectedItem();
            if(selectedGroup != null){
               nrc = selectedGroup.getNrc();
                System.out.println(nrc);
            }
            if (selectedGroup != null &&option.equals("Desmatricular estudiante")){
                try {
                    showStudentsGroup();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    private void clickStudents(){
        tableStudents.setOnMouseClicked(event-> {
            selectedStudent = tableStudents.getSelectionModel().getSelectedItem();
            if(tableStudents != null){
                System.out.println("Stu pressed");
               // nrc = selectedStudent.getId();
            }
        
        });
    }
    private void loadChoice(){
        ObservableList<String> options = FXCollections.observableArrayList(
        "Matricular estudiante",
        "Desmatricular estudiante"  
    );
        choiceOptions.setItems(options);
    }
    private void choiceListener(){
        choiceOptions.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
             if (newValue.equals("Matricular estudiante")){
                 tableGroups.setVisible(true);
                 tableGroups.setDisable(false);
                 tableStudents.setVisible(true);
                 tableStudents.setDisable(false);
                 routeStudent =  "studentsUniversity";
                 routeGroup = "Groups";
                 btnEnroll.setVisible(true);
                 btnEnroll.setDisable(false);
                 textFGrade.setVisible(true);
                 textFGrade.setDisable(false);
                  option = newValue;
                 initializaTableGroups();
                 try {
                     initilizableTableStudents();
                 } catch (InterruptedException ex) {
                     ex.printStackTrace();
                 }
             }
             if(newValue.equals("Desmatricular estudiante")){
                 tableGroups.setVisible(true);
                 tableGroups.setDisable(false);
                 routeGroup = "Groups";
                 option = newValue;
                 initializaTableGroups();
                 if(selectedGroup != null){
                     try {
                         showStudentsGroup();
                     } catch (InterruptedException ex) {
                         ex.printStackTrace();
                     }
                 }
             }
        });
    }
    private void showStudentsGroup() throws InterruptedException{
        routeStudent = "studentsPerGroup";
        System.out.println(routeStudent);
        initilizableTableStudents();
        tableStudents.setVisible(true);
        tableStudents.setDisable(false);
        btnUnenroll.setVisible(true);
        btnUnenroll.setDisable(false);
    }
    private void hideComponents(){
        tableStudents.setDisable(true);
        tableStudents.setVisible(false);
        tableGroups.setDisable(true);
        tableGroups.setVisible(false);
        btnEnroll.setVisible(false);
        btnEnroll.setDisable(true);
        btnUnenroll.setVisible(false);
        btnUnenroll.setDisable(true);
        textFGrade.setVisible(false);
        textFGrade.setDisable(true);
    }

    
}
