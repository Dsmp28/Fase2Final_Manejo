package org.java.fase2final_manejo.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public interface MensajesEmergentes {

    // Método para mostrar un mensaje básico
    default void mostrarMensajeEmergente(Alert.AlertType tipo, String titulo, String encabezado, String contenido) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    // Método para mostrar un mensaje con opciones de "Aceptar" y "Cancelar"
    default boolean mostrarMensajeConfirmacion(String titulo, String encabezado, String contenido) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);

        // Configurar los botones "Aceptar" y "Cancelar"
        ButtonType buttonAceptar = new ButtonType("Aceptar");
        ButtonType buttonCancelar = new ButtonType("Cancelar");

        alert.getButtonTypes().setAll(buttonAceptar, buttonCancelar);

        // Mostrar el cuadro de diálogo y esperar la respuesta del usuario
        Optional<ButtonType> result = alert.showAndWait();

        // Devolver true si el usuario presiona "Aceptar", de lo contrario false
        return result.isPresent() && result.get() == buttonAceptar;
    }
}
