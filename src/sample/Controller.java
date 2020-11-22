package sample;


import com.mysql.jdbc.PreparedStatement;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Controller {

    public TextField mail;
    public PasswordField pass;
    public Button log_butt;


    public void login(ActionEvent actionEvent) {

           try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        Connection pripojeni = null;
        try {
            pripojeni = DriverManager.getConnection("jdbc:mysql://89.203.248.248:3306/Tracked?user=Karel@karel.cz&password=karelkarel&useSSL=false");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        PreparedStatement dotaz = null;
            try {
                dotaz = (PreparedStatement) pripojeni.prepareStatement("SELECT * FROM User");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        ResultSet vysledky = null;
            try {
                vysledky = dotaz.executeQuery();
                System.out.println(vysledky);
                vysledky.close();
                dotaz.close();
                pripojeni.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }



    }
    public void change(ActionEvent event){

        Parent LogPar = null;
        try {
            LogPar = FXMLLoader.load(getClass().getResource("loged.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene loged = new Scene(LogPar,700,500);
        Stage okno = (Stage)((Node)event.getSource()).getScene().getWindow();
        okno.setScene(loged);
        okno.show();
    }
}
