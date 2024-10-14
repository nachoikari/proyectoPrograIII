module com.mycompany.prograproyecto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    opens Controller to javafx.fxml;
    opens com.mycompany.prograproyecto to javafx.fxml;
    exports com.mycompany.prograproyecto;
}
