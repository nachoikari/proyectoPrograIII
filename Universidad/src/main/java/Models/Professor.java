package Models;

public class Professor extends User {
    private int idCareer;
    private String phoneNumber;
    
    public Professor(String id, String name, String email, String password, String phoneNumber, int idCareer) {
        super(id, name, email, password);
        this.idCareer = idCareer;
        this.phoneNumber = phoneNumber;
    }
    
    public Professor(String id, String name, String email, String phoneNumber, int idCareer) {
        super(id, name, email);
        this.idCareer = idCareer;
        this.phoneNumber = phoneNumber;
    }

    public int getIdCareer() {
        return idCareer;
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
    
    public String getPassword(){
        return password;
    }

    public void setIdCareer(int idCareer) {
        this.idCareer = idCareer;
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
    
    
}
