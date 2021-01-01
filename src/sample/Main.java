package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXML/sample.fxml"));
        Scene scena = new Scene(root, 700, 500);
        scena.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scena);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../resources/Logo.png")));

    }


    public static void main(String[] args) {
        launch(args);
    }
}
