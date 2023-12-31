module com.owen.macropadgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires json.simple;
    requires jssc;
    requires java.desktop;



    requires com.almasb.fxgl.all;

    opens com.owen.macropadgui to javafx.fxml;
    exports com.owen.macropadgui;
    exports com.owen.macropadgui.devices;
    opens com.owen.macropadgui.devices to javafx.fxml;
    exports com.owen.macropadgui.handlers;
    opens com.owen.macropadgui.handlers to javafx.fxml;
}