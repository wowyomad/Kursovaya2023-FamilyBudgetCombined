package com.gnida;

import javafx.application.Platform;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.WindowEvent;

import java.util.Optional;

public class PrefabDialogs {
    public static void exitDialog(WindowEvent windowEvent) {
        Dialog<Boolean> exitDialog = new Dialog<>();
        exitDialog.setTitle("Выход");
        exitDialog.setHeaderText("Вы уверены, что хотите выйти?");
        exitDialog.getDialogPane().getButtonTypes().add(new ButtonType("Да", ButtonBar.ButtonData.YES));
        exitDialog.getDialogPane().getButtonTypes().add(new ButtonType("Нет", ButtonBar.ButtonData.NO));
        exitDialog.setResultConverter(buttonType -> buttonType.getButtonData().equals(ButtonBar.ButtonData.YES));
        Optional<Boolean> toExit = exitDialog.showAndWait();
        if (toExit.isPresent() && toExit.get()) {
            if(windowEvent == null) Platform.exit();
        } else {
            if(windowEvent != null) windowEvent.consume();
        }
    }

}
