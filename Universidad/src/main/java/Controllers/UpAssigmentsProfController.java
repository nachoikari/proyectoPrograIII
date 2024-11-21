/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import App.App;
import Models.Groups;
import Models.ProfAssigment;
import Utils.Threads.Thread_Assignments;
import Utils.Threads.Thread_groups;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author nacho
 */
public class UpAssigmentsProfController implements Initializable {

    @FXML
    private Button btnFile;
    @FXML
    private TextField textFName;
    @FXML
    private DatePicker date_due;
    @FXML
    private ChoiceBox<String> choiceOptions;
    @FXML
    private TableView<Groups> tableGroup;
    @FXML
    private TableView<ProfAssigment> tableAssignment;
    private Groups selectedGroup = null;
    private String option = "";
    private String routeGroup;
    private String nrc;
    private String action;
    private String ced = Utils.SelectionModel.getInstance().getProfessor().getId();
    private String page = "1";
    private ProfAssigment selectedAssig;
    File selectedFile = null;
    @FXML
    private Button btnBackMenu;
    @FXML
    private Button btnUpload;
    @FXML
    private Button btnDelete;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hideComponents();
        initializaTableGroups();
        
        loadChoice();
        choiceListener();
        clickGroups();
        clickAssignments();
    }    

    @FXML
    private void selectFile(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            // Establecer un título para el cuadro de diálogo
            fileChooser.setTitle("Selecciona un archivo");
            
            // Configurar el directorio inicial (opcional)
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            
            // Mostrar el cuadro de diálogo y obtener el archivo seleccionado
            selectedFile = fileChooser.showOpenDialog(btnFile.getScene().getWindow());
            
            if (selectedFile != null) {
                
                System.out.println("Archivo seleccionado: " + selectedFile.getAbsolutePath()); 
                textFName.setText(selectedFile.getName());
            } else {
                System.out.println("No se seleccionó ningún archivo.");
            }
    }
    
    private void hideComponents(){
          // Ocultar y deshabilitar el botón
        btnFile.setVisible(false);
        btnFile.setDisable(true);

        // Ocultar y deshabilitar el campo de texto
        textFName.setVisible(false);
        textFName.setDisable(true);

        // Ocultar y deshabilitar el selector de fecha
        date_due.setVisible(false);
        date_due.setDisable(true);

        // Ocultar y deshabilitar la tabla de grupos
        tableGroup.setVisible(false);
        tableGroup.setDisable(true);

        // Ocultar y deshabilitar la tabla de asignaciones
        tableAssignment.setVisible(false);
        tableAssignment.setDisable(true);

        // Dejar visible y habilitado el ChoiceBox
        choiceOptions.setVisible(true);
        choiceOptions.setDisable(false);
        btnUpload.setVisible(false);
        btnUpload.setDisable(false);
    }
    private void showComponents() {
    // Mostrar y habilitar el botón
    btnFile.setVisible(true);
    btnFile.setDisable(false);

    // Mostrar y habilitar el campo de texto
    textFName.setVisible(true);
    textFName.setDisable(false);

    // Mostrar y habilitar el selector de fecha
    date_due.setVisible(true);
    date_due.setDisable(false);

    // Mostrar y habilitar la tabla de grupos
    tableGroup.setVisible(true);
    tableGroup.setDisable(false);

    // Mostrar y habilitar la tabla de asignaciones
    tableAssignment.setVisible(true);
    tableAssignment.setDisable(false);

    // Dejar visible y habilitado el ChoiceBox
    choiceOptions.setVisible(true);
    choiceOptions.setDisable(false);
}
    private void loadChoice(){
        ObservableList<String> options = FXCollections.observableArrayList(
        "Agregar nueva asignacion",
        "Eliminar asignacion"  
    );
        choiceOptions.setItems(options);
    }
    private void choiceListener(){
        choiceOptions.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
             if (newValue.equals("Agregar nueva asignacion")){
                    btnFile.setVisible(true);
                    btnFile.setDisable(false);
                    textFName.setVisible(true);
                    textFName.setDisable(false);
                    tableGroup.setVisible(true);
                    tableGroup.setDisable(false);
                    date_due.setDisable(false);
                    date_due.setVisible(true);
                    btnUpload.setVisible(true);
                    btnUpload.setDisable(false);
             }
             if(newValue.equals("Eliminar asignacion")){
                    hideComponents();
                    tableGroup.setVisible(true);
                    tableGroup.setDisable(false);
                    tableAssignment.setVisible(true);
                    tableAssignment.setDisable(false);
                    initializaTableAssigment();
             }
        });
    }
    private void clickGroups(){
        tableGroup.setOnMouseClicked(event-> {
            selectedGroup = tableGroup.getSelectionModel().getSelectedItem();
            if(selectedGroup != null){
              nrc = selectedGroup.getNrc();
              
              initializaTableAssigment();
            }
            if (selectedGroup != null && option.equals("Eliminar asignacion")){
                initializaTableGroups();
            }
        });
    }
    private void clickAssignments(){
        tableAssignment.setOnMouseClicked(event-> {
            selectedAssig = tableAssignment.getSelectionModel().getSelectedItem();
            if(selectedAssig != null){
                System.out.println("Asignacion");
            }
        });
    }
    private void initializaTableGroups(){
        tableGroup.getColumns().clear();
        TableColumn<Groups, String> nrcColumn = new TableColumn<>("NRC");
        nrcColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Groups) cellData.getValue()).getNrc()));
        
        TableColumn<Groups, String>  courseColumn= new TableColumn<>("Curso");
        courseColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Groups) cellData.getValue()).getCode_course()));
        
        TableColumn<Groups, String>  cedColumn= new TableColumn<>("Cedula del profesor");
        cedColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Groups) cellData.getValue()).getCed_professor()));
        
        TableColumn<Groups, Integer> groupNumberColumn = new TableColumn<>("Numero de grupo");
        groupNumberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(((Groups) cellData.getValue()).getGroup_number()).asObject());
        
        tableGroup.getColumns().addAll(nrcColumn, courseColumn, cedColumn,groupNumberColumn);
        //TableView<Groups> groups, int pageToGet, String code, String acction, String route
        routeGroup = "Groups";
        Thread_groups thread = new Thread_groups(tableGroup,1,Utils.SelectionModel.getInstance().getProfessor().getId(),"GET",routeGroup);
        
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    private void initializaTableAssigment(){
        tableAssignment.getColumns().clear();

        // Columna NRC
        TableColumn<ProfAssigment, String> nrcColumn = new TableColumn<>("NRC");
        nrcColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getNrc())
        );

        // Columna Nombre
        TableColumn<ProfAssigment, String> nameColumn = new TableColumn<>("Nombre");
        nameColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getName())
        );
        // Columna Nombre
        TableColumn<ProfAssigment, String> pathColumn = new TableColumn<>("Path");
        pathColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getUrl())
        );

        // Columna Fecha límite
        TableColumn<ProfAssigment, String> dateColumn = new TableColumn<>("Fecha Límite");
        dateColumn.setCellValueFactory(cellData -> {
            LocalDateTime dueDate = cellData.getValue().getDueDate();
            String formattedDate = dueDate != null 
                ? dueDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                : "Sin fecha";
            return new SimpleStringProperty(formattedDate);
        });

        // Columna ID Asignación
        TableColumn<ProfAssigment, Integer> idColumn = new TableColumn<>("Asignación ID");
        idColumn.setCellValueFactory(cellData -> 
            new SimpleIntegerProperty(cellData.getValue().getId()).asObject()
        );

        tableAssignment.getColumns().addAll(nrcColumn, nameColumn, dateColumn, idColumn, pathColumn);

       //Thread para cargar datos
       //String nrc_group, String action, String method, TableView<ProfAssigment> tableAssignment
        Thread_Assignments thread = new Thread_Assignments( nrc, "profGet", "GET",tableAssignment,page);

       thread.start();
        try {
           thread.join();
       } catch (InterruptedException ex) {
           ex.printStackTrace();
       }
    }

    @FXML
    private void backMenu(ActionEvent event) throws IOException{
        App.changeScene("professorMenu", "Menu del profesor");
    }

    @FXML
    private void upload(ActionEvent event) throws InterruptedException {
        if(selectedGroup != null && selectedFile !=null){
             
             LocalDate selectedDate = date_due.getValue();
             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
             String formattedDate = selectedDate.format(formatter);
             String name = selectedFile.getName();
             //String nrc_group, String action, String method, File file, String date_due
             action = "profCreate";
             Thread_Assignments thread = new Thread_Assignments( nrc, action, "POST",selectedFile,formattedDate);
             thread.start();
             thread.join();
        }
    }

    @FXML
    private void delete(ActionEvent event) throws InterruptedException {
        if(selectedAssig != null){
            int id = selectedAssig.getId();
            //String action, String method, int id_assigment
            action = "profDelete";
            Thread_Assignments thread = new Thread_Assignments(action,"DELETE",id);
            thread.start();
            thread.join();
        }
    }
    
}
