package com.soundhive;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kong.unirest.Unirest;

/**
 * JavaFX App
 */
public class App extends Application {

    private Router router;



    @Override
    public void start(final Stage stage) {
        Unirest.config().defaultBaseUrl("http://localhost:3000/");

        final FXMLLoader loader = new FXMLLoader(this.getClass().getResource("MainView.fxml"));
        final Parent view;
        try {
            view = loader.load();
            stage.setScene(new Scene(view));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //stage.setResizable(false);
        stage.setTitle("SoundHive");

        stage.show();
    }



    public static void main(final String[] args) {
        launch(args);
    }


}