module com.mycompany.prograproyecto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    opens Controller to javafx.fxml;
    opens com.mycompany.prograproyecto to javafx.fxml;
    exports Model;
    exports com.mycompany.prograproyecto;
     requires org.json;
}
