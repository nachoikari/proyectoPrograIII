
package Utils.Threads;

import Models.Groups;
import Models.ProfAssigment;
import Models.assignmentResponse;
import Utils.RemoteConnection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Base64;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author nacho
 */
public class Thread_Assignments extends Thread {
    private String nrc_group;
    private String action;
    private String method;
    private int id_assigment;
    private String page;
    private File file;
    private String date_due;
    TableView<ProfAssigment> tableAssignment;
    TableView<assignmentResponse> tableResponse;
    private int id_response;
    private String comment;
    private String grade;
    private ProfAssigment selectedAssignment = null;
    public Thread_Assignments(String nrc_group, String action, String method, TableView<ProfAssigment> tableAssignment,String page) {
        this.nrc_group = nrc_group;
        this.action = action;
        this.method = method;
        this.tableAssignment = tableAssignment;
        this.page = page;
    
    }

    public Thread_Assignments(String nrc_group, String action, String method, File file, String date_due) {
        this.nrc_group = nrc_group;
        this.action = action;
        this.method = method;
        this.file = file;
        this.date_due = date_due;
    }

    public Thread_Assignments(String action, String method, int id_assigment) {
        this.action = action;
        this.method = method;
        this.id_assigment = id_assigment;
    }
    
    public Thread_Assignments(int id_assigment,String action, String method) {
        this.action = action;
        this.method = method;
        this.id_response = id_assigment;
    }
    public Thread_Assignments(String action,int id_response, String method,String comment, String grade) {
        this.action = action;
        this.method = method;
        this.id_response = id_response;
        this.comment = comment;
        this.grade = grade;
    }
    public Thread_Assignments(int id_assigment,String action, String method,TableView<assignmentResponse> tableResponse) {
        this.action = action;
        this.method = method;
        this.id_response = id_assigment;
        this.tableResponse = tableResponse;
        this.id_assigment = id_assigment;
    }
    public Thread_Assignments(String action, String method, int id_assigment, File file, String date_due) {
        this.action = action;
        this.method = method;
        this.id_assigment = id_assigment;
        this.file = file;
        this.date_due = date_due;
    }

    public Thread_Assignments(String nrc_group, String action, String method, String page, TableView<assignmentResponse> tableResponse) {
        this.nrc_group = nrc_group;
        this.action = action;
        this.method = method;
        this.page = page;
        this.tableResponse = tableResponse;
    }
    
