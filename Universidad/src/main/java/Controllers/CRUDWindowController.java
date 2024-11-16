package Controllers;

import Models.Administrator;
import Models.Professor;
import Models.Student;
import Models.University;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class CRUDWindowController implements Initializable {

    @FXML
    private TextField txt_field1;
    @FXML
    private TextField txt_field5;
    @FXML
    private TextField txt_field4;
    @FXML
    private TextField txt_field3;
    @FXML
    private TextField txt_field2;
    @FXML
    private TextField txt_field6;

    private int opt;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        opt = Utils.SelectionModel.getInstance().getOption();
        System.out.println("Opcion anda en: " + opt);
        prepareFields();
    }

    private void prepareFields() {
        if (opt == 1) {
            prepareToAdmins();
            return;
        }
        if (opt == 2) {
            prepareToUniversities();
            return;
        }
        if (opt == 3) {
            prepareToProfessors();
            return;
        }
        if (opt == 4) {
            prepareToStudents();
            return;
        }
    }

    private void prepareToAdmins() {
        txt_field1.setPromptText("Cedula");
        txt_field2.setPromptText("Contraseña");
        txt_field3.setPromptText("Correo Electronico");
        txt_field4.setPromptText("Name");
        txt_field5.setPromptText("Token");
        txt_field6.setVisible(false);
        if (Utils.SelectionModel.getInstance().isModifying()) { //codigo para cargar los datos y modificar
            Administrator admin = Utils.SelectionModel.getInstance().getAdministrator();
            txt_field1.setText(admin.getId());
            txt_field1.setEditable(false);
            //txt_field2.setText("contra");
            txt_field3.setText(admin.getEmail());
            txt_field4.setText(admin.getName());
            //txt_field5.setText("Token");
        }

    }

    private void prepareToProfessors() {
        txt_field1.setPromptText("Cedula");
        txt_field2.setPromptText("Nombre");
        txt_field3.setPromptText("Contraseña");
        txt_field4.setPromptText("correo");
        txt_field5.setPromptText("id de la carrera");
        txt_field6.setPromptText("Numero Telefonico");
        if (Utils.SelectionModel.getInstance().isModifying()) { //codigo para cargar los datos y modificar
            Professor professor = Utils.SelectionModel.getInstance().getProfessor();
            txt_field1.setText("Cedula");
            txt_field1.setEditable(false);
            txt_field2.setText("Nombre");
            txt_field3.setText("Contraseña");
            txt_field4.setText("correo");
            txt_field5.setText("id de la carrera");
            txt_field6.setText("Numero Telefonico");
        }

    }

    private void prepareToStudents() {
        txt_field1.setPromptText("Cedula");
        txt_field2.setPromptText("Nombre");
        txt_field3.setPromptText("Contraseña");
        txt_field4.setPromptText("correo");
        txt_field5.setPromptText("id de la carrera");
        txt_field6.setPromptText("Numero Telefonico");
        if (Utils.SelectionModel.getInstance().isModifying()) {//codigo para cargar los datos y modificar
            Student student = Utils.SelectionModel.getInstance().getStudent();
            txt_field1.setText(student.getId());
            txt_field1.setEditable(false);
            txt_field2.setText(student.getName());
            //txt_field3.setText(student.getPassword());
            txt_field4.setText(student.getEmail());
            txt_field5.setText(Integer.toString(student.getFaculty()));
            txt_field6.setText(student.getPhoneNumber());
        }

    }

    private void prepareToUniversities() {
        txt_field1.setPromptText("ID");
        txt_field2.setPromptText("Name");
        txt_field3.setPromptText("Direccion");
        txt_field4.setPromptText("Correo Electronico");
        txt_field5.setPromptText("URL");
        txt_field6.setVisible(false);
        if (Utils.SelectionModel.getInstance().isModifying()) { //codigo para cargar los datos y modificar
            University university = Utils.SelectionModel.getInstance().getUniversity();
            txt_field1.setText(Integer.toString(university.getId()));
            txt_field1.setEditable(false);
            txt_field2.setText(university.getName());
            txt_field3.setText(university.getAddress());
            txt_field4.setText(university.getEmail());
            txt_field5.setText(university.getUrl());
        }

    }

}
