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
    protected static int ID;
    protected static Scene scena;


   public void initialize(){
       idAndName.setText("#"+ControllerLoged.arrayTask.get(ID).getID()+" "+ControllerLoged.arrayTask.get(ID).getName());
       popis.setText(ControllerLoged.arrayTask.get(ID).getDesc());
       deadline.setText(ControllerLoged.arrayTask.get(ID).getDeadlline()+"");
       priorita.setText(ControllerLoged.arrayTask.get(ID).getPriority()+"");
       group.setText(ControllerLoged.arrayTask.get(ID).getGroups());
       stav.setText(ControllerLoged.arrayTask.get(ID).getStav()+"");
    }



    public static void create(int i) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CreateTask.class.getResource("showTask.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        scena = new Scene(root1,400,500);
        scena.setFill(Color.TRANSPARENT);
        stage.setScene(scena);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        ID = i;
    }


    public void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