    @Override
    public void run(){
        try {
            menu();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void menu() throws IOException{
        if(method.equals("GET")){
            getOptions();
        }
        if(method.equals("POST")){
            uploadOptions();
        }
        if(method.equals("DELETE")){
            deleteOptios();
        }
        if(method.equals("PUT")){
            putOptions();
        }
    }
    private void getOptions(){
        if(action.equals("profGet")){
            getAssignments();
        }
        if(action.equals("studentGet")){
            getResponses();
        }
        if(action.equals("getResponsesAssignment")){
            getResponsesByAssignment();
        }
    }
    private void putOptions(){
        if(action.equals("evaluate")){
            evaluate();
        }
        if(action.equals("evaluateIA")){
            evaluateIA();
        }
    }
    private void uploadOptions() throws IOException{
        if(action.equals("profCreate")){
            upload();
        }
        if(action.equals("studentCreate")){
            uploadStudent();
        }
    }
    private void deleteOptios(){
        if(action.equals("profDelete")){
            delete();
        }
        if(action.equals("studentDelete")){
            deleteResponses();
        }
    }
    
    private void getAssignments(){
        String per_page = "10";
        String token = Utils.SelectionModel.getInstance().getToken();
        String endpoint = "/assignments/showPergroup?token="+token+"&nrc_group="+nrc_group+"&page="+page+"&per_page="+per_page;
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "GET", "");
        
        JSONObject jsonObject = new JSONObject(response);
        JSONArray assignments = jsonObject.getJSONArray("assignments");
        ObservableList<ProfAssigment> objectList = FXCollections.observableArrayList();
        int id;
        String nrc;
        String url;
        String name;
        String dueDate; 
        for(int i =0; i<assignments.length(); i++){
            JSONObject assignment = assignments.getJSONObject(i);
            name = assignment.getString("name");
            url = assignment.getString("url");
            nrc = assignment.getString("nrc_group");
            id = assignment.getInt("id");
            dueDate = assignment.getString("due_date");
            //int id, String nrc, String url, String name, String dueDateStr
            ProfAssigment groupGetted = new ProfAssigment(id,nrc,url,name,dueDate);
            objectList.add(groupGetted);
        }
        Platform.runLater(() -> {
            tableAssignment.setItems(objectList);
        });
    }
    private void getResponses() {
        String token = Utils.SelectionModel.getInstance().getToken();
        String endpoint = "/student_response/showResponses?token=" + token + "&nrc_group=" + nrc_group 
                          + "&page=" + page + "&per_page=10";
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "GET", "");
        System.out.println("====================\n"+response+"\n===============");
        JSONObject jsonObject = new JSONObject(response);

        if (jsonObject.optInt("Code") == 1) {
            JSONArray responses = jsonObject.getJSONArray("responses");
            ObservableList<assignmentResponse> responseList = FXCollections.observableArrayList();

            for (int i = 0; i < responses.length(); i++) {
                JSONObject responseObj = responses.getJSONObject(i);

                // Extraer los datos
                int id = responseObj.getInt("id");
                int assignmentId = responseObj.getInt("assignment_id");
                String cedStudent = responseObj.getString("student_id");
                String url = responseObj.getString("url");
                String submittedAtStr = responseObj.getString("submitted_at");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime submittedAt = LocalDateTime.parse(submittedAtStr, formatter);
                Float grade = responseObj.isNull("grade") ? null : (float) responseObj.getDouble("grade");
                String comment = responseObj.isNull("comment") ? "" : responseObj.getString("comment");

                // Crear objeto AssignmentResponse
                assignmentResponse assignmentResponse = new assignmentResponse(
                    id,
                    assignmentId,
                    nrc_group,
                    cedStudent,
                    url,
                    submittedAt,
                    grade,
                    comment
                );
                responseList.add(assignmentResponse);
            }

            // Actualizar el TableView
            Platform.runLater(() -> {
                tableResponse.setItems(responseList);
            });
        } else {
            Platform.runLater(() -> {
                //Alert alert = new Alert(Alert.AlertType.WARNING, jsonObject.optString("msg"), ButtonType.OK);
                //alert.showAndWait();
            });
        }
    }
    private void getResponsesByAssignment() {
    String token = Utils.SelectionModel.getInstance().getToken();
    String endpoint = "/student_response/showPerAssignment?token=" + token + "&id_assignment=" + id_assigment;
    String response = RemoteConnection.getInstance().connectToServer(endpoint, "GET", "");
    System.out.println("====================\n" + response + "\n===============");

    JSONObject jsonObject = new JSONObject(response);

    if (jsonObject.optInt("code") == 1) {
        JSONArray responses = jsonObject.getJSONArray("responses");
        ObservableList<assignmentResponse> responseList = FXCollections.observableArrayList();

        for (int i = 0; i < responses.length(); i++) {
            JSONObject responseObj = responses.getJSONObject(i);
            // Extraer los datos del JSON
            int id = responseObj.getInt("id");
            int assignmentIdFromResponse = responseObj.getInt("assignment_id");
            String nrcGroup = responseObj.getString("nrc_group");
            String cedStudent = responseObj.getString("ced_student");
            String url = responseObj.getString("url");
            String submittedAtStr = responseObj.getString("submitted_at");

            // Usar el método de conversión para la fecha
            LocalDateTime submittedAt = parseSubmittedDate(submittedAtStr);

            Float grade = responseObj.isNull("grade") ? null : (float) responseObj.getDouble("grade");
            String comment = responseObj.isNull("comment") ? "" : responseObj.getString("comment");

            // Crear un nuevo objeto assignmentResponse
            assignmentResponse assignmentResponse = new assignmentResponse(
                id,
                assignmentIdFromResponse,
                nrcGroup,
                cedStudent,
                url,
                submittedAt,
                grade,
                comment
            );

            // Agregarlo a la lista observable
            responseList.add(assignmentResponse);
        }

        // Actualizar la tabla en el hilo principal
        Platform.runLater(() -> {
            tableResponse.setItems(responseList);
        });
    } else {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING, jsonObject.optString("msg"), ButtonType.OK);
            alert.showAndWait();
        });
    }
}
    private LocalDateTime parseSubmittedDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Cambiar el patrón para que coincida con el formato recibido

           try {
               // Parseamos la fecha en formato "dd/MM/yyyy" y luego la convertimos a LocalDateTime
               LocalDate date = LocalDate.parse(dateString, formatter);
               return date.atStartOfDay(); // Convertimos LocalDate a LocalDateTime con hora predeterminada 00:00
           } catch (DateTimeParseException e) {
               throw new IllegalArgumentException("Formato de fecha inválido. Se esperaba dd/MM/yyyy: " + dateString, e);
           }
    }




    private void upload() throws IOException{
        String endpoint = "/assignments/create";
        String token = Utils.SelectionModel.getInstance().getToken();
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');

        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            fileName = fileName.substring(0, dotIndex); 
        }

        Path path = file.toPath();
        byte[] fileBytes = Files.readAllBytes(path);

        String fileType = Files.probeContentType(path);

        System.out.println("========================");
        System.out.println(Base64.getEncoder().encodeToString(fileBytes));
        System.out.println(fileType);
        System.out.println(fileName);
        System.out.println("========================");
        String data = "token=" + token +
                "&nrc_group=" + nrc_group +
                "&name=" + fileName +
                "&due_date=" + date_due +
                "&file=" + Base64.getEncoder().encodeToString(fileBytes) + // Encode directly
                "&filename=" + fileName +
                "&filetype=" + fileType;

        String response = RemoteConnection.getInstance().connectToServer(endpoint, "POST", data);
        System.out.println(response);
    }
    private void uploadStudent() throws IOException{
        String endpoint = "/student_response/create";
        String token = Utils.SelectionModel.getInstance().getToken();
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');

        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            fileName = fileName.substring(0, dotIndex); 
        }

        Path path = file.toPath();
        byte[] fileBytes = Files.readAllBytes(path);

        String fileType = Files.probeContentType(path);
        String ced = Utils.SelectionModel.getInstance().getStudent().getId();
        String data = "token=" + token +
                "&student_id=" + ced +
                "&assignment_id=" + id_assigment +
                "&grade=" + 0 +
                "&comment=" + "" +
                "&file=" + Base64.getEncoder().encodeToString(fileBytes) + // Codificar directamente
                "&filename=" + fileName +
                "&filetype=" + fileType;
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "POST", data);
        System.out.println(response);
        
    }
    private void delete(){
        String endpoint = "/assignments/delete";
        String token = Utils.SelectionModel.getInstance().getToken();
        String data = "token=" + token + "&id_file=" + id_assigment;
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "DELETE", data);
        System.out.println(response);
    }
    private void deleteResponses(){
        String endpoint = "/student_response/delete";
        String token = Utils.SelectionModel.getInstance().getToken();
        String data = "token=" + token + "&id_response=" + id_response;
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "DELETE", data);
        System.out.println(response);
    }
    
    
    
    private String encode() throws FileNotFoundException, IOException{
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
        byte[] fileBytes = new byte[(int) file.length()];
        fileInputStream.read(fileBytes);
        
        String encodedString = Base64.getEncoder().encodeToString(fileBytes);
        
        // Asegúrate de que la longitud sea un múltiplo de 4
        int paddingNeeded = encodedString.length() % 4;
        if (paddingNeeded != 0) {
            // Añadir padding (signos de igualdad)
            encodedString += "=".repeat(4 - paddingNeeded);
        }
        
        return encodedString;
        } 
    }
    
    private void evaluate(){
        String token = Utils.SelectionModel.getInstance().getToken();
        String data = "token=" + token + "&comment=" + comment+"&grade="+grade+"&id_response="+id_response;
        String endpoint = "/student_response/manualEvaluate";
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "PUT", data);
    }
    private void evaluateIA(){
        String token = Utils.SelectionModel.getInstance().getToken();
        //String data = "token=" + token + "&comment=" + comment+"&grade="+grade+"";
        //id_response
         String data = "token=" + token+"&id_response="+id_response;
        String endpoint = "/student_response/iaEvaluate";
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "PUT", data);
    }
}
