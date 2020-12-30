package sample;

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


public class ShowTask {

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


    public static void create() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(CreateTask.class.getResource("showTask.fxml"));
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
        deadline.setText(ControllerLoged.arrayTask.get(ID).getDeadline()+"");
        priorita.setText(prioritaText(ControllerLoged.arrayTask.get(ID).getPriority()));
        if (!ControllerLoged.arrayTask.get(ID).getDir_users().equals("none"))group.setText(ControllerLoged.arrayTask.get(ID).getDir_users());
        else group.setText(ControllerLoged.arrayTask.get(ID).getGroups());
        stav.setText(done(ControllerLoged.arrayTask.get(ID).getStav()));
        solution.setText(ControllerLoged.arrayTask.get(ID).getSolution());
    }

    public void submit(){
        String sqlUpdate = "UPDATE Tasks SET Stav = 1, Solution = '"+solution.getText()+"' WHERE ID ="+ControllerLoged.arrayTask.get(ID).getID();
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
        stav.setText(done(ControllerLoged.arrayTask.get(ID).getStav()));
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
