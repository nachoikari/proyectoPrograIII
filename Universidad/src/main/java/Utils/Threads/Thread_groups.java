
package Utils.Threads;

import Models.Course;
import Models.Groups;
import Utils.RemoteConnection;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.TableView;
import org.json.JSONArray;
import org.json.JSONObject;

public class Thread_groups extends Thread {
    private TableView<Course> coursesTable;
    private TableView<Groups> groupsTable;
    private int pageToGet;
    private String code_course;
    private int career_id;
    private String acction;
    private Course course = null;
    private Groups group = null;
    private String route = null;
    private String ced = null;
    public Thread_groups(TableView<Groups> groups, int pageToGet, String code, String acction, String route) {
        this.groupsTable = groups;
        this.pageToGet = pageToGet;
        this.code_course = code;
        this.acction = acction;
        this.route = route;
    }

    public Thread_groups(TableView<Course> courseTable, int pageToGet, int career_id, String acction, String route) {
        this.coursesTable = courseTable;
        this.pageToGet = pageToGet;
        this.career_id = career_id;
        this.acction = acction;
        this.route = route;
    }

    public Thread_groups(TableView<Groups> groupsTable, int pageToGet, String acction, String route) {
        this.groupsTable = groupsTable;
        this.pageToGet = pageToGet;
        this.acction = acction;
        this.route = route;
    }
    
    public Thread_groups(String acction, Course course, Groups group) {
        this.acction = acction;
        this.course = course;
        this.group = group;
    }
    public Thread_groups(String acction,Groups group, String route){
        this.acction = acction;
        this.group = group;
        this.route = route;
    }

    public String getCed() {
        return ced;
    }

    public void setCed(String ced) {
        this.ced = ced;
    }
    
    @Override
    public void run(){
        menu();
    }
    private void menu(){
        if(acction.equals("GET")){
            getMenus();
        }
        if(acction.equals("POST")){
            create();
        }
        if(acction.equals("DELETE")){
            menuDelete();
        }
    }
    private void getMenus(){
        if(route.equals("Courses")){
            getCourses();
        }
        if(route.equals("Groups")){
            getGroups();
        }
        if(route.equals("GroupsPerStudent")){
            getGroupStudent();
        }
        if(route.equals("GroupCourse")){
            getGroupsCourse();
        }
    }
    private void menuDelete(){
        if(route.equals("Groups")){
            deleteGroups();
        }
    }
    private void getCourses(){
        String per_page = "10";
        String token = Utils.SelectionModel.getInstance().getToken();
        String endpoint = "/course/showPage?token="+token+"&career_id="+career_id+"&page="+pageToGet+"&per_page="+per_page;
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "GET", "");
        if (response == null) {
            return;
        }
        System.out.println(response);
        JSONObject jsonObject = new JSONObject(response);
        JSONArray groups = jsonObject.getJSONArray("groups");
        ObservableList<Course> objectList = FXCollections.observableArrayList();
        String name;
        int career;
        String code_course;
        for(int i =0; i<groups.length(); i++){
            JSONObject group = groups.getJSONObject(i);
            name = group.getString("Course_Name");
            code_course = group.getString("Course_Code");
            career = group.getInt("Career");
            Course add_group  = new Course(code_course,name,career);
            objectList.add(add_group);
        }
        Platform.runLater(() -> {
            coursesTable.setItems(objectList);
        });
    }
    private void create(){
        String endpoint = "/group/create/";
        String number_group = String.valueOf(group.getGroup_number());
        String data = "nrc=" + group.getNrc()
            + "&group_number=" + number_group
            + "&ced_professor=" + group.getCed_professor()
            + "&code_course=" + group.getCode_course()
            + "&token=" + Utils.SelectionModel.getInstance().getToken();
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "POST", data);
        System.out.println(response);
    }
    private void deleteGroups(){
        String token = Utils.SelectionModel.getInstance().getToken();
        String endpoint = "/group/delete";
        String data = "nrc=" + group.getNrc()
                +"&token=" + Utils.SelectionModel.getInstance().getToken();
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "DELETE", data);
        System.out.println("==========================");
        System.out.println(response);
    }
    private void getGroups(){
        String per_page = "10";
        String token = Utils.SelectionModel.getInstance().getToken();
        String ced = Utils.SelectionModel.getInstance().getProfessor().getId();
        String endpoint = "/group/showProfGroups?token="+token+"&ced_professor="+ced+"&page="+pageToGet+"&per_page="+per_page;
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "GET", "");
        if (response == null) {
            return;
        }
        System.out.println(response);
        JSONObject jsonObject = new JSONObject(response);
        JSONArray groups = jsonObject.getJSONArray("groups");
        ObservableList<Groups> objectList = FXCollections.observableArrayList();
        
        String course_Name;
        int number_group;
        String code;
        String nrc;
        
        
        for(int i =0; i<groups.length(); i++){
            JSONObject group = groups.getJSONObject(i);
            course_Name = group.getString("Course_Name");
            code = group.getString("Course_Code");
            nrc = group.getString("NRC");
            number_group = group.getInt("Number_group");
            //String nrc, int group_number, String ced_professor, String code_course
            Groups groupGetted = new Groups(nrc,number_group,ced,code);
            objectList.add(groupGetted);
        }
        Platform.runLater(() -> {
            groupsTable.setItems(objectList);
        });
        
        
    }
    private void getGroupsCourse(){
        String per_page = "10";
        String token = Utils.SelectionModel.getInstance().getToken();
        String endpoint = "/group/showAll?token="+token+"&code_course="+code_course+"&page="+pageToGet+"&per_page="+per_page;
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "GET", "");
        System.out.println(response);
        JSONObject jsonObject = new JSONObject(response);
        JSONArray groups = jsonObject.getJSONArray("groups");
        ObservableList<Groups> objectList = FXCollections.observableArrayList();
        
        String ced;
        int number_group;
        String nrc;
        
        for(int i =0; i<groups.length(); i++){
            JSONObject group = groups.getJSONObject(i);
            ced = group.getString("Professor_Ced");
            nrc = group.getString("NRC");
            number_group = group.getInt("Number_group");
            //String nrc, int group_number, String ced_professor, String code_course
            Groups groupGetted = new Groups(nrc,number_group,ced,code_course);
            objectList.add(groupGetted);
        }
        Platform.runLater(() -> {
            groupsTable.setItems(objectList);
        });
    }
    private void getGroupStudent(){
        String token = Utils.SelectionModel.getInstance().getToken();
        String endpoint = "/enroll/showStudentGroups?token="+token+"&ced="+ced;
        String response = RemoteConnection.getInstance().connectToServer(endpoint, "GET", "");
        System.out.println("================\n"+response+"================\n");
        JSONObject jsonObject = new JSONObject(response);
        JSONArray groups = jsonObject.getJSONArray("data");
        ObservableList<Groups> objectList = FXCollections.observableArrayList();
        String ced_prof;
        int number_group;
        String code;
        String nrc;
        for(int i =0; i<groups.length(); i++){
            JSONObject group = groups.getJSONObject(i);
            ced_prof = group.getString("Professor");
            code = group.getString("Code course");
            nrc = group.getString("nrc");
            number_group = group.getInt("group_number");
            //String nrc, int group_number, String ced_professor, String code_course
            Groups groupGetted = new Groups(nrc,number_group,ced_prof,code);
            objectList.add(groupGetted);
        }
        Platform.runLater(() -> {
            groupsTable.setItems(objectList);
        });
    }
}
