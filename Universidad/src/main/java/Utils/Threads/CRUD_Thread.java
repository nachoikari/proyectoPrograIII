package Utils.Threads;

import Utils.RemoteConnection;
import java.io.IOException;
import javafx.application.Platform;
import org.json.JSONObject;

public class CRUD_Thread extends Thread {
    
    //varibles CRUD
    private String id;
    private String objectName;
    private String email;
    private String password;
    private String phoneNumber;
    private String idCareer;
    private String url;
    private String address;
    
    private int opt;
    private boolean deleted;

    public CRUD_Thread() {
        opt = Utils.SelectionModel.getInstance().getOption();
        deleted = false;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setObjectName(String name) {
        this.objectName = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setIdCareer(String idCareer) {
        this.idCareer = idCareer;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isDeleted() {
        return deleted;
    }
    
    
    @Override
    public void run(){
        if (opt == 1) {//Administradores
            adminCRUD();
            return;
        }
        if (opt == 3) { //Profesores
            professorCRUD();
            return;
        }
        if (opt == 4) {//Estudiantes
            studentCRUD();
            return;
        }
        if(opt == 8){//Universidades TableView
            universityCRUD();
        }
    }
    
    //Administradores
    private void adminCRUD(){
        if(Utils.SelectionModel.getInstance().isDeleting()){
            adminDelete();
            return;
        }
        
        if(Utils.SelectionModel.getInstance().isModifying()){
            adminModify();
            return;
        }
        
        adminAdd();
    }
    
    private void adminAdd(){
        String token = Utils.SelectionModel.getInstance().getToken();
        String data = "ced=" + id + "&password=" + password + "&email=" + email + "&name=" + objectName + "&token=" + token;
        String endpoint = "/admin/create";
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "POST", data);
        if(response != null){
            JSONObject jsonResponse = new JSONObject(response);
            int code = jsonResponse.optInt("code");
            if (code == 1) {
                System.out.println("Se agrego a un filipino");
                code1Handler();
            }
        }
    }
    
    private void adminModify(){
        String token = Utils.SelectionModel.getInstance().getToken();
        String data = "ced=" + id + "&password=" + password + "&email=" + email + "&name=" + objectName + "&token=" + token;
        String endpoint = "/admin/update";
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "PUT", data);
        if(response != null){
            JSONObject jsonResponse = new JSONObject(response);
            int code = jsonResponse.optInt("code");
            if (code == 1) {
                System.out.println("Se modifico al filipino");
                Utils.SelectionModel.getInstance().setModifying(false);
                code1Handler();
            }
        }
    }
    
    private void adminDelete(){
        String token = Utils.SelectionModel.getInstance().getToken();
        String data = "ced=" + id +"&token=" + token;
        String endpoint = "/admin/delete";
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "DELETE", data);
        if(response != null){
            JSONObject jsonResponse = new JSONObject(response);
            int code = jsonResponse.optInt("code");
            if (code == 1) {
                System.out.println("Se borro al filipino");
                deleted = true;
            }
        }
    }
    
    
    //Profesores
    private void professorCRUD(){
        if(Utils.SelectionModel.getInstance().isDeleting()){
            professorDelete();
            return;
        }
        if(Utils.SelectionModel.getInstance().isModifying()){
            professorModify();
            return;
        }
        //Esta creando
        professorAdd();
    }
    
