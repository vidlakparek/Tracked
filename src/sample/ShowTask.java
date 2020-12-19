package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class ShowTask {

    public Label idAndName;
    public TextArea popis;
    public Label deadline;
    public Label priorita;
    public Label group;
    public Label stav;
    public Button closeButton;

    Task task;


    public void initialize(){

        idAndName.setText(String.valueOf(task.getID()) +". "+task.getName());
    }

    /*public void getTask(){
        task = ControllerLoged.getTaskFromButton(event);
    }*/


    public static void create() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CreateTask.class.getResource("showTask.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        Scene scena = new Scene(root1,700,500);
        scena.setFill(Color.TRANSPARENT);
        stage.setScene(scena);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();

    }

    public void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
