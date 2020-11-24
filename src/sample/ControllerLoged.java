package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerLoged {

    public TableColumn<?, ?> idUkol;
    public TableColumn<?, ?> ukol;



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
        }

    public void add_task(ActionEvent event) {
        /* Vyskočení dialogového okna, do kterého uživatel zadá aparametery pro vytvřoení nového tasku*/
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

