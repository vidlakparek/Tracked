package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.text.Collator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;


public class ControllerLoged {
    protected static int ID_user = 0;
    protected static ArrayList<Integer> IDTeam;



    public Button closeButton;
    int ID = 0;
    String name = null;
    String desc = null;
    Timestamp deadline = null;
    int priority = 0;
    int group = 0;
    boolean stav = false;
    String solution = null;
    protected static ArrayList<Task> arrayTask;

    public AnchorPane arPane;
    public ScrollPane scroll;
    public Label timeLabel;
    public CheckMenuItem clearDone;
    public CheckMenuItem sortByName;
    public CheckMenuItem sortByPriority;
    public CheckMenuItem sortByDeadline;
    public CheckMenuItem ukolyUzivatele;
    public MenuItem addUkol;
    public Button[] butt;
    public FlowPane fPane;

    DateTimeFormatter formatterLC = DateTimeFormatter.ofPattern("dd. MMMM HH:mm");
    DateFormat formatter = new SimpleDateFormat("dd. MMMM yyyy HH:mm");




    public void initialize(){
        initClock();
        groupInitialize();
        addTasks();
        initializeButtons();

        arPane.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.F5))refresh();
        });

    }

    public void initClock() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> timeLabel.setText(LocalDateTime.now().format(formatterLC))), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    /**
     * Metoda inicializuje všechny skupiny ve kterých se uživatel nachází.
     */
    public void groupInitialize(){
        int i = 0;
        IDTeam = new ArrayList();
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = Controller.getConnection();
        try {
            PreparedStatement dotaz = conn.prepareStatement("SELECT * FROM `Users` WHERE Name = '"+Controller.getUserName().trim()+"'");
            ResultSet vysledky = dotaz.executeQuery();
            if(vysledky.next())ID_user=vysledky.getInt("ID_User");
            dotaz.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            PreparedStatement dotaz = conn.prepareStatement("SELECT IDTeam FROM Mix WHERE IDUser = '"+ID_user+"'");
            ResultSet vysledky = dotaz.executeQuery();
            while (vysledky.next()){
                IDTeam.add(i,vysledky.getInt("IDTeam"));
                i++;
            }
            dotaz.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addTasks(){
        arrayTask = new ArrayList<>();
        Date date = new Date();
        date.setDate(date.getDay()-30);
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = Controller.getConnection();
        for(int j =0;j<IDTeam.size();j++) {
            try {
                PreparedStatement dotaz = conn.prepareStatement("SELECT * FROM Tasks WHERE `Groups` = '"+IDTeam.get(j)+"'");
                ResultSet vysledky = dotaz.executeQuery();
                int i = 0;
                while (vysledky.next()) {
                    ID = vysledky.getInt("ID");
                    name = vysledky.getString("Název");
                    desc = vysledky.getString("Popisek");
                    deadline = vysledky.getTimestamp("Deadline");
                    priority = vysledky.getInt("Priorita");
                    stav = vysledky.getBoolean("Stav");
                    solution = vysledky.getString("Solution");
                    group = vysledky.getInt("Groups");
                    if (deadline.before(date) && stav) ;
                    else {
                        arrayTask.add(i, new Task(ID, name, desc, deadline, priority, stav, solution, group));
                        i++;
                    }
                }
                dotaz.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

        public void initializeButtons(){
            butt = new Button[arrayTask.size()];
            String datum;
            for(int i = 0;i< arrayTask.size();i++) {
                datum = formatter.format(arrayTask.get(i).getDeadline());
                butt[i] = new Button(arrayTask.get(i).getName()+ "\n"+"\n"+datum);
                butt[i].setLayoutX(10);
                butt[i].setLayoutY(40 + i*100);
                LocalDateTime lc = arrayTask.get(i).getDeadline().toLocalDateTime();
                if(arrayTask.get(i).getStav())butt[i].setId("buttonDone");
                else{ if(lc.isBefore(LocalDateTime.now())) butt[i].setId("buttonPozde");
                    else {
                    switch (arrayTask.get(i).getPriority()) {
                        case 1 -> butt[i].setId("button1");
                        case 2 -> butt[i].setId("button2");
                        case 3 -> butt[i].setId("button3");
                        case 4 -> butt[i].setId("button4");
                        case 5 -> butt[i].setId("button5");
                    }
                }
                }
                butt[i].setTextAlignment(TextAlignment.CENTER);
                butt[i].setPrefSize(600,100);
                int finalI = i;
                butt[i].setOnAction(event -> {
                    ShowTask.ID=finalI;
                    try {
                        ShowTask.create(this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
            }
            fPane = new FlowPane();
            fPane.setPadding(new Insets(10, 20, 0, 40));
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
                LogPar = FXMLLoader.load(getClass().getResource("FXML/sample.fxml"));
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
            Controller.pripojeni = null;
        }

    public void add_task() throws IOException {
        CreateTask.create(this);
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
        }else {
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


    }

    public void sort_by_name() {
        if(sortByName.isSelected()){
            sortByPriority.setSelected(false);
            sortByDeadline.setSelected(false);
            arrayTask.sort(new nameComparator());
            initializeButtons();
        }
        else refresh();
    }

    public void sort_by_priority() {
        if(sortByPriority.isSelected()) {
            sortByName.setSelected(false);
            sortByDeadline.setSelected(false);
            arrayTask.sort(comparatorPriority);
            Collections.reverse(arrayTask);
            initializeButtons();
        }
        else refresh();
    }

    public void sort_by_deadline() {
        if(sortByDeadline.isSelected()) {
            sortByName.setSelected(false);
            sortByPriority.setSelected(false);
            arrayTask.sort(comparatorDeadline);
            initializeButtons();
        }
        else refresh();
    }


    Comparator<Task> comparatorPriority = (t1, t2) -> {
        Collator czechCollator = Collator.getInstance(new Locale("cs","CZ"));
        if(t1.getPriority()!=t2.getPriority()){
            return Integer.compare(t1.getPriority(),t2.getPriority());
        }else{
            return czechCollator.compare(t1.getName(),t2.getName());
        }
    };

    public void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        
    }

    private static class nameComparator implements Comparator<Task>{
        Collator czechCollator = Collator.getInstance(new Locale("cs","CZ"));
        public int compare(Task t1, Task t2){
            return czechCollator.compare(t1.getName(),t2.getName());
        }
    }

    Comparator<Task> comparatorDeadline = (t1, t2) -> {
        Collator czechCollator = Collator.getInstance(new Locale("cs","CZ"));
        if (!t1.getDeadline().equals(t2.getDeadline())) {
            return t1.getDeadline().compareTo(t2.getDeadline());
        } else {
            return czechCollator.compare(t1.getName(),t2.getName());
        }
    };

}

