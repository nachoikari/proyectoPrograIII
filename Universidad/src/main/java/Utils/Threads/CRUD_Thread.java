package Utils.Threads;

import Models.Administrator;
import Utils.RemoteConnection;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.json.JSONArray;
import org.json.JSONObject;

public class CRUD_Thread extends Thread {

    private TableView<Object> objectTable;
    private int opt;
    private String method;
    private int page;

    public CRUD_Thread(int opt, String method, int page, TableView<Object> objectTable) {
        this.opt = opt;
        this.method = method;
        this.page = page;
        this.objectTable = objectTable;
    }

    @Override
    public void run() {
        if (opt == 1) {
            return;
        }
        if (opt == 2) {
            return;
        }
        if (opt == 3) {
            return;
        }
        if (opt == 4) {
            adminCRUD();
            return;
        }

    }

    private void adminCRUD() {
        if (method.equals("showPage")) {
            String per_page = "10";
            String token = RemoteConnection.getInstance().getToken();
            String endpoint = "/admin/showPage?" + "per_page=" + per_page + "&page=" + Integer.toString(page) + "&token=" + token;
            String response = RemoteConnection.getInstance().connectToServer(endpoint, "GET", "");
            JSONObject jsonObject = new JSONObject(response);
            JSONArray adminsArray = jsonObject.getJSONArray("admins");

            ObservableList<Object> objectList = FXCollections.observableArrayList();
            String id;
            String email;
            String name;

            for (int i = 0; i < adminsArray.length(); i++) {
                JSONObject adminObject = adminsArray.getJSONObject(i);
                id = adminObject.getString("ced");
                email = adminObject.getString("email");
                name = adminObject.getString("name");
                Administrator admin = new Administrator(id, name, email);
                objectList.add(admin);
            }

            Platform.runLater(() -> {
                objectTable.setItems(objectList);
            });
        }
    }
}
