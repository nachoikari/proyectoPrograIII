package Models;


public class Student  extends User{
    private int faculty;
    private String phoneNumber;
    
    public Student(String id, String name, String email, int faculty, String phoneNumber) {
        super(id, name, email);
        this.faculty = faculty;
        this.phoneNumber = phoneNumber;
    }

    public void setFaculty(int faculty) {
        this.faculty = faculty;
    }

    public void setPhoneNumber(String phoneNumber) {
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

    public int getFaculty() {
        return faculty;
    }
    

    public String getPhoneNumber() {
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
