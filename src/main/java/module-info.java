module thirdproject.groupchat {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires java.mail;

    opens thirdproject.groupchat to javafx.fxml;
    exports thirdproject.groupchat;

    opens thirdproject.groupchat.Controller to javafx.fxml;
    exports thirdproject.groupchat.Controller;
}