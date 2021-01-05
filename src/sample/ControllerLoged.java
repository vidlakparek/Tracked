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
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    public Button closeButton;
    int ID = 0;
    String name = null;
    String desc = null;
    Timestamp deadline = null;
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
    public Button[] butt;
    public FlowPane fPane;

    DateTimeFormatter formatterLC = DateTimeFormatter.ofPattern("dd. MMMM HH:mm");
    DateFormat formatter = new SimpleDateFormat("dd. MMMM yyyy HH:mm");



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

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> timeLabel.setText(LocalDateTime.now().format(formatterLC))), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

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
                    if(vysledky.getString(3).charAt(0) == '1')vyvoj="Vývoj";
                    if(vysledky.getString(4).charAt(0) == '1')uklid="Úklid";
                    if(vysledky.getString(5).charAt(0) == '1')administrativa="Administrativa";
                    if(vysledky.getString(3).charAt(1) == '1')bvyvoj=true;
                    if(vysledky.getString(4).charAt(1) == '1')buklid=true;
                    if(vysledky.getString(5).charAt(1) == '1')badministrativa=true;
                }
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
        Timestamp datum = new Timestamp(date.getTime());
        System.out.println(datum);
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
                deadline = vysledky.getTimestamp(4);
                priority = vysledky.getInt(5);
                if(vysledky.getString(6)==null)dir_users = "none";
                else dir_users = vysledky.getString(6);
                if(vysledky.getString(7)==null)groups = "none";
                else groups = vysledky.getString(7);
                stav = vysledky.getBoolean(8);
                solution = vysledky.getString(9);

                if(groups.equals(vyvoj)||groups.equals(uklid)||groups.equals(administrativa)||dir_users.equals(Controller.userName)){
                    if (deadline.before(date) && stav) ;
                    else {
                        arrayTask.add(i, new Task(ID, name, desc, deadline, priority, dir_users, groups, stav, solution));
                        i++;
                    }
                }

            }
            dotaz.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void initializeButtons(){
        butt = new Button[arrayTask.size()];
        String datum;
        for(int i = 0;i< arrayTask.size();i++) {
            datum = formatter.format(arrayTask.get(i).getDeadline());
            System.out.println(arrayTask.get(i).getName()+arrayTask.get(i).getDeadline());
            butt[i] = new Button(arrayTask.get(i).getName()+ "\n"+"\n"+datum);
            butt[i].setLayoutX(10);
            butt[i].setLayoutY(40 + i*100);
            LocalDateTime lc = arrayTask.get(i).getDeadline().toLocalDateTime();
            if(arrayTask.get(i).getStav())butt[i].setId("buttonDone");
            else{ if(lc.isBefore(LocalDateTime.now())) butt[i].setId("buttonPozde");
                else {
                    switch (priority){
                        case 1 : butt[i].setId("button1");
                            break;
                        case 2 : butt[i].setId("button2");
                            break;
                        case 3 : butt[i].setId("button3");
                            break;
                        case 4 : butt[i].setId("button4");
                            break;
                        case 5 : butt[i].setId("button5");
                            break;
                        default: ;
                    }
            }
            }
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

