module CLI {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires CORE;

    opens com.soundhive.gui to javafx.fxml;
    exports com.soundhive.gui;
    exports com.soundhive.gui.controllers;
}