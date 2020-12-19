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
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTask {
    public ComboBox priorita;
    public ComboBox group;
    public Button closeButton;
    public TextField name;
    public DatePicker deadline;
    public TextArea popis;
    public Label wrong;

    public static void create() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CreateTask.class.getResource("taskCreate.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        Scene scena = new Scene(root1,400,500);
        scena.setFill(Color.TRANSPARENT);
        stage.setScene(scena);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();

    }
    public void initialize() {
        priorita.getItems().removeAll(priorita.getItems());
        priorita.getItems().addAll("1- Velmi nízká", "2 - Nízká ", "3 - Normální","4 - Vysoká","5 - Urgentní");

        group.getItems().removeAll(group.getItems());
        group.getItems().addAll("Úklid", "Vývoj", "Administrativa");

    }

    public void addNewTask() {
        int prioritaNum = 0;
        switch (String.valueOf(priorita.getValue())) {
            case "1- Velmi nízká":
                prioritaNum = 1;
                break;
            case "2 - Nízká ":
                prioritaNum = 2;
                break;
            case "3 - Normální":
                prioritaNum = 3;
                break;
            case "4 - Vysoká":
                prioritaNum = 4;
                break;
            case "5 - Urgentní":
                prioritaNum = 5;
                break;
            default:
                prioritaNum = 0;
        }
        if (name.getText() !="" && popis.getText() != "" && deadline !=null && prioritaNum != 0 && group.getValue() != "") {
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
            String sql = "INSERT INTO Tasks (ID, Název,Popisek,Deadline,Priorita,dir_users,Groups,stav) VALUES ('" + 0 + "','" + name.getText() + "','" + popis.getText() + "','" + deadline.getValue() + "','" + prioritaNum + "','" + "" + "','" + group.getValue() + "','" + 0 + "' )";
            try {
                dotaz.executeUpdate(sql);

                dotaz.close();
                close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else wrong.setText("Vyplňte prosím všechny povinné údaje!"); wrong.setVisible(true);
    }

    public void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
