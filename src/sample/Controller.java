package sample;


import com.mysql.jdbc.PreparedStatement;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class Controller {

    public TextField mail;
    public PasswordField pass;
    public Button log_butt;
    public Label wrong_cred;

    
    public void login(ActionEvent actionEvent) {

           try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        Connection pripojeni = null;
        try {
            pripojeni = DriverManager.getConnection("jdbc:mysql://89.203.248.248:3306/Tracked?user="+mail.getText()+"&password="+pass.getText()+"&useSSL=false");
        } catch (SQLException throwables) {
            wrong_cred.setVisible(true);
            throwables.printStackTrace();
        }
        if(pripojeni != null)change(actionEvent);

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
        okno.setTitle("Tracked - " + mail.getText());
        okno.show();
    }
}
