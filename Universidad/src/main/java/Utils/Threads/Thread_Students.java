/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils.Threads;

import Models.Course;
import Models.Groups;
import Models.Student;
import Utils.RemoteConnection;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.json.JSONArray;
import org.json.JSONObject;

public class Thread_Students extends Thread {

    private TableView<Student> studentsTable;
    private String action;
    private String route;
    private int pageToGet;
    private int career_id;
    private String nrc_group;
    private String grade;
    private String ced_Stu;

    public String getNrc_group() {
        return nrc_group;
    }

    public void setNrc_group(String nrc_group) {
        this.nrc_group = nrc_group;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCed_Stu() {
        return ced_Stu;
    }

    public void setCed_Stu(String ced_Stu) {
        this.ced_Stu = ced_Stu;
    }

    public Thread_Students(TableView<Student> studentsTable, String route, int pageToGet, int career_id, String action, String nrc_group) {
        this.studentsTable = studentsTable;
        this.route = route;
        this.pageToGet = pageToGet;
        this.career_id = career_id;
        this.action = action;
        this.nrc_group = nrc_group;
    }

    public Thread_Students(String action, String route, String nrc_group, String ced_Stu, String grade) {
        this.action = action;
        this.route = route;
        this.nrc_group = nrc_group;
        this.ced_Stu = ced_Stu;
        this.grade = grade;
    }

    @Override
    public void run() {
        menu();
    }

    private void menu() {
        if (action.equals("POST")) {
            enroll();
        }
        if (action.equals("GET")) {
            getsMenu();
        }
        if (action.equals("DELETE")) {
            unEnroll();
        }
    }

    private void getsMenu() {
        if (route.equals("studentsUniversity")) {
            studentsPerUniversity();
        }
        if (route.equals("studentsPerGroup")) {
            studentsPerGroup();
        }
    }

    private void unEnroll() {
        String token = Utils.SelectionModel.getInstance().getToken();
        String endpoint = "/enroll/delete";
        String data = "nrc_group=" + nrc_group
                + "&ced_student=" + ced_Stu
                + "&token=" + Utils.SelectionModel.getInstance().getToken();
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "DELETE", data);
        System.out.println("======================\n" + response + "======================");
    }

    private void enroll() {
        String token = Utils.SelectionModel.getInstance().getToken();
        String endpoint = "/enroll/register";
        String data = "nrc_group=" + nrc_group
                + "&ced_student=" + ced_Stu
                + "&grade=" + grade
                + "&token=" + Utils.SelectionModel.getInstance().getToken();
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "POST", data);
        System.out.println("======================\n" + response + "======================");
    }

    private void studentsPerUniversity() {
        System.out.println("\n###############\nSTUDENTS\n###########");
        String per_page = "10";
        String token = Utils.SelectionModel.getInstance().getToken();
        String endpoint = "/student/showPage?token=" + token + "&career_id=" + career_id + "&page=" + pageToGet + "&per_page=" + per_page;
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "GET", "");
        JSONObject jsonObject = new JSONObject(response);
        JSONArray students = jsonObject.getJSONArray("Student");
        ObservableList<Student> objectList = FXCollections.observableArrayList();
        String name;
        int career;
        String ced;
        String phone_number;
        String email;
        for (int i = 0; i < students.length(); i++) {
            //String id, String name, String email, int faculty, String phoneNumber
            JSONObject stu = students.getJSONObject(i);
            name = stu.getString("name");
            ced = stu.getString("ced");
            career = stu.getInt("career");
            phone_number = stu.getString("phone_number");
            email = stu.getString("email");
            Student student = new Student(ced, name, email, career, phone_number);
            objectList.add(student);
        }
        Platform.runLater(() -> {
            studentsTable.setItems(objectList);
        });
    }

    private void studentsPerGroup() {
        String per_page = "10";
        String token = Utils.SelectionModel.getInstance().getToken();
        System.out.println("token: " + token);
        String endpoint = "/enroll/showPerGroup?token=" + token + "&nrc=" + nrc_group;
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "GET", "");
        System.out.println("=====================\n" + response);
        JSONObject jsonObject = new JSONObject(response);

        String name;
        int career;
        String ced;
        String phone_number;
        String email;
        ObservableList<Student> objectList = FXCollections.observableArrayList();
        try {
            JSONArray students = jsonObject.getJSONArray("data");
            for (int i = 0; i < students.length(); i++) {
                //String id, String name, String email, int faculty, String phoneNumber
                JSONObject stu = students.getJSONObject(i);
                name = stu.getString("name");
                ced = stu.getString("ced");
                career = stu.getInt("career");
                phone_number = stu.getString("phone_number");
                email = stu.getString("email");
                Student student = new Student(ced, name, email, career, phone_number);
                objectList.add(student);
            }
        } catch (Exception e) {
        }

        Platform.runLater(() -> {
            studentsTable.setItems(objectList);
        });
    }
}
