package org.example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ShowTask {

    private static ControllerLoged a;
    public Label idAndName;
    public TextArea popis;
    public Label deadline;
    public Label priorita;
    public Label group;
    public Label stav;
    public Button closeButton;
    public static int ID = 0;
    protected static Scene scena;
    public TextArea solution;
    public Button Submit;
    DateFormat formater = new SimpleDateFormat("dd. MMMM yyyy HH:mm");
    DateTimeFormatter formatterLC = DateTimeFormatter.ofPattern("dd. MMMM HH:mm");


    public static void create(ControllerLoged cl) throws IOException{
        a=cl;
        FXMLLoader fxmlLoader = new FXMLLoader(CreateTask.class.getResource("/FXML/showTask.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        scena = new Scene(root1,375,450);
        scena.setFill(Color.TRANSPARENT);
        stage.setScene(scena);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    public void initialize(){

        idAndName.setText("#"+ControllerLoged.arrayTask.get(ID).getID()+" "+ControllerLoged.arrayTask.get(ID).getName());
        popis.setText(ControllerLoged.arrayTask.get(ID).getDesc());
        deadline.setText(formater.format(ControllerLoged.arrayTask.get(ID).getDeadline()));
        priorita.setText(prioritaText(ControllerLoged.arrayTask.get(ID).getPriority()));
        group.setText(getSkupina());
        stav.setText(done(ControllerLoged.arrayTask.get(ID).getStav()));
        solution.setText(ControllerLoged.arrayTask.get(ID).getSolution());
    }
    public String getSkupina(){
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = Controller.getConnection();
        try {
            PreparedStatement dotaz = conn.prepareStatement("SELECT `NazevSkupiny` FROM `Groups` WHERE `IDGroup` = '"+ControllerLoged.arrayTask.get(ID).getGroup()+"'");
            ResultSet vysledky = dotaz.executeQuery();
            if(vysledky.next())return vysledky.getString("NazevSkupiny");
            dotaz.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }

    public void submit(){
        String sqlUpdate = "UPDATE Tasks SET Stav = 1, Solution = '"+solution.getText()+"\nOdevzdal uživatel: "+Controller.userName+"\n"+ LocalDateTime.now().format(formatterLC) +"' WHERE ID ="+ControllerLoged.arrayTask.get(ID).getID();
        stav.setText("Dokončeno");
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = Controller.getConnection();
        try {
            Statement dotaz = conn.createStatement();
            dotaz.executeUpdate(sqlUpdate);
            dotaz.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        a.refresh();
    }



    public String prioritaText(int i){
        return switch (i) {
            case 1 -> "1 - Velmi nízká";
            case 2 -> "2 - Nízká";
            case 3 -> "3 - Normální";
            case 4 -> "4 - Vysoká";
            case 5 -> "5 - Urgentní";
            default -> "Chyba zadání priority";
        };
    }
    public String done(boolean a){
        if (a){
            solution.setEditable(false);
            Submit.setVisible(false);
            solution.setPrefSize(310,80);
            return"Dokončeno";
        }
        else return"Nedokončeno";
    }
    public void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
