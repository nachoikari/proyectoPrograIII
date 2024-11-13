package Utils.Threads;

import Models.Administrator;
import Models.University;
import java.io.IOException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONObject;

public class TablesThread extends Thread {

    //Variables para paginacion
    private int page;
    private Button btn_prevPage;
    private Button btn_nextPage;

    //Variable metodo para conocer cual quiere realizar
    private int opt;

    //Variables Contenedores
    @FXML
    private VBox universitiesContainer;

    //constructores
    public TablesThread(int opt, int page, VBox container) {
        this.opt = opt;
        this.page = page;
        universitiesContainer = container;
    }

    //metodos
    @Override
    public void run() {
        if (opt == 2) { //Universidades
            getUniversitiesContains();
            return;
        }
    }

    private void getUniversitiesContains() {
        String per_page = "10";
        String token = Utils.SelectionModel.getInstance().getToken();
        String urlConcat = "/university/showPage?" + "token=" + token + "&page=" + Integer.toString(page) + "&per_page=" + per_page;
        String response = Utils.RemoteConnection.getInstance().connectToServer(urlConcat, "GET", "");
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
            universitiesContainer.getChildren().clear();
            universitiesContainer.getChildren().addAll(container);
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
            Object source = event.getSource();
            if (source instanceof HBox) {
                HBox clickedHBox = (HBox) source;
                University u = (University)clickedHBox.getUserData();
                System.out.println(u.getId());
                try {
                    Utils.SelectionModel.getInstance().setUniversity(university);
                    App.App.changeScene("UniversityAdmin", "Universidad " + name);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.out.println("Error intentando cargar el fxml en la acción de un botón en la clase TablesThread, método CreateUniPanel");
                }
            }
        };
        universityPanel.setOnMouseClicked(onClickHandler);
        return universityPanel;
    }
}
