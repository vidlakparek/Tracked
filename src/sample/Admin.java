package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Admin {
    public MenuBar menuBar;
    public Button exitButton;
    public Label timeLabel;

    DateTimeFormatter formatterLC = DateTimeFormatter.ofPattern("dd. MMMM HH:mm");

    public void initialize(){
        initClock();
    }

    public void initClock() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            timeLabel.setText(LocalDateTime.now().format(formatterLC));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    public void close() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }
}
