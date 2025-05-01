module game {
    requires transitive javafx.fxml;
    requires transitive javafx.controls;
    requires transitive javafx.media;
    requires transitive javafx.graphics;

    exports com.example.view;
    opens com.example.view to javafx.fxml;
}