    private void professorAdd(){
        String token = Utils.SelectionModel.getInstance().getToken();
        String data = "ced=" + id + "&password="+ password + "&email=" + email + "&name=" + objectName
                +"&phone_number="+phoneNumber+"&id_career="+ idCareer +"&token=" + token;
        String endpoint = "/professor/create";
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "POST", data);
        System.out.println(response);
        if(response != null){
            JSONObject jsonResponse = new JSONObject(response);
            int code = jsonResponse.optInt("code");
            if (code == 1) {
                System.out.println("Se agrego a un filipino");
                code1Handler();
            }
        }
    }
    
    private void professorModify(){
        String token = Utils.SelectionModel.getInstance().getToken();
        String data = "ced=" + id + "&password="+ password + "&email=" + email + "&name=" + objectName
                +"&phone_number="+phoneNumber +"&token=" + token;
        String endpoint = "/professor/update";
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "PUT", data);
        System.out.println(response);
        if(response != null){
            JSONObject jsonResponse = new JSONObject(response);
            int code = jsonResponse.optInt("code");
            if (code == 1) {
                System.out.println("Se modifico a un filipino");
                Utils.SelectionModel.getInstance().setModifying(false);
                code1Handler();
            }
        }
    }
    
    private void professorDelete(){
        String token = Utils.SelectionModel.getInstance().getToken();
        String data = "ced=" + id + "&token=" + token;
        String endpoint = "/professor/delete";
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "DELETE", data);
        System.out.println(response);
        if(response != null){
            JSONObject jsonResponse = new JSONObject(response);
            int code = jsonResponse.optInt("code");
            if (code == 1) {
                deleted = true;
                System.out.println("Se elimino al filipino de un profe");
            }
        }
    }
    
    
    //Estudiantes
    private void studentCRUD(){
        if(Utils.SelectionModel.getInstance().isDeleting()){
            studentDelete();
            return;
        }
        
        if(Utils.SelectionModel.getInstance().isModifying()){
            studentModify();
            return;
        }
        //Esta creando
        studentAdd();
    }
    
    private void studentAdd(){
        String token = Utils.SelectionModel.getInstance().getToken();
        String data = "ced=" + id + "&password="+ password + "&email=" + email + "&name=" + objectName
                +"&phone_number="+phoneNumber+"&id_career="+ idCareer +"&token=" + token;
        String endpoint = "/student/create";
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "POST", data);
        System.out.println(response);
        if(response != null){
            JSONObject jsonResponse = new JSONObject(response);
            int code = jsonResponse.optInt("code");
            if (code == 1) {
                System.out.println("Se agrego a un filipino");
                code1Handler();
            }
        }
    }
    
    private void studentModify(){
        String token = Utils.SelectionModel.getInstance().getToken();
        String data = "ced=" + id + "&password="+ password + "&email=" + email + "&name=" + objectName
                +"&phone_number="+phoneNumber +"&token=" + token;
        String endpoint = "/student/update";
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "PUT", data);
        System.out.println(response);
        if(response != null){
            JSONObject jsonResponse = new JSONObject(response);
            int code = jsonResponse.optInt("code");
            if (code == 1) {
                System.out.println("Se edito a un filipino");
                Utils.SelectionModel.getInstance().setModifying(false);
                code1Handler();
            }
        }
    }
    
    private void studentDelete(){
        String token = Utils.SelectionModel.getInstance().getToken();
        String data = "ced=" + id +"&token=" + token;
        String endpoint = "/student/delete";
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "DELETE", data);
        System.out.println(response);
        if(response != null){
            JSONObject jsonResponse = new JSONObject(response);
            int code = jsonResponse.optInt("code");
            if (code == 1) {
                System.out.println("Se elimino a un filipino");
                deleted = true;
            }
        }
    }
    
    
    //Universidades
    private void universityCRUD(){
        if(Utils.SelectionModel.getInstance().isDeleting()){
            universityDelete();
            return;
        }
        
        if(Utils.SelectionModel.getInstance().isModifying()){
            universityModify();
            return;
        }
        //Esta creando
        universityAdd();
    }
    
    private void universityAdd(){
        String token = Utils.SelectionModel.getInstance().getToken();
        String data = "email=" + email + "&name=" + objectName
                +"&address="+ address +"&url="+ url +"&token=" + token;
        String endpoint = "/university/create";
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "POST", data);
        System.out.println(response);
        if(response != null){
            JSONObject jsonResponse = new JSONObject(response);
            int code = jsonResponse.optInt("code");
            System.out.println(response);
            if (code == 1) {
                System.out.println("Se agrego a un filipino");
                code1Handler();
            }
        }
    }
    
    private void universityModify(){
        String token = Utils.SelectionModel.getInstance().getToken();
        String data = "id=" + id+"&email=" + email + "&name=" + objectName
                +"&address="+ address +"&url="+ url +"&token=" + token;
        String endpoint = "/university/update";
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "PUT", data);
        System.out.println(response);
        if(response != null){
            JSONObject jsonResponse = new JSONObject(response);
            int code = jsonResponse.optInt("code");
            if (code == 1) {
                System.out.println("Se modifico a un filipino");
                Utils.SelectionModel.getInstance().setModifying(false);
                code1Handler();
            }
        }
    }
    
    private void universityDelete(){
        String token = Utils.SelectionModel.getInstance().getToken();
        String data = "id="+ id + "&token=" + token;
        String endpoint = "/university/delete";
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "DELETE", data);
        System.out.println(response);
        if(response != null){
            JSONObject jsonResponse = new JSONObject(response);
            int code = jsonResponse.optInt("code");
            if (code == 1) {
                System.out.println("Se elimino a un filipino");
                deleted = true;
            }
        }
    }

    
    //Handler
    private void code1Handler(){
        Platform.runLater(() -> {
            try {
                App.App.changeScene( "TablesCRUD", "Sistema de Gestión");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
