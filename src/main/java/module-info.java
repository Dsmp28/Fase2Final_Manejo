module org.java.fase2final_manejo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    opens org.java.fase2final_manejo to javafx.fxml;
    opens org.java.fase2final_manejo.controllers to javafx.fxml;
    exports org.java.fase2final_manejo.controllers;
    opens org.java.fase2final_manejo.models to com.fasterxml.jackson.databind;

    opens org.java.fase2final_manejo.controllers.edits to javafx.fxml;
    exports org.java.fase2final_manejo.controllers.edits;
    exports org.java.fase2final_manejo;
    exports org.java.fase2final_manejo.models;
}