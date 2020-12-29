package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.text.DateFormatter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.*;
import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;


public class ControllerLoged {
    public Button closeButton;
    int ID = 0;
    String name = null;
    String desc = null;
    Date deadline = null;
    int priority = 0;
    String dir_users = null;
    String groups = null;
    boolean stav = false;
    String solution = null;
    protected static ArrayList<Task> arrayTask;
    String vyvoj="";
    String uklid="";
    String administrativa="";
    boolean bvyvoj = false;
    boolean buklid = false;
    boolean badministrativa = false;

    public AnchorPane arPane;
    public ScrollPane scroll;
    public Label timeLabel;
    public CheckMenuItem clearDone;
    public CheckMenuItem sortByName;
    public CheckMenuItem sortByPriority;
    public CheckMenuItem sortByDeadline;
    public CheckMenuItem ukolyUzivatele;
    public MenuItem addUkol;
    public Button butt[];
    public FlowPane fPane;



    public void initialize(){
        initClock();
        groupInitialize();
        addTasks();
        if(!bvyvoj && !buklid && !badministrativa)addUkol.setDisable(true);
        initializeButtons();
        arPane.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.F5))refresh();
        });
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
            PreparedStatement dotaz = conn.prepareStatement("SELECT * FROM Users");
            ResultSet vysledky = dotaz.executeQuery();

            while (vysledky.next()) {
                if(vysledky.getString(2).equals(Controller.getUserName())){
                    if(vysledky.getString(3).substring(0,1).equals("1"))vyvoj="Vývoj";
                    if(vysledky.getString(4).substring(0,1).equals("1"))uklid="Úklid";
                    if(vysledky.getString(5).substring(0,1).equals("1"))administrativa="Administrativa";
                    if(vysledky.getString(3).substring(1).equals("1"))bvyvoj=true;
                    if(vysledky.getString(4).substring(1).equals("1"))buklid=true;
                    if(vysledky.getString(5).substring(1).equals("1"))badministrativa=true;
                }
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
                if(vysledky.getString(6)==null)dir_users = "none";
                else dir_users = vysledky.getString(6);
                if(vysledky.getString(7)==null)groups = "none";
                else groups = vysledky.getString(7);
                stav = vysledky.getBoolean(8);
                solution = vysledky.getString(9);

                if(groups.equals(vyvoj)||groups.equals(uklid)||groups.equals(administrativa)||dir_users.equals(Controller.userName)) {
                    arrayTask.add(i, new Task(ID, name, desc, deadline, priority, dir_users, groups, stav, solution));
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
            butt[i] = new Button(arrayTask.get(i).getName()+ "\n"+"\n"+arrayTask.get(i).getDeadline());
            butt[i].setLayoutX(10);
            butt[i].setLayoutY(40 + i*100);
            if(arrayTask.get(i).getStav())butt[i].setId("buttonDone");
            else butt[i].setId("button");
            butt[i].setTextAlignment(TextAlignment.CENTER);
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
            Controller.pripojeni = null;
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
        else if(ukolyUzivatele.isSelected()){
            if (sortByName.isSelected()) {
                addTasks();
                show_dir_userOnly();
                sort_by_name();
            } else if (sortByPriority.isSelected()) {
                addTasks();
                show_dir_userOnly();
                sort_by_priority();
            } else if (sortByDeadline.isSelected()) {
                addTasks();
                show_dir_userOnly();
                sort_by_deadline();
            }
            else {
                addTasks();
                show_dir_userOnly();
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

        /* Skryje tasks, které jsou již dokončeny.*/
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
            Collections.sort(arrayTask, comparatorPriority);
            Collections.reverse(arrayTask);
            initializeButtons();
        }
        else refresh();
    }

    public void sort_by_deadline() {
        if(sortByDeadline.isSelected()) {
            sortByName.setSelected(false);
            sortByPriority.setSelected(false);
            Collections.sort(arrayTask, comparatorDeadline);
            initializeButtons();
        }
        else refresh();
    }

    public void show_dir_userOnly(){
        if(ukolyUzivatele.isSelected()){
            for(int i = 0;i<arrayTask.size();i++){
                if(!arrayTask.get(i).getDir_users().equals(Controller.getUserName())) {
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

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        
    }

    private static class nameComparator implements Comparator<Task>{
        Collator czechCollator = Collator.getInstance(new Locale("cz","CZ"));
        public int compare(Task t1, Task t2){
            return czechCollator.compare(t1.getName(),t2.getName());
        }
    }

    Comparator<Task> comparatorDeadline = (o1, o2) -> {
        if (!o1.getDeadline().equals(o2.getDeadline())) {
            return o1.getDeadline().compareTo(o2.getDeadline());
        } else {
            return o1.getName().compareTo(o2.getName());
        }
    };
}

