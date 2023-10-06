module fxqqmusicdemo {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media;
    requires rxcontrols;

    exports com.y.player;
    opens com.y.player to javafx.fxml;
}