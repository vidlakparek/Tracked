package sample;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class CreateTask {
    public ComboBox priorita;
    public ComboBox group;

    public static void create() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CreateTask.class.getResource("taskCreate.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        Scene scena = new Scene(root1,400,500);
        scena.setFill(Color.TRANSPARENT);
        stage.setScene(scena);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();

    }
    public void initialize() {
        priorita.getItems().removeAll(priorita.getItems());
        priorita.getItems().addAll("1- Velmi nízká", "2 - Nízká ", "3 - Normální","4 - Vysoká","5 - Urgentní");

        group.getItems().removeAll(group.getItems());
        group.getItems().addAll("Úklid", "Vývoj", "Administrativa");

    }
}
