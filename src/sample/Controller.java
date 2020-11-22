package sample;


import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.*;

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
            }

    }
}
