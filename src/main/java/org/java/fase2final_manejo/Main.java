package org.java.fase2final_manejo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.java.fase2final_manejo.models.Linea;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/java/fase2final_manejo/visuals/lateralView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Mantenimiento de Datos");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}