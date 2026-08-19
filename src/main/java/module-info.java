module com.example.scenariodefense {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.scenariodefense to javafx.fxml;
    exports com.example.scenariodefense;
}