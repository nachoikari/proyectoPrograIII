package Utils.Threads;

import Models.Professor;
import Models.Student;
import Utils.RemoteConnection;
import java.io.IOException;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import org.json.JSONObject;

/**
 *
 * @author Joseph
 */
public class LoginThread extends Thread {

    private TextField txt_user;
    private TextField txt_password;
    private String user;
    private String password;

    public LoginThread(TextField _user, TextField _password) {
        txt_user = _user;
        txt_password = _password;
        user = _user.getText();
        password = _password.getText();
    }

    @Override
    public void run() {
        if (loginAdmin()) {
            changeFXML("adminMenu", "Sistema de Administración");
            return;
        }

        if (loginProffesor()) {
            changeFXML("professorMenu", "Administrasión Profesores");
            return;
        }

        if (loginStudent()) {
            changeFXML("studentMenu", "CambioEstudiante");
            return;
        }

        userNotFound();
    }

    private boolean loginAdmin() {
        String data = "ced=" + user + "&password=" + password;
        String endpoint = "/admin/login";
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "POST", data);
        if (!response.isEmpty()) {
            JSONObject jsonResponse = new JSONObject(response);
            int code = jsonResponse.optInt("code");
            if (code == 1) {
                String token = jsonResponse.optString("token");
                Utils.SelectionModel.getInstance().setToken(token);
                return true;
            }
        }

        return false;
    }

    private boolean loginProffesor() {
        String data = "ced=" + user + "&password=" + password;
        String endpoint = "/professor/login";
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "POST", data);
        if (!response.isEmpty()) {
            JSONObject jsonResponse = new JSONObject(response);
            int code = jsonResponse.optInt("code");
            if (code == 1) {
                JSONObject jwt = jsonResponse.optJSONObject("jwt");
                String token = jwt.optString("token");
                Utils.SelectionModel.getInstance().setToken(token);
                String ced = jwt.optString("ced");
                int career = jwt.optInt("career");
                Professor prof = new Professor(ced,"","","",career);
                Utils.SelectionModel.getInstance().setProfessor(prof);
                System.out.println(Utils.SelectionModel.getInstance().getProfessor().getFaculty());
                return true;
            }
        }

        return false;
    }

    private boolean loginStudent() {
        String data = "ced=" + user + "&password=" + password;
        String endpoint = "/student/login";
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "POST", data);
        if (!response.isEmpty()) {
            JSONObject jsonResponse = new JSONObject(response);
            int code = jsonResponse.optInt("code");
            if (code == 1) {
                 // Extraer los datos del JSON
            JSONObject jwt = jsonResponse.optJSONObject("jwt");
            if (jwt != null) {
                    String ced = jwt.optString("ced");
                    String name = jwt.optString("name");
                    String email = jwt.optString("email");
                    String token = jwt.optString("token");
                    String phoneNumber = jwt.optString("phone_number");
                    int career = jwt.optInt("career");

                    // Crear el objeto Student
                    Student student = new Student(ced, name, email, null, career, phoneNumber);
                    Utils.SelectionModel.getInstance().setStudent(student);
                    // Imprimir el objeto Student para confirmar
                    System.out.println("Student created: " + student);
                    // Guardar el token en el modelo de selección
                    Utils.SelectionModel.getInstance().setToken(token);
                    return true;
                }
            }   
        }
        return false;
    }
    
    private void changeFXML(String fxml, String tittle) {
        Platform.runLater(() -> {
            try {
                App.App.changeScene(fxml, tittle);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void userNotFound() {
        Platform.runLater(() -> {
            txt_user.clear();
            txt_password.clear();
        });
    }
    
    
}
