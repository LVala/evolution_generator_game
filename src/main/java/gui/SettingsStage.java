package gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class SettingsStage extends Application {

    public GridPane createInputGrid() {
        // creates GridPane for the input field

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // SCENE TITLE

        Text sceneTitle = new Text("Welcome to the Evolution Generator!\nEnter simulation parameters:");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        String fontName = "Tahoma";
        int fontSize = 16;

        // INPUT FIELDS

        Label width = new Label("Map width:");
        width.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        grid.add(width, 0, 1);
        TextField widthTextField = new TextField("100");
        grid.add(widthTextField, 1, 1);

        Label height = new Label("Map height:");
        height.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        grid.add(height, 0, 2);
        TextField heightTextField = new TextField("100");
        grid.add(heightTextField, 1, 2);

        Label startEnergy = new Label("Animal start energy:");
        startEnergy.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        grid.add(startEnergy, 0, 3);
        TextField startEnergyTextField = new TextField("100");
        grid.add(startEnergyTextField, 1, 3);

        Label moveEnergy = new Label("Animal move energy:");
        moveEnergy.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        grid.add(moveEnergy, 0, 4);
        TextField moveEnergyTextField = new TextField("15");
        grid.add(moveEnergyTextField, 1, 4);

        Label plantEnergy = new Label("Plant energy:");
        plantEnergy.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        grid.add(plantEnergy, 0, 5);
        TextField plantEnergyTextField = new TextField("35");
        grid.add(plantEnergyTextField, 1, 5);

        Label jungleRatio = new Label("Jungle to steppe ratio (0-100%):");
        jungleRatio.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        grid.add(jungleRatio, 0, 6);
        TextField jungleRatioTextField = new TextField("30");
        grid.add(jungleRatioTextField, 1, 6);

        Label initialAnimals = new Label("Initial number of animals:");
        initialAnimals.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        grid.add(initialAnimals, 0, 7);
        TextField initialAnimalsTextField = new TextField("10");
        grid.add(initialAnimalsTextField, 1, 7);

        // BUTTON

        Button btn = new Button("Start the simulation");
        btn.setFont(Font.font(fontName, FontWeight.NORMAL, 22));
        btn.setPrefSize(250,60);
        btn.setOnAction(new EventHandler<>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                if (validate(widthTextField, "[0-9]+") && validate(heightTextField, "[0-9]+") && validate(startEnergyTextField, "[0-9]+") &&
                        validate(moveEnergyTextField, "[0-9]+") && validate(plantEnergyTextField, "[0-9]+") &&
                        validate(jungleRatioTextField, "[0-9][0-9]?|100") && validate(initialAnimalsTextField, "[0-9]+")) {
                    //TODO start simulation
                    App.main(new String[] {"a"});
                }
            }
        });

        HBox hbBtn = new HBox(20);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 0, 9, 2, 1);

        return grid;
    }

    public boolean validate(TextField textField, String regex) {

        if (!textField.getText().matches(regex)){
            textField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            textField.setText("invalid input");
            return false;
        }
        else {
            textField.setStyle(null);
            return true;
        }
    }

    public  void start(Stage primaryStage) {
        primaryStage.setTitle("Evolution Generator - Initial Settings");

        GridPane grid = createInputGrid();
        Scene scene = new Scene(grid, 500, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
