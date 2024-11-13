package Models;

public class University {

    private int id;
    private String email;
    private String name;
    private String address;
    private String url;

    
    public University(int id, String email, String name, String address, String url) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.address = address;
        this.url = url;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
