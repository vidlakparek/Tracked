package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class ControllerLoged {

    int ID = 0;
    String name = null;
    String desc = null;
    Date deadline = null;
    int priority = 0;
    String dir_users = null;
    String groups = null;
    boolean stav = false;
    protected static ArrayList<Task> arrayTask;
    String userGroup;

    public AnchorPane arPane;
    public ScrollPane scroll;
    public Label timeLabel;
    public CheckMenuItem clearDone;
    public CheckMenuItem sortByName;
    public CheckMenuItem sortByPriority;
    public CheckMenuItem sortByDeadline;
    public CheckMenuItem ukolyUzivatele;
    public Button butt[];
    public FlowPane fPane;



    public void initialize(){
        initClock();
        addTasks();
        initializeButtons();
    }

    public void initClock() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MMMM HH:mm");
            timeLabel.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    /*Zjištění do jakých skupin uživatel patří*/
    public void groupInitialize(){
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = Controller.getConnection();
        try {
            PreparedStatement dotaz = conn.prepareStatement("SELECT Group FROM User");
            ResultSet vysledky = dotaz.executeQuery();

            while (vysledky.next()) {

            }
            dotaz.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addTasks(){
        arrayTask = new ArrayList<>();
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
                if(groups.equals(userGroup)) {
                    arrayTask.add(i, new Task(ID, name, desc, deadline, priority, dir_users, groups, stav));
                    i++;
                }
            }
            dotaz.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void initializeButtons(){
        butt = new Button[arrayTask.size()];
        for(int i = 0;i< arrayTask.size();i++) {
            System.out.println(arrayTask.get(i).getName()+arrayTask.get(i).getDeadline());
            butt[i] = new Button(arrayTask.get(i).getName()+ "\n"+"\n"+"\n"+arrayTask.get(i).getDeadline());
            butt[i].setLayoutX(10);
            butt[i].setLayoutY(40 + i*100);
            butt[i].setId("button");
            butt[i].setPrefSize(600,100);
            int finalI = i;
            butt[i].setOnAction(event -> {
                ShowTask.ID=finalI;
                try {
                    ShowTask.create();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
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
        Scene loged = null;
        if (LogPar != null) {
            loged = new Scene(LogPar,700,500);
        }
        Stage okno = (Stage)((Node)event.getSource()).getScene().getWindow();
            okno.setScene(loged);
            okno.setTitle("Tracked - přihlašování");
            okno.show();
        }

    public void add_task() throws IOException {
        CreateTask.create();
    }

    public void refresh() {
        if(clearDone.isSelected()){
            if (sortByName.isSelected()) {
                addTasks();
                clear_done();
                sort_by_name();
            } else if (sortByPriority.isSelected()) {
                addTasks();
                clear_done();
                sort_by_priority();
            } else if (sortByDeadline.isSelected()) {
                addTasks();
                clear_done();
                sort_by_deadline();
            }
            else {
                addTasks();
                clear_done();
                initializeButtons();
            }
        }
        else {
            if (sortByName.isSelected()) {
                addTasks();
                sort_by_name();
            } else if (sortByPriority.isSelected()) {
                addTasks();
                sort_by_priority();
            } else if (sortByDeadline.isSelected()) {
                addTasks();
                sort_by_deadline();
            }
            else {
                addTasks();
                initializeButtons();
            }
        }
        /*Aktualizace seznamu tasks, stejně jako při stisknutí klávesy F5*/
    }

    public void clear_done() {
        if(clearDone.isSelected()){
            for(int i =0;i<arrayTask.size();i++) {
                if (arrayTask.get(i).getStav()) {
                    arrayTask.remove(i);
                    i--;
                }
                initializeButtons();
            }
        }
        else refresh();

        /* Skryje tasks, které jsou již dokončeny.*/
    }

    public void sort_by_name() {
        sortByPriority.setSelected(false);
        sortByDeadline.setSelected(false);
        Collections.sort(arrayTask, Comparator.comparing(Task::getName));
        initializeButtons();
    }

    public void sort_by_priority() {
        sortByName.setSelected(false);
        sortByDeadline.setSelected(false);
        Collections.sort(arrayTask, comparatorPriority);
        Collections.reverse(arrayTask);
        initializeButtons();
    }

    public void sort_by_deadline() {
        sortByName.setSelected(false);
        sortByPriority.setSelected(false);
        Collections.sort(arrayTask, comparatorDeadline);
        initializeButtons();
    }

    public void show_dir_userOnly(){
        if(ukolyUzivatele.isSelected()){
            for(int i = 0;i<arrayTask.size();i++){
                if(arrayTask.get(i).getDir_users().equals(Controller.getUserName())) {
                    arrayTask.remove(i);
                    i--;
                }
            }
        }
        else {
            addTasks();
        }
        initializeButtons();
    }


    Comparator<Task> comparatorPriority = (o1, o2) -> {
        if(o1.getPriority()!=o2.getPriority()){
            return Integer.compare(o1.getPriority(),o2.getPriority());
        }else{
            return o1.getName().compareTo(o2.getName());
        }
    };

    Comparator<Task> comparatorDeadline = (o1, o2) -> {
        if (!o1.getDeadline().equals(o2.getDeadline())) {
            return o1.getDeadline().compareTo(o2.getDeadline());
        } else {
            return o1.getName().compareTo(o2.getName());
        }
    };


}

