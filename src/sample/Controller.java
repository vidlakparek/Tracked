package sample;


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


public class Controller {

    public TextField mail;
    public PasswordField pass;
    public Button log_butt;


    public void login(ActionEvent actionEvent) {

           /* try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection pripojeni = DriverManager.getConnection();
            PreparedStatement dotaz = null;
            try {
                dotaz = pripojeni.prepareStatement("SELECT * FROM ");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                ResultSet vysledky = dotaz.executeQuery();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }*/


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
