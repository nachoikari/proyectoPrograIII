package Models;

public class Professor extends User {
    private int faculty;
    private int phoneNumber;
    
    public Professor(String id, String name, String email, int faculty, int phoneNumber) {
        super(id, name, email);
        this.faculty = faculty;
        this.phoneNumber = phoneNumber;
    }

    public int getFaculty() {
        return faculty;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setFaculty(int faculty) {
        this.faculty = faculty;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
}