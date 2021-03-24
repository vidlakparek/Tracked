package sample;

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

    /**
     * Metoda načte priority do Comboboxu priorita.
     * Spustí metodu groupRole a addAllUsers
     */
    public void initialize() {
        priorita.getItems().addAll("1- Velmi nízká", "2 - Nízká ", "3 - Normální","4 - Vysoká","5 - Urgentní");
        groupRole();
        addAllUsers();
    }

    /**
     * Metoda se připojí k databázi pomocí getConnection ze třídy Controller.
     * Pomocí výběrového sql dotazu načte skupiny ve kterých má uživatel práva přidávat úkoly a přidá je do ComboBoxu groups.
     */
    public void groupRole(){
        groups = new ArrayList<>();
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

    /**
     * Metoda se připojí k databázi pomocí getConnection ze třídy Controller.
     * Pomocí výběrového sql dotazu načte všechny uživatele, kterým přihlášený uživatel může zadávat úkoly a přidá je do ComboBoxu dirUser.
     *
     */
    public void addAllUsers(){
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = Controller.getConnection();
        try {
            PreparedStatement dotaz = conn.prepareStatement("SELECT * FROM Users");
            ResultSet vysledky = dotaz.executeQuery();
            while (vysledky.next()){
                dirUser.getItems().add(vysledky.getString("Name"));
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    /**
     * Metoda ověří zda jsou všechny vstupy zadané správně.
     * Metoda se připojí k databázi pomocí getConnection ze třídy Controller.
     * Pomocí sql INSERT INTO nahraje uživatelem zadaná data do databáze.
     */

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
            int skupina = switch (String.valueOf(group.getValue())) {
                case "Vývoj" -> 1;
                case "Administrativa" -> 2;
                case "Úklid" -> 3;
                default -> 0;
            };
            int dirUserInt = 0;
            if(skupina==0) {
                try {
                    PreparedStatement selDotaz = conn.prepareStatement("SELECT * FROM `Groups` WHERE NazevSkupiny = '" + String.valueOf(dirUser.getValue()).trim() + "'");
                    ResultSet vysledky = selDotaz.executeQuery();
                    while (vysledky.next()){
                        dirUserInt = vysledky.getInt(1);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    if (dotaz != null) {
                        dotaz.executeUpdate("INSERT INTO Tasks(`ID`, `Název`, `Popisek`, `Deadline`, `Priorita`, `Stav`,`userSet`, `Groups`) VALUES ('" + 0 + "','" + name.getText() + "','" + popis.getText() + "','" + deadline.getDateTimeValue() + "','" + prioritaNum + "','" + 0 + "','" + Controller.getUserName() + "','" + dirUserInt + "' )");
                    }

                    dotaz.close();
                    close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            else {
                try {
                    if (dotaz != null) {
                        dotaz.executeUpdate("INSERT INTO Tasks(`ID`, `Název`, `Popisek`, `Deadline`, `Priorita`, `Stav`,`userSet`, `Groups`) VALUES ('" + 0 + "','" + name.getText() + "','" + popis.getText() + "','" + deadline.getDateTimeValue() + "','" + prioritaNum + "','" + 0 + "','" + Controller.getUserName() + "','" + skupina + "' )");
                    }

                    dotaz.close();
                    close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        wrong.setVisible(true);
        a.refresh();
    }

    public void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
