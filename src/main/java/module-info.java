module com.example.lab {
    // transitive - чтобы публичные классы могли использовать типы javafx.* (предупреждения IDE)
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.graphics;


    opens com.example.lab to javafx.fxml;
    exports com.example.lab;
}