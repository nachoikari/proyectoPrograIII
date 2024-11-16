package Utils;

import Models.Professor;
import Models.University;

public class SelectionModel {

    private static SelectionModel selection;
    private int option;
    private String token;
    private University u = null;
    private Professor prof;
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

    public Professor getProf() {
        return prof;
    }

    public void setProf(Professor prof) {
        this.prof = prof;
    }
    
    

}
