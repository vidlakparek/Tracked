package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerLoged {


    @FXML
    private Label idMail;

    @FXML
    private TableColumn<?, ?> idUkol;

    @FXML
    private TableColumn<?, ?> ukol;

    public void initializate(){
        idMail.setText(new Controller().getMail());
    }

    public void change(ActionEvent event){

        Parent Par = null;
        try {
            Par = FXMLLoader.load(getClass().getResource("sample.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene sample = new Scene(Par,700,500);
        Stage okno = (Stage)((Node)event.getSource()).getScene().getWindow();
        okno.setScene(sample);
        okno.show();
    }
}