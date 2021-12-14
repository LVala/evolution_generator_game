package gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    public void main(String[] args) {
        try {
            Application.launch(App.class, args);
        } catch (IllegalArgumentException ex) {
            //TODO łapanie wyjątków
        }
    }


    public void start(Stage primaryStage) {
        primaryStage.show();
    }
}
