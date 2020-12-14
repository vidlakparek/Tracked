package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
    int ID = 0;
    String name = null;
    String desc = null;
    Date deadline = null;
    int priority = 0;
    String dir_users = null;
    String groups = null;
    boolean stav = false;

    boolean clear = false;

    public AnchorPane arPane;
    public ScrollPane scroll;
    public Button butt[];
    public FlowPane fPane;
    public Label timeLabel;
    public Task ukoly[];



    public void initialize(){
        createUkoly();
        addTasks();
        initializeButtons();

    }



    public void createUkoly(){
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = Controller.getConnection();
        try {
            PreparedStatement dotaz = conn.prepareStatement("SELECT * FROM Tasks");
            ResultSet vysledky = dotaz.executeQuery();
            int i = 0;
            while (vysledky.next()){i++;}
            ukoly = new Task[i];

        } catch (SQLException throwables) {
        throwables.printStackTrace();
        }
    }

    public void addTasks(){
        ukoly[0]= new Task(0,null,null,null,0,null,null,false);
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = Controller.getConnection();
        try {
            PreparedStatement dotaz = conn.prepareStatement("SELECT * FROM Tasks");
            ResultSet vysledky = dotaz.executeQuery();

            int i = 0;
            while (vysledky.next()) {
                ID = vysledky.getInt(1);
                name = vysledky.getString(2);
                desc = vysledky.getString(3);
                deadline = vysledky.getDate(4);
                priority = vysledky.getInt(5);
                dir_users = vysledky.getString(6);
                groups = vysledky.getString(7);
                stav = vysledky.getBoolean(8);
                ukoly[i] = new Task(ID,name,desc,deadline,priority,dir_users,groups,stav);
                i++;

            }
            dotaz.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void initializeButtons(){
        butt = new Button[ukoly.length];
        butt[0] = new Button("");
        for(int i = 0;i< ukoly.length;i++) {
            System.out.println(ukoly[i].getName()+ukoly[i].getDeadlline());
            butt[i] = new Button(ukoly[i].getName()+ "\n"+"\n"+"\n"+"\n"+ukoly[i].getDeadlline());
            butt[i].setLayoutX(10);
            butt[i].setLayoutY(40 + i*100);
            butt[i].setId("button"+i);
            //butt[i].setBorder(new Border(new BorderStroke( BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            //butt[i].setStyle("task.css");
            butt[i].setPrefSize(680,100);
        }
        if(clear)clear_done();
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
        createUkoly();
        addTasks();
        initializeButtons();
        /*Aktualizace seznamu tasks, stejně jako při stisknutí klávesy F5*/
    }

    public void clear_done() {
        for(int i =0;i<ukoly.length;i++){
            if(ukoly[i].getStav())butt[i].setText("Splněno");
        }
        clear = true;
        /* Skryje tasks, které jsou již dokončeny.*/
    }

    public void sort(ActionEvent event) {
        /*Seřadí tasks podle toho, která možnost byla zvolena.*/
    }

    public void show_all(ActionEvent event){
        clear = false;
        refresh(event);
    }
}

