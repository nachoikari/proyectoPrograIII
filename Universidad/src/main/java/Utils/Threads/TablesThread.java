package Utils.Threads;

import Models.Administrator;
import Models.Career;
import Models.Department;
import Models.Faculty;
import Models.Professor;
import Models.Student;
import Models.University;
import Utils.RemoteConnection;
import java.io.IOException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONObject;

public class TablesThread extends Thread {

    //Variables para paginacion
    private Label lbl_page;
    private Button btn_prevPage;
    private Button btn_nextPage;

    //Variable metodo para conocer cual quiere realizar
    private int opt;
    private int pageToGet;

    //Variables Contenedores
    @FXML
    private VBox VBoxContainer;
    private TableView<Object> objectTable;

    //constructores
    public TablesThread(Button prev_Page, Button next_Page, Label page, VBox container) {
        btn_prevPage = prev_Page;
        this.lbl_page = page;
        VBoxContainer = container;
    }

    public TablesThread(Button prev_Page, Button next_Page, Label page, TableView<Object> container) {
        btn_prevPage = prev_Page;
        this.lbl_page = page;
        objectTable = container;
    }

    //metodos
    @Override
    public void run() {
        if (opt == 1) {//Administradores
            getAdministratorsContains();
            return;
        }
        if (opt == 2) { //UniversidadesHBox
            getUniversitiesPanel();
            return;
        }
        if (opt == 3) { //Profesores
            getProfessorsContains();
            return;
        }
        if (opt == 4) {//Estudiantes
            getStudentsContains();
            return;
        }
        if (opt == 5) {
            getFacultyContains();
        }
        if (opt == 6) {
            getDeparmentContains();
        }
        if (opt == 7) {
            getCareersContains();
        }
        if (opt == 8) {//Universidades TableView
            getUniversitiesContains();
        }
    }

    //Administradores
    private void getAdministratorsContains() {
        String per_page = "10";
        String token = Utils.SelectionModel.getInstance().getToken();
        String endpoint = "/admin/showPage?" + "per_page=" + per_page + "&page=" + pageToGet + "&token=" + token;
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "GET", "");
        if (response == null) {
            return;
        }
        JSONObject jsonObject = new JSONObject(response);
        JSONArray adminsArray = jsonObject.getJSONArray("admins");

        ObservableList<Object> objectList = FXCollections.observableArrayList();
        String id;
        String password;
        String email;
        String name;

        for (int i = 0; i < adminsArray.length(); i++) {
            JSONObject adminObject = adminsArray.getJSONObject(i);
            id = adminObject.getString("ced");
            email = adminObject.getString("email");
            name = adminObject.getString("name");
            password = adminObject.getString("password");
            Administrator admin = new Administrator(id, name, email, password);
            objectList.add(admin);
        }

