module org.java.fase2final_manejo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens org.java.fase2final_manejo to javafx.fxml;
    exports org.java.fase2final_manejo;
    exports org.java.fase2final_manejo.controllers to javafx.fxml;
}