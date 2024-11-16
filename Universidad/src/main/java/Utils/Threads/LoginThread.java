package Utils.Threads;

import Models.Professor;
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
            //cambiar a ventana del profesor
            changeFXML("professorMenu", "CambioProfe");
            return;
        }

        if (loginStudent()) {
            //cambiar a ventana del student
            changeFXML("adminMain", "CambioEstudiante");
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
                Utils.SelectionModel.getInstance().setProf(prof);
                System.out.println(Utils.SelectionModel.getInstance().getProf().getFaculty());
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
                String token = jsonResponse.optString("token");
                Utils.SelectionModel.getInstance().setToken(token);
                
                //System.out.println(Utils.SelectionModel.getInstance().(user));
                return true;
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
