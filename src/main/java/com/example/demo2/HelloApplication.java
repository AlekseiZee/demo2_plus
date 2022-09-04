package com.example.demo2;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("one-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.setTitle("Welcome to Geo Builder!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Открывает диалоговое окно для внесения/изменения рёбер АМС.
     * Если пользователь кликнул "Применить", то изменения сохраняются
     * и возвращается значение true.
     *
     * @param listRibs - лист ребер.
     * @return true, если пользователь кликнул "Применить", в противном случае false.
     */
    public static boolean showRibsEditDialog(List<Double> listRibs) {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("ribs-view.fxml"));
            AnchorPane page = loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ввод рёбер АМС");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(new Stage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            RibsEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            //controller.getTextPanelForRibs();
            controller.setListRibs(listRibs);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

            return controller.isApplyClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}