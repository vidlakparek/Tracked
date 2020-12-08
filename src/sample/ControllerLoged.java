package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class ControllerLoged {
    public AnchorPane arPane;
    public ScrollPane scroll;
    public Button butt[];
    public FlowPane fPane;

    public void initialize(){
        AddTasks.addTask();
        initializeButtons();
    }

    public void initializeButtons(){
        butt = new Button[AddTasks.getLength()];
        butt[0] = new Button("");
        for(int i = 0;i<AddTasks.getLength();i++) {
            System.out.println(Task.getNameFromArray(2)+Task.getDeadlineFromArray(i));
            butt[i] = new Button(Task.getNameFromArray(i) +"\n" +"\n" +"\n" +"\n" + Task.getDeadlineFromArray(i));
            butt[i].setLayoutX(10);
            butt[i].setLayoutY(40 + i*100);
            //butt[i].setStyle("task.css");
            butt[i].setPrefSize(680,100);
        }
        fPane = new FlowPane();
        fPane.getChildren().addAll(butt);
        scroll.setContent(fPane);

    }

    public void log_out(ActionEvent event) {

        try {
            Controller.pripojeni.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

            Parent LogPar = null;
            try {
                LogPar = FXMLLoader.load(getClass().getResource("sample.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene loged = new Scene(LogPar,700,500);
            Stage okno = (Stage)((Node)event.getSource()).getScene().getWindow();
            okno.setScene(loged);
            okno.setTitle("Tracked - přihlašování");
            okno.show();
        }

    public void add_task(ActionEvent event) throws IOException {
        CreateTask.create();
    }

    public void refresh(ActionEvent event) {
        /*Aktualizace seznamu tasks, stejně jako při stisknutí klávesy F5*/
    }

    public void clear_done(ActionEvent event) {
        /* Skryje tasks, které jsou již dokončeny.*/
    }

    public void sort(ActionEvent event) {
        /*Seřadí tasks podle toho, která možnost byla zvolena.*/
    }
}

