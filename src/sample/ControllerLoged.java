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
import java.util.*;


public class ControllerLoged {
    int ID = 0;
    String name = null;
    String desc = null;
    Date deadline = null;
    int priority = 0;
    String dir_users = null;
    String groups = null;
    boolean stav = false;
    ArrayList<Task> arrayTask;
    boolean clear = false;

    public AnchorPane arPane;
    public ScrollPane scroll;
    public Button butt[];
    public FlowPane fPane;
    public Label timeLabel;



    public void initialize(){
        addTasks();
        initializeButtons();

    }



    public void addTasks(){
        arrayTask = new ArrayList<Task>();

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
                arrayTask.add(i,new Task(ID,name,desc,deadline,priority,dir_users,groups,stav));
                i++;

            }
            dotaz.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void initializeButtons(){
        butt = new Button[arrayTask.size()];
        butt[0] = new Button("");
        for(int i = 0;i< arrayTask.size();i++) {
            System.out.println(arrayTask.get(i).getName()+arrayTask.get(i).getDeadlline());
            butt[i] = new Button(arrayTask.get(i).getName()+ "\n"+"\n"+"\n"+"\n"+arrayTask.get(i).getDeadlline());
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
        if(clear){
            addTasks();
            clear_done();
            initializeButtons();
        }
        else{
            addTasks();
            initializeButtons();
        }
        /*Aktualizace seznamu tasks, stejně jako při stisknutí klávesy F5*/
    }

    public void clear_done() {
        for(int i =0;i<arrayTask.size();i++){
            if(arrayTask.get(i).getStav()) {
                arrayTask.remove(i);
                i--;
            }
        }
        initializeButtons();
        clear = true;
        /* Skryje tasks, které jsou již dokončeny.*/
    }

    public void sort_by_name(ActionEvent event) {
        Collections.sort(arrayTask, Comparator.comparing(Task::getName));
        initializeButtons();
    }

    public void sort_by_priority(ActionEvent event) {
        Collections.sort(arrayTask, Comparator.comparing(Task::getPriority));
        Collections.reverse(arrayTask);
        initializeButtons();
    }

    public void sort_by_deadline(ActionEvent event) {
        Collections.sort(arrayTask, Comparator.comparing(Task::getDeadlline));
        initializeButtons();
    }

    public void show_all(ActionEvent event){
        clear = false;
        refresh(event);
    }
}

