package Models;

public class Career {
    private int id;
    private String name;

    public Career(int id, String career) {
        this.id = id;
        this.name = career;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String career) {
        this.name = career;
    }
    
    
}