        Platform.runLater(() -> {
            objectTable.setItems(objectList);
        });
    }

    //Universidades
    private void getUniversitiesContains() {
        String per_page = "10";
        String token = Utils.SelectionModel.getInstance().getToken();
        String endpoint = "/university/showPage?" + "per_page=" + per_page + "&page=" + pageToGet + "&token=" + token;
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "GET", "");
        if (response == null) {
            return;
        }
        JSONObject jsonObject = new JSONObject(response);
        JSONArray professorArray = jsonObject.getJSONArray("universities");

        ObservableList<Object> objectList = FXCollections.observableArrayList();
        int id;
        String email;
        String name;
        String address;
        String url;
        for (int i = 0; i < professorArray.length(); i++) {
            JSONObject object = professorArray.getJSONObject(i);
            id = object.getInt("id");
            email = object.getString("email");
            name = object.getString("name");
            address = object.getString("address");
            if (object.isNull("url")) {
                url = "";
            } else {
                url = object.getString("url");
            }
            University university = new University(id, email, name, address, url);
            objectList.add(university);
        }

        Platform.runLater(() -> {
            objectTable.setItems(objectList);
        });
    }

    private void getUniversitiesPanel() {
        String per_page = "10";
        String token = Utils.SelectionModel.getInstance().getToken();
        String urlConcat = "/university/showPage?" + "token=" + token + "&page=" + pageToGet + "&per_page=" + per_page;
        String response = Utils.RemoteConnection.getInstance().connectToServer(urlConcat, "GET", "");
        if (response == null) {
            return;
        }
        JSONObject jsonObject = new JSONObject(response);
        JSONArray universityArray = jsonObject.getJSONArray("universities");
        ObservableList<Node> container = FXCollections.observableArrayList();
        int id;
        String email;
        String name;
        String address;
        String url;

        for (int i = 0; i < universityArray.length(); i++) {
            JSONObject universityObject = universityArray.getJSONObject(i);
            id = universityObject.getInt("id");
            email = universityObject.getString("email");
            name = universityObject.getString("name");
            address = universityObject.getString("address");
            if (universityObject.isNull("url")) {
                url = null;
            } else {
                url = universityObject.getString("url");
            }
            container.add(createUniversityPanel(id, name, address, email, url));
        }

        Platform.runLater(() -> {
            VBoxContainer.getChildren().clear();
            VBoxContainer.getChildren().addAll(container);
        });
    }

    private HBox createUniversityPanel(int id, String name, String address, String email, String url) {
        University university = new University(id, email, name, address, url);

        HBox universityPanel = new HBox(10); // Espacio entre elementos
        universityPanel.getStyleClass().add("Recuadros_Texto"); //Style
        universityPanel.setPrefHeight(150); // Altura fija para cada noticia

        // Crear los elementos de la noticia
        Label lblName = new Label(name);
        lblName.getStyleClass().add("TextTitle_Label");

        Label lblAddress = new Label(address);
        lblAddress.getStyleClass().add("TextBody_Label");

        Label lblEmail = new Label(email);
        lblEmail.getStyleClass().add("TextBody_Label");

        // Contenedor para el título y contenido
        VBox textContainer = new VBox(lblName, lblAddress, lblEmail);
        textContainer.setSpacing(5);

        Image image = null;
        ImageView imageView = null;

        // Imagen de la Universidad
        if (url != null) {
            try {
                image = new Image(url);
                imageView = new ImageView(image);
                imageView.setFitWidth(250); // Ancho de la imagen
                imageView.setPreserveRatio(true);
            } catch (Exception e) {
                System.out.println("Error al cargar la imagen: " + e.getMessage());
                image = new Image(getClass().getResourceAsStream("/Iconos/Universidad.png"));
                imageView = new ImageView(image);
                imageView.setFitWidth(250); // Ancho de la imagen
                imageView.setPreserveRatio(true);
            }
        } else {
            image = new Image(getClass().getResourceAsStream("/Iconos/Universidad.png"));
            imageView = new ImageView(image);
            imageView.setFitWidth(100); // Ancho de la imagen
            imageView.setFitHeight(100); // Ancho de la imagen
            imageView.setPreserveRatio(true);
        }

        //Settear el panel de la Universidad antes de ser almacenado
        universityPanel.getChildren().addAll(imageView, textContainer);
        universityPanel.setUserData(university);
        EventHandler<MouseEvent> onClickHandler = event -> {
            try {
                Utils.SelectionModel.getInstance().setUniversity(university);
                App.App.changeScene("UniversityAdmin", "Universidad " + name);
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Error intentando cargar el fxml en la acción de un botón en la clase TablesThread, método CreateUniPanel");
            }
        };
        universityPanel.setOnMouseClicked(onClickHandler);
        return universityPanel;
    }

    //Profesores
    private void getProfessorsContains() {
        String per_page = "10";
        String token = Utils.SelectionModel.getInstance().getToken();
        String university_id = Integer.toString(Utils.SelectionModel.getInstance().getUniversity().getId());
        String endpoint = "/professor/showPerUniversity?" + "university_id=" + university_id + "&per_page=" + per_page + "&page=" + pageToGet + "&token=" + token;
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "GET", "");
        if (response == null) {
            return;
        }
        JSONObject jsonObject = new JSONObject(response);

        ObservableList<Object> objectList = FXCollections.observableArrayList();
        String id;
        String email;
        String name;
        int career;
        String phone;
        String password;

        try {
            JSONArray professorArray = jsonObject.getJSONArray("professors");
            for (int i = 0; i < professorArray.length(); i++) {
                JSONObject object = professorArray.getJSONObject(i);
                id = object.getString("ced");
                email = object.getString("email");
                name = object.getString("name");
                career = object.getInt("career");
                phone = object.getString("phone_number");
                password = object.getString("password");
                Professor professor = new Professor(id, name, email, password, phone, career);
                objectList.add(professor);
            }
        } catch (Exception e) {
        }

        Platform.runLater(() -> {
            objectTable.setItems(objectList);
        });
    }

    //Estudiantes
    private void getStudentsContains() {
        String per_page = "10";
        String token = Utils.SelectionModel.getInstance().getToken();
        String university_id = Integer.toString(Utils.SelectionModel.getInstance().getUniversity().getId());
        String endpoint = "/student/showPerUniversity?" + "university_id=" + university_id + "&per_page=" + per_page + "&page=" + pageToGet + "&token=" + token;
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "GET", "");
        if (response == null) {
            return;
        }
        JSONObject jsonObject = new JSONObject(response);

        ObservableList<Object> objectList = FXCollections.observableArrayList();
        String id;
        String email;
        String name;
        int career;
        String phone;
        String password;

        try {
            JSONArray studentArray = jsonObject.getJSONArray("students");
            for (int i = 0; i < studentArray.length(); i++) {
                JSONObject object = studentArray.getJSONObject(i);
                id = object.getString("ced");
                email = object.getString("email");
                name = object.getString("name");
                career = object.getInt("career");
                phone = object.getString("phone_number");
                password = object.getString("password");
                Student student = new Student(id, name, email, password, career, phone);
                objectList.add(student);
            }
        } catch (Exception e) {
        }

        Platform.runLater(() -> {
            objectTable.setItems(objectList);
        });
    }

    //Variables setteables para funcionamiento del hilo (Obligatorio)
    public void setOption(int _option) {
        opt = _option;
    }

    public void setPageToFind(int _page) {
        pageToGet = _page;
    }

    private void getFacultyContains() {
        String per_page = "10";
        String token = Utils.SelectionModel.getInstance().getToken();
        String university_id = Integer.toString(Utils.SelectionModel.getInstance().getUniversity().getId());
        String endpoint = "/faculty/showPerUniversity?" + "id_university=" + university_id + "&per_page=" + per_page + "&page=" + pageToGet + "&token=" + token;
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "GET", "");
        if (response == null) {
            return;
        }
        JSONObject jsonObject = new JSONObject(response);

        ObservableList<Object> objectList = FXCollections.observableArrayList();
        int id;
        String name;
        try {
            JSONArray facultiesArray = jsonObject.getJSONArray("Faculties");
            for (int i = 0; i < facultiesArray.length(); i++) {
                JSONObject object = facultiesArray.getJSONObject(i);
                id = object.getInt("id");
                name = object.getString("name");
                Faculty faculty = new Faculty(id, name);
                objectList.add(faculty);
            }
        } catch (Exception e) {
        }

        Platform.runLater(() -> {
            objectTable.setItems(objectList);
        });
    }

    private void getDeparmentContains() {
        String per_page = "50";
        String token = Utils.SelectionModel.getInstance().getToken();
        String faculty_id = Integer.toString(Utils.SelectionModel.getInstance().getFaculty().getId());
        String endpoint = "/department/showPage?" + "faculty_id=" + faculty_id + "&per_page=" + per_page + "&page=" + pageToGet + "&token=" + token;
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "GET", "");
        if (response == null) {
            return;
        }
        JSONObject jsonObject = new JSONObject(response);

        ObservableList<Object> objectList = FXCollections.observableArrayList();
        int id;
        String name;
        try {
            JSONArray facultiesArray = jsonObject.getJSONArray("groups");
            for (int i = 0; i < facultiesArray.length(); i++) {
                JSONObject object = facultiesArray.getJSONObject(i);
                id = object.getInt("id");
                name = object.getString("name");
                Department department = new Department(id, name);
                objectList.add(department);
            }
        } catch (Exception e) {
        }
        Platform.runLater(() -> {
            objectTable.setItems(objectList);
        });
    }

    private void getCareersContains() {
        String per_page = "10";
        String token = Utils.SelectionModel.getInstance().getToken();
        String depa_id = Integer.toString(Utils.SelectionModel.getInstance().getDepartment().getId());
        String endpoint = "/career/showPage?" + "department_id=" + depa_id + "&per_page=" + per_page + "&page=" + pageToGet + "&token=" + token;
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "GET", "");
        if (response == null) {
            return;
        }
        JSONObject jsonObject = new JSONObject(response);

        ObservableList<Object> objectList = FXCollections.observableArrayList();
        int id;
        String name;
        try {
            JSONArray facultiesArray = jsonObject.getJSONArray("groups");
            for (int i = 0; i < facultiesArray.length(); i++) {
                JSONObject object = facultiesArray.getJSONObject(i);
                id = object.getInt("Id");
                name = object.getString("Career");
                Career career = new Career(id, name);
                objectList.add(career);
            }
        } catch (Exception e) {
        }
        Platform.runLater(() -> {
            objectTable.setItems(objectList);
        });
    }

}
