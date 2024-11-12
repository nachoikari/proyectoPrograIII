package Models;


public class Student  extends User{
    private String faculty;
    private int phoneNumber;
    
    public Student(String id, String name, String email, String faculty, int phoneNumber) {
        super(id, name, email);
        this.faculty = faculty;
        this.phoneNumber = phoneNumber;
    }

    public void setFaculty(String faculty) {
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

    public String getFaculty() {
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
    
    
}
