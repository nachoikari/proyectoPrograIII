
package Controllers;

import Models.Groups;
import Models.ProfAssigment;
import Models.assignmentResponse;
import Utils.Threads.Thread_Assignments;
import Utils.Threads.Thread_groups;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;


public class EvaluateController implements Initializable {

    @FXML
    private ChoiceBox<String> choiceOptions;
    @FXML
    private Button btnBack;
    @FXML
    private TableView<Groups> tableGroup;
    @FXML
    private TableView<ProfAssigment> tableAssignment;
    @FXML
    private TableView<assignmentResponse> tableResponse;
    @FXML
    private Button btnEvaluate;
    @FXML
    private Button btnIAEvaluate;
    //
    private String routeGroup;
    private Groups selectedGroup = null;
    private String nrc;
    private ProfAssigment selectedAssignment = null;
    private assignmentResponse selectedResponse = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        choiceOptions.setVisible(false);
        choiceOptions.setDisable(true);
        initializaTableGroups();
        clickGroups();
        clickAssigntmentProf();
        clickResponse();
    }    

    @FXML
    private void back(ActionEvent event) throws IOException {
        App.App.changeScene("professorMenu", "Menu del profesor");
    }

    @FXML
    private void evaluate(ActionEvent event) {
        if(selectedResponse!=null){
            Controllers.EvaluarController.id_response = selectedResponse.getId();
            try {
                // Cargar el archivo FXML de la ventana emergente
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/evaluar.fxml"));
                Parent root = loader.load();

                // Crear una nueva escena para la ventana emergente
                Stage stage = new Stage();
                stage.setTitle("Evaluar Respuesta");
                stage.setScene(new Scene(root));

                // Mostrar la ventana emergente
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
    private void clickGroups(){
        tableGroup.setOnMouseClicked(event-> {
            selectedGroup = tableGroup.getSelectionModel().getSelectedItem();
            if(selectedGroup != null){
                //studentGet
               nrc = selectedGroup.getNrc();
               initializaTableAssignment();
            }
        });
    }
    private void initializaTableAssignment(){
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

        Thread_Assignments thread = new Thread_Assignments( nrc, "profGet", "GET",tableAssignment,"1");

       thread.start();
        try {
           thread.join();
       } catch (InterruptedException ex) {
           ex.printStackTrace();
       }
    }
    private void clickAssigntmentProf(){
        tableAssignment.setOnMouseClicked(event-> {
            selectedAssignment = tableAssignment.getSelectionModel().getSelectedItem();
            if(selectedAssignment != null){
            try {
                    System.out.println("Asignacion seleccionada");
                    initializaTableResponses();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    private void initializaTableResponses() throws InterruptedException{
        tableResponse.getColumns().clear();

        TableColumn<assignmentResponse, String> submittedAtColumn = new TableColumn<>("Fecha de Envío");
        submittedAtColumn.setCellValueFactory(cellData -> {
            LocalDateTime submittedAt = cellData.getValue().getSubmittedAt();
            String formattedDate = submittedAt != null
                    ? submittedAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                    : "Sin fecha";
            return new SimpleStringProperty(formattedDate);
        });

        TableColumn<assignmentResponse, String> gradeColumn = new TableColumn<>("Nota");
        gradeColumn.setCellValueFactory(cellData -> {
            Float grade = cellData.getValue().getGrade();
            return new SimpleStringProperty(grade != null ? String.valueOf(grade) : "Sin calificación");
        });

        TableColumn<assignmentResponse, String> commentColumn = new TableColumn<>("Comentario");
        commentColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getComment() != null ? cellData.getValue().getComment() : "Sin comentarios")
        );

        tableResponse.getColumns().addAll(submittedAtColumn, gradeColumn, commentColumn);
        //(int id_assigment,String action, String method,TableView<assignmentResponse> tableResponse
        Thread_Assignments thread = new Thread_Assignments( selectedAssignment.getId(), "getResponsesAssignment", "GET", tableResponse);
        thread.start();
        thread.join();
    }
    private void clickResponse(){
        tableResponse.setOnMouseClicked(event-> {
            selectedResponse = tableResponse.getSelectionModel().getSelectedItem();
            if(selectedResponse != null){
            try {
                    System.out.println("Respuesta seleccionada");
                    initializaTableResponses();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    
    @FXML
    private void iaEvaluate(ActionEvent event) throws InterruptedException {
        
        Thread_Assignments thread = new Thread_Assignments( "evaluateIA", selectedResponse.getId(), "PUT","","");
        thread.start();
        thread.join();
    }
    
}
