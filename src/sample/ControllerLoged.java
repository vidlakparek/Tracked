package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerLoged {


    public Pane task;
    public Label name;
    int color;

    //@Override
    public void initialize(ActionEvent actionEvent){
        name.setText(Task.name);
        task.setStyle("-fx-background-color: #"+color+"");

    }
    public void log_out(ActionEvent event) {
        try {
            Controller.pripojeni.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

            Parent LogPar = null;
            try {
                LogPar = FXMLLoader.load(getClass().getResource("sample.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene loged = new Scene(LogPar,700,500);
            Stage okno = (Stage)((Node)event.getSource()).getScene().getWindow();
            okno.setScene(loged);
            okno.setTitle("Tracked - přihlašování");
            okno.show();
            Task.addTask();
            Task.getNameFromArray(0);
        }

    public void add_task(ActionEvent event) throws IOException {
        CreateTask.create();
    }

    public void refresh(ActionEvent event) {
        /*Aktualizace seznamu tasks, stejně jako při stisknutí klávesy F5*/
    }

    public void clear_done(ActionEvent event) {
        /* Skryje tasks, které jsou již dokončeny.*/
    }

    public void sort(ActionEvent event) {
        /*Seřadí tasks podle toho, která možnost byla zvolena.*/
    }
}

