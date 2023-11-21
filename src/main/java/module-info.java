module com.petcare.petcare {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.petcare.petcare to javafx.fxml;
    opens com.petcare.petcare.Scenes to javafx.fxml, javafx.graphics;
    opens com.petcare.petcare.Controllers to javafx.fxml;
    exports com.petcare.petcare;
}