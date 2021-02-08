package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;


public class Controller {

    public TextField mail;
    public PasswordField pass;
    public Button log_butt;
    public Label wrong_cred;
    public static Connection pripojeni = null;
    public AnchorPane mainPane;
    static String userName;
    public Button closeButton;
    private String name = "Tracked";
    private String password = "Tracked-123";

    /**
     * Přihlášení
     * Metoda se přihlásí přes admin účet a poté ověří zda je uživate
     * @param actionEvent
     */
    public void login(ActionEvent actionEvent) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            pripojeni = DriverManager.getConnection("jdbc:mysql://89.203.248.248:3306/Tracked?user="+mail.getText()+"&password="+pass.getText()+"&useUnicode=true&characterEncoding=utf8&useSSL=false");
        } catch (SQLException throwables) {
            wrong_cred.setVisible(true);
            throwables.printStackTrace();
        }
        if(pripojeni != null){
            userName = mail.getText();
            change(actionEvent);
        }

    }

        public void change(ActionEvent event){

        Parent LogPar = null;
        try {
            LogPar = FXMLLoader.load(getClass().getResource("FXML/loged.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene loged = null;
        if (LogPar != null) {
            loged = new Scene(LogPar,700,500);
        }
        Stage okno = (Stage)((Node)event.getSource()).getScene().getWindow();
        okno.setScene(loged);
        okno.setTitle("Tracked - " + mail.getText());
        okno.show();
    }

    /**
     * Metoda vrací aktuální připojení.
     * @return připojení
     */
    public static Connection getConnection(){
        return pripojeni;
    }
    public static String getUserName(){return userName;}

    public void onEnter(ActionEvent ae){
        login(ae);
    }

    public void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
