module App {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.base;

    opens App to javafx.fxml;
    opens Controllers to javafx.fxml;
    
    exports App;
}
