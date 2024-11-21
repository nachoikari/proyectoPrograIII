package Controllers;

import Models.Groups;
import Models.ProfAssigment;
import Models.assignmentResponse;
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

public class AssignmentStuController implements Initializable {

    @FXML
    private Button btnBack;
    @FXML
    private TableView<Groups> tableGroup;
    @FXML
    private TableView<ProfAssigment> tableAssignment;
    @FXML
    private Button btnSelectFile;
    @FXML
    private TableView<assignmentResponse> tableResponse;
    @FXML
    private ChoiceBox<String> choiceOptions;
    private Groups selectedGroup = null;
    private ProfAssigment selectedAssignment = null;
    private assignmentResponse selectedResponse = null;
    private String routeGroup = "";
    private String nrc = "";
    private String page = "1";
    File selectedFile = null;
    @FXML
    private TextField textFName;
    @FXML
    private Button btnUpload;
    @FXML
    private Button btnDelete;
    @FXML
    private DatePicker due_date;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        textFName.setVisible(false);
        textFName.setDisable(true);
        initializaTableGroups();
        clickGroups();
        clickAssigntmentProf();
        clickResponses();
    }    

    @FXML
    private void backMenu(ActionEvent event) throws IOException {
        App.App.changeScene("studentMenu", "Menu del estudiante");
    }

    @FXML
    private void selectFile(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            // Establecer un título para el cuadro de diálogo
            fileChooser.setTitle("Selecciona un archivo");
            
            // Configurar el directorio inicial (opcional)
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            
            // Mostrar el cuadro de diálogo y obtener el archivo seleccionado
            selectedFile = fileChooser.showOpenDialog(btnSelectFile.getScene().getWindow());
            
            if (selectedFile != null) {
                textFName.setVisible(true);
                System.out.println("Archivo seleccionado: " + selectedFile.getAbsolutePath()); 
                textFName.setText(selectedFile.getName());
            } else {
                System.out.println("No se seleccionó ningún archivo.");
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
        //TableView<Groups> groupsTable, int pageToGet, String acction
        routeGroup = "GroupsPerStudent";
        
        Thread_groups thread = new Thread_groups(tableGroup,1,"GET",routeGroup);
        thread.setCed(Utils.SelectionModel.getInstance().getStudent().getId());
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
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
        //String nrc_group, String action, String method, String page, TableView<assignmentResponse> tableResponse
        Thread_Assignments thread = new Thread_Assignments( nrc, "studentGet", "GET","1",tableResponse);
        thread.start();
        thread.join();
    }
    private void clickGroups(){
        tableGroup.setOnMouseClicked(event-> {
            selectedGroup = tableGroup.getSelectionModel().getSelectedItem();
            if(selectedGroup != null){
                //studentGet
               nrc = selectedGroup.getNrc();
               initializaTableAssignment();
                try {
                    
                    initializaTableResponses();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    private void clickAssigntmentProf(){
        tableAssignment.setOnMouseClicked(event-> {
            selectedAssignment = tableAssignment.getSelectionModel().getSelectedItem();
            if(selectedAssignment != null){
                //System.out.println("Assignacion seleccionada");
            }
        });
    }
    private void clickResponses(){
        tableResponse.setOnMouseClicked(event-> {
            selectedResponse = tableResponse.getSelectionModel().getSelectedItem();
            if(selectedResponse != null){
                System.out.println("respuesta seleccionada");
            }
        });
    }

    @FXML
    private void upload(ActionEvent event) throws InterruptedException {
        if (selectedAssignment != null && selectedFile != null){
            //String action, String method, int id_assigment, File file, String date_due
            LocalDate selectedDate = due_date.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = selectedDate.format(formatter);
            //String action, String method, int id_assigment, File file, String date_due
            Thread_Assignments thread = new Thread_Assignments( "studentCreate", "POST", selectedAssignment.getId(),selectedFile,formattedDate);
            thread.start();
            thread.join();
        }
    }

    @FXML
    private void delete(ActionEvent event) throws InterruptedException {
        if(selectedResponse != null){
            //int id_assigment,String action, String method
            Thread_Assignments thread = new Thread_Assignments(selectedResponse.getId(),"studentDelete","DELETE");
            thread.start();
            thread.join();
        }
        
    }
    
}
