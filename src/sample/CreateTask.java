package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import tornadofx.control.DateTimePicker;

public class CreateTask {
    public ComboBox priorita;
    public ComboBox group;
    public Button closeButton;
    public TextField name;
    public DateTimePicker deadline;
    public TextArea popis;
    public Label wrong;
    public ComboBox dirUser;
    boolean vyvoj = false;
    boolean uklid = false;
    boolean administrativa = false;


    public static void create() throws IOException {
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
        groupInitialize();
        priorita.getItems().addAll("1- Velmi nízká", "2 - Nízká ", "3 - Normální","4 - Vysoká","5 - Urgentní");

        if(vyvoj)group.getItems().add("Vývoj");
        if(uklid)group.getItems().add("Úklid");
        if(administrativa)group.getItems().add("Administrativa");

        dirUser.getItems().removeAll();
        dirUser.getItems().addAll(getAllUsers());

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
                    if(vysledky.getString(3).substring(1).equals("1"))vyvoj=true;
                    if(vysledky.getString(4).substring(1).equals("1"))uklid=true;
                    if(vysledky.getString(5).substring(1).equals("1"))administrativa=true;
                }
            }
            dotaz.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList<String> getAllUsers(){
        ArrayList<String>users = new ArrayList<>();
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = Controller.getConnection();
        try {
            PreparedStatement dotaz = conn.prepareStatement("SELECT Login FROM Users");
            ResultSet vysledky = dotaz.executeQuery();


            while (vysledky.next()) {
                users.add(vysledky.getString(1));
                }
            dotaz.close();
            }
         catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }
    //public void updateDateTimePicker(){
        //deadline
    //}

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
            String skupina = String.valueOf(group.getValue());
            System.out.println(skupina);
            // TODO: 05.01.2021 Opravit vkládání úkolů do databáze 
            String sql;
            if (dirUser.getValue()!=null)sql = "INSERT INTO Tasks (`ID`, `Název`, `Popisek`, `Deadline`, `Priorita`, `Dir_users`, `Stav`) VALUES ('" + 0 + "','" + name.getText() + "','" + popis.getText() + "','" + deadline.getDateTimeValue() + "','" + prioritaNum + "','" + dirUser.getValue() + "','" + 0 + "' )";
            else sql = "INSERT INTO Tasks(`ID`, `Název`, `Popisek`, `Deadline`, `Priorita`, `Groups`, `Stav`) VALUES ('"+0+"','" + name.getText() + "','" + popis.getText() + "','" + deadline.getDateTimeValue() + "','"+prioritaNum+"','"+ group.getValue() + "','"+0+"' )";
            try {
                if (dotaz != null) {
                    dotaz.executeUpdate(sql);
                }

                dotaz.close();
                close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        wrong.setVisible(true);
    }

    public void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
