package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import tornadofx.control.DateTimePicker;

public class CreateTask {
    private static ControllerLoged a;
    public ComboBox priorita;
    public ComboBox group;
    public Button closeButton;
    public TextField name;
    public DateTimePicker deadline;
    public TextArea popis;
    public Label wrong;
    public ComboBox dirUser;
    ArrayList<Integer>groups;


    public static void create(ControllerLoged cl) throws IOException {
        a = cl;
        FXMLLoader fxmlLoader = new FXMLLoader(CreateTask.class.getResource("FXML/taskCreate.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        Scene scena = new Scene(root1,400,550);
        scena.setFill(Color.TRANSPARENT);
        stage.setScene(scena);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }
    public void initialize() {
        priorita.getItems().addAll("1- Velmi nízká", "2 - Nízká ", "3 - Normální","4 - Vysoká","5 - Urgentní");
        groupRole();
    }

    public void groupRole(){
        groups = new ArrayList<Integer>();
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = Controller.getConnection();
        try {
            PreparedStatement dotaz = conn.prepareStatement("SELECT IDTeam FROM Mix WHERE IDUser = '"+ControllerLoged.ID_user+"' AND IDRole = 2");
            ResultSet vysledky = dotaz.executeQuery();
            while (vysledky.next()) {
                groups.add(vysledky.getInt("IDTeam"));
            }
            dotaz.close();
            for (int skupina:groups){
                PreparedStatement dotaz1 = conn.prepareStatement("SELECT `NazevSkupiny` FROM `Groups` WHERE `IDGroup` = '"+skupina+"'");
                ResultSet vysledky1 = dotaz1.executeQuery();
                if(vysledky1.next())group.getItems().add(vysledky1.getString("NazevSkupiny"));
            }

        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addNewTask() {
        int prioritaNum = switch (String.valueOf(priorita.getValue())) {
            case "1- Velmi nízká" -> 1;
            case "2 - Nízká " -> 2;
            case "3 - Normální" -> 3;
            case "4 - Vysoká" -> 4;
            case "5 - Urgentní" -> 5;
            default -> 0;
        };

        if (name.getText().equals("") || popis.getText().equals("") || deadline == null || prioritaNum == 0 || (group.getValue() == "" && dirUser.getValue() == "")) {
            wrong.setText("Vyplňte prosím všechny povinné údaje!");
        } else {
            wrong.setVisible(false);
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection conn = Controller.getConnection();
            Statement dotaz = null;
            try {
                dotaz = conn.createStatement();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            int skupina = 0;
            switch (String.valueOf(group.getValue())){
                case "Vývoj": skupina=1;
                    break;
                case "Administrativa": skupina=2;
                    break;
                case "Úklid": skupina=3;
                    break;
            }
            try {
                if (dotaz != null) {
                    dotaz.executeUpdate("INSERT INTO Tasks(`ID`, `Název`, `Popisek`, `Deadline`, `Priorita`, `Stav`,`userSet`, `Groups`) VALUES ('"+0+"','" + name.getText() + "','" + popis.getText() + "','" + deadline.getDateTimeValue() + "','"+prioritaNum+"','"+0+"','"+Controller.getUserName()+"','"+ skupina + "' )");
                }

                dotaz.close();
                close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        wrong.setVisible(true);
        //a.refresh();
    }

    public void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
