package com.example.demo2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RibsEditDialogController {

    @FXML
    private TextArea textPanelForRibs;

    @FXML
    private TextArea textPanel;

    private Stage dialogStage;

    List<Double> listRibs = new ArrayList<>();
    private boolean applyClicked = false;

    /**
     * Инициализирует класс-контроллер. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Устанавливает сцену для этого окна.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /*public void getTextPanelForRibs(){
        textPanelForRibs.setText(listRibs);
        textPanelForRibs.getText();
        //return textPanelForRibs;
    }*/

    /**
     * Задаёт ребра, которые будем менять.
     *
     * @param listRibs
     */
    public void setListRibs(List<Double> listRibs) {
        this.listRibs = listRibs;
    }

    /**
     * Returns true, если пользователь кликнул OK, в другом случае false.
     *
     * @return
     */
    public boolean isApplyClicked() {
        return applyClicked;
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке "Применит".
     */
    @FXML
    private void applyClicked() {
        if (isInputValid()) {
            this.listRibs.clear();
            Scanner scanner = new Scanner(textPanelForRibs.getText());
            while (scanner.hasNext()) {
                String s = scanner.next();
                Pattern pattern = Pattern.compile("^[+-]?([0-9]*[.])?[0-9]+$");
                Matcher matcher = pattern.matcher(s);
                if (matcher.matches()) {
                    this.listRibs.add(Double.valueOf(s));
                    textPanel.setText(String.valueOf(listRibs));
                    textPanel.getText();
                    applyClicked = true;
                } else {
                    textPanel.setText("Некорректный ввод данных");
                    textPanel.getText();
                    break;
                }
            }
        }
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке "Закрыть".
     */
    @FXML
    private void cancelRibs() {
        dialogStage.close();
    }

    /**
     * Проверяет пользовательский ввод в текстовых полях.
     *
     * @return true, если пользовательский ввод корректен
     */
    private boolean isInputValid() {
        String errorMessage = "";
        Scanner scanner = new Scanner(textPanelForRibs.getText());
        if (!scanner.hasNext()) {
            errorMessage += "Рёбра не заданы\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
