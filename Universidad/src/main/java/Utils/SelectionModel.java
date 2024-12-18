package Utils;

import Models.Administrator;
import Models.Career;
import Models.Department;
import Models.Faculty;
import Models.Professor;
import Models.Student;
import Models.University;

public class SelectionModel {

    private static SelectionModel selection;
    private int option;
    private String token;
    private University u = null;
    private Student s = null;
    private Professor p= null;
    private Administrator a = null;
    private Faculty f = null;
    private Department d = null;
    private Career career = null;
    private boolean modifying = false;
    private boolean deleting = false;
    
    private SelectionModel() {
    }

    public static SelectionModel getInstance() {
        if (selection == null) {
            selection = new SelectionModel();
        }
        return selection;
    }

    public String getToken() {
        return token;
    }

    public Career getCareer() {
        return career;
    }

    public void setCareer(Career career) {
        this.career = career;
    }

    public int getOption() {
        return option;
    }
    
    public University getUniversity(){
        return u;
    }

    public void setToken(String _token) {
        token = _token;
    }

    public void setOption(int _option) {
        option = _option;
    }

    public void setUniversity(University university){
        u = university;
    }
    
    public boolean isModifying(){
        return modifying;
    }
    
    public boolean isDeleting(){
        return deleting;
    }

    public Student getStudent() {
        return s;
    }

    public Professor getProfessor() {
        return p;
    }

    public Administrator getAdministrator() {
        return a;
    }

    public void setStudent(Student _student) {
        s = _student;
    }

    public void setProfessor(Professor _professor) {
        p = _professor;
    }

    public void setAdministrator(Administrator _administrator) {
        a = _administrator;
    }
    
    public void setModifying(boolean bool) {
        modifying = bool;
    }
    
    public void setDeleting(boolean bool) {
        deleting = bool;
    }

    public Faculty getFaculty() {
        return f;
    }

    public Department getDepartment() {
        return d;
    }

    public void setFaculty(Faculty faculty) {
        this.f = faculty;
    }

    public void setDepartment(Department department) {
        this.d = department;
    }
    
    
}
