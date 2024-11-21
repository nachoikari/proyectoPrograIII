package Controllers;

import Models.Administrator;
import Models.Career;
import Models.Department;
import Models.Faculty;
import Models.Professor;
import Models.Student;
import Models.University;
import Utils.Threads.CRUD_Thread;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    @FXML
    private Button btn_backToMenu;
    @FXML
    private Button btn_confirm;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        opt = Utils.SelectionModel.getInstance().getOption();
        prepareFields();
    }

    private void prepareFields() {
        if (opt == 1) {
            prepareToAdmins();
            return;
        }
        if (opt == 8) {
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
        if (opt == 5) {
            prepareToFaculty();
            return;
        }
        if (opt == 6) {
            prepareToDepartment();
            return;
        }
        if (opt == 7) {
            prepareToCareer();
            return;
        }
    }

    private void prepareToAdmins() {
        txt_field1.setPromptText("Cedula");
        txt_field2.setPromptText("Contraseña");
        txt_field3.setPromptText("Nombre");
        txt_field4.setPromptText("Correo Electronico");
        txt_field5.setVisible(false);
        txt_field6.setVisible(false);
        if (Utils.SelectionModel.getInstance().isModifying()) { //codigo para cargar los datos y modificar
            Administrator admin = Utils.SelectionModel.getInstance().getAdministrator();
            txt_field1.setText(admin.getId());
            txt_field1.setEditable(false);
            txt_field2.setText(admin.getPassword());
            txt_field3.setText(admin.getName());
            txt_field4.setText(admin.getEmail());
        }

    }

    private void prepareToProfessors() {
        txt_field1.setPromptText("Cedula");
        txt_field2.setPromptText("Contraseña");
        txt_field3.setPromptText("Nombre");
        txt_field4.setPromptText("correo");
        txt_field5.setPromptText("id de la carrera");
        txt_field6.setPromptText("Numero Telefonico");
        if (Utils.SelectionModel.getInstance().isModifying()) { //codigo para cargar los datos y modificar
            Professor professor = Utils.SelectionModel.getInstance().getProfessor();
            txt_field1.setText(professor.getId());
            txt_field1.setEditable(false);
            txt_field2.setText(professor.getPassword());
            txt_field3.setText(professor.getName());
            txt_field4.setText(professor.getEmail());
            txt_field5.setText(Integer.toString(professor.getIdCareer()));
            txt_field5.setEditable(false);
            txt_field6.setText(professor.getPhoneNumber());
        }
    }

    private void prepareToStudents() {
        txt_field1.setPromptText("Cedula");
        txt_field2.setPromptText("Contraseña");
        txt_field3.setPromptText("Nombre");
        txt_field4.setPromptText("correo");
        txt_field5.setPromptText("id de la carrera");
        txt_field6.setPromptText("Numero Telefonico");
        if (Utils.SelectionModel.getInstance().isModifying()) {//codigo para cargar los datos y modificar
            Student student = Utils.SelectionModel.getInstance().getStudent();
            txt_field1.setText(student.getId());
            txt_field1.setEditable(false);
            txt_field2.setText(student.getPassword());
            txt_field3.setText(student.getName());
            txt_field4.setText(student.getEmail());
            txt_field5.setText(Integer.toString(student.getIdCareer()));
            txt_field5.setEditable(false);
            txt_field6.setText(student.getPhoneNumber());
        }

    }

    private void prepareToUniversities() {
        txt_field1.setVisible(false);
        txt_field2.setPromptText("Name");
        txt_field3.setPromptText("Direccion");
        txt_field4.setPromptText("Correo Electronico");
        txt_field5.setPromptText("URL");
        txt_field6.setVisible(false);
        if (Utils.SelectionModel.getInstance().isModifying()) { //codigo para cargar los datos y modificar
            txt_field1.setPromptText("ID");
            txt_field1.setVisible(true);
            University university = Utils.SelectionModel.getInstance().getUniversity();
            txt_field1.setText(Integer.toString(university.getId()));
            txt_field1.setEditable(false);
            txt_field2.setText(university.getName());
            txt_field3.setText(university.getAddress());
            txt_field4.setText(university.getEmail());
            txt_field5.setText(university.getUrl());
        }

    }

    @FXML
    private void backToTableCRUD(ActionEvent event) throws IOException {
        if (opt == 5 || opt == 6 || opt == 7) {
            App.App.changeScene("DepartFacultyAdmin", "Administrar Carreras");
            return;
        }
        App.App.changeScene("TablesCRUD", "Tabla");
    }

    @FXML
    private void callThread(ActionEvent event) {
        if (opt == 1) {//admis
            CRUD_Thread thrd = new CRUD_Thread();
            thrd.setId(txt_field1.getText());
            thrd.setPassword(txt_field2.getText());
            thrd.setObjectName(txt_field3.getText());
            thrd.setEmail(txt_field4.getText());
            thrd.start();
            return;
        }
        if (opt == 8) {//unis
            CRUD_Thread thrd = new CRUD_Thread();
            thrd.setId(txt_field1.getText());
            thrd.setObjectName(txt_field2.getText());
            thrd.setAddress(txt_field3.getText());
            thrd.setEmail(txt_field4.getText());
            thrd.setUrl(txt_field5.getText());
            thrd.start();
        }
        if (opt == 3) {//profes
            CRUD_Thread thrd = new CRUD_Thread();
            thrd.setId(txt_field1.getText());
            thrd.setPassword(txt_field2.getText());
            thrd.setObjectName(txt_field3.getText());
            thrd.setEmail(txt_field4.getText());
            thrd.setIdCareer(txt_field5.getText());
            thrd.setPhoneNumber(txt_field6.getText());
            thrd.start();
        }
        if (opt == 4) {//estudiantes
            CRUD_Thread thrd = new CRUD_Thread();
            thrd.setId(txt_field1.getText());
            thrd.setPassword(txt_field2.getText());
            thrd.setObjectName(txt_field3.getText());
            thrd.setEmail(txt_field4.getText());
            thrd.setIdCareer(txt_field5.getText());
            thrd.setPhoneNumber(txt_field6.getText());
            thrd.start();
            return;
        }
        if (opt == 5) {//Facultades
            CRUD_Thread thrd = new CRUD_Thread();
            thrd.setId(txt_field1.getText());
            thrd.setPassword(txt_field3.getText());
            thrd.setObjectName(txt_field2.getText());
            thrd.setEmail(txt_field4.getText());
            thrd.setIdCareer(Integer.toString(Utils.SelectionModel.getInstance().getUniversity().getId()));
            thrd.setPhoneNumber(txt_field6.getText());
            thrd.start();
            return;
        }
        if (opt == 6) {//Departamentos
            CRUD_Thread thrd = new CRUD_Thread();
            thrd.setId(txt_field1.getText());
            thrd.setPassword(txt_field3.getText());
            thrd.setObjectName(txt_field2.getText());
            thrd.setEmail(txt_field4.getText());
            thrd.setIdCareer(Integer.toString(Utils.SelectionModel.getInstance().getFaculty().getId()));
            thrd.setPhoneNumber(txt_field6.getText());
            thrd.start();
            return;
        }
        if (opt == 7) {//Carreras
            CRUD_Thread thrd = new CRUD_Thread();
            thrd.setId(txt_field1.getText());
            thrd.setPassword(txt_field3.getText());
            thrd.setObjectName(txt_field2.getText());
            thrd.setEmail(txt_field4.getText());
            thrd.setIdCareer(Integer.toString(Utils.SelectionModel.getInstance().getDepartment().getId()));
            thrd.setPhoneNumber(txt_field6.getText());
            thrd.start();
            return;
        }
    }

    private void prepareToFaculty() {
        txt_field1.setVisible(false);
        txt_field2.setPromptText("Nombre de la Facultad");
        txt_field3.setVisible(false);
        txt_field4.setVisible(false);
        txt_field5.setVisible(false);
        txt_field6.setVisible(false);
        if (Utils.SelectionModel.getInstance().isModifying()) { //codigo para cargar los datos y modificar
            Faculty f = Utils.SelectionModel.getInstance().getFaculty();
            txt_field1.setText(Integer.toString(f.getId()));
            txt_field1.setEditable(false);
            txt_field1.setVisible(true);
            txt_field2.setText(f.getName());
        }
    }

    private void prepareToDepartment() {
        txt_field1.setVisible(false);
        txt_field2.setPromptText("Nombre del departamento");
        txt_field3.setVisible(false);
        txt_field4.setVisible(false);
        txt_field5.setVisible(false);
        txt_field6.setVisible(false);
        if (Utils.SelectionModel.getInstance().isModifying()) { //codigo para cargar los datos y modificar
            Department d = Utils.SelectionModel.getInstance().getDepartment();
            txt_field1.setText(Integer.toString(d.getId()));
            txt_field1.setEditable(false);
            txt_field1.setVisible(true);
            txt_field2.setText(d.getName());
        }
    }
    

        private void prepareToCareer() {
        txt_field1.setVisible(false);
        txt_field2.setPromptText("Nombre de la carrera");
        txt_field3.setVisible(false);
        txt_field4.setVisible(false);
        txt_field5.setVisible(false);
        txt_field6.setVisible(false);
        if (Utils.SelectionModel.getInstance().isModifying()) { //codigo para cargar los datos y modificar
            Career c = Utils.SelectionModel.getInstance().getCareer();
            txt_field1.setText(Integer.toString(c.getId()));
            txt_field1.setEditable(false);
            txt_field1.setVisible(true);
            txt_field2.setText(c.getName());
        }
    }
}
