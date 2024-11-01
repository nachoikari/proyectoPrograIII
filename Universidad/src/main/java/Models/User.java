package Models;

/**
 *
 * @author Joseph
 */

public class User {
    private int id;
    private String name;
    private String mail;
    private String token;
    private String faculty;

    public User(int id, String name, String mail, String token, String faculty) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.token = token;
        this.faculty = faculty;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public String getToken() {
        return token;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
    
    
}
