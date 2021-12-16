package gui;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SettingsStage extends Application {

    public GridPane createInputGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));

        String fontName = "Tahoma";
        int fontSize = 16;

        // SCENE TITLE

        Text sceneTitle = new Text("Welcome to the Evolution Generator!\nEnter simulation parameters:");
        sceneTitle.setFont(Font.font(fontName, FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        // INPUT FIELDS

        Label widthLabel = new Label("Map width:");
        TextField widthTextField = new TextField("100");

        Label heightLabel = new Label("Map height:");
        TextField heightTextField = new TextField("100");

        Label startEnergyLabel = new Label("Animal start energy:");
        TextField startEnergyTextField = new TextField("100");

        Label moveEnergyLabel = new Label("Animal move energy:");
        TextField moveEnergyTextField = new TextField("15");

        Label plantEnergyLabel = new Label("Plant energy:");
        TextField plantEnergyTextField = new TextField("35");

        Label jungleRatioLabel = new Label("Jungle to steppe ratio (0-100%):");
        TextField jungleRatioTextField = new TextField("30");

        Label initialAnimalsLabel = new Label("Initial number of animals:");
        TextField initialAnimalsTextField = new TextField("10");

        Label[] labels = {widthLabel, heightLabel, startEnergyLabel, moveEnergyLabel, plantEnergyLabel,
                jungleRatioLabel, initialAnimalsLabel};
        TextField[] textFields = {widthTextField, heightTextField, startEnergyTextField, moveEnergyTextField,
                plantEnergyTextField, jungleRatioTextField, initialAnimalsTextField};

        for (int i = 0; i < 7; i++) {
            labels[i].setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
            grid.add(labels[i], 0, i+1);
            grid.add(textFields[i], 1, i+1);
        }

        // CHECKBOXES

        Label isMagicLabel = new Label("If simulation uses \"magic\" rule:");
        isMagicLabel.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        grid.add(isMagicLabel, 0, 8, 2, 1);
        GridPane.setHalignment(isMagicLabel, HPos.CENTER);

        Label foldedLabel = new Label("Folded map");
        isMagicLabel.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        Label boundedLabel = new Label("Bounded map");
        isMagicLabel.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));

        CheckBox foldedCheckBox = new CheckBox();
        CheckBox boundedCheckBox = new CheckBox();

        HBox foldedHBox = new HBox(10);
        HBox boundedHBox = new HBox(10);

        foldedHBox.getChildren().addAll(foldedLabel, foldedCheckBox);
        boundedHBox.getChildren().addAll(boundedLabel, boundedCheckBox);

        grid.add(foldedHBox, 0, 9);
        grid.add(boundedHBox, 1, 9);

        // BUTTON

        Button btn = new Button("Start the simulation");
        btn.setFont(Font.font(fontName, FontWeight.NORMAL, 22));
        btn.setPrefSize(250,60);

        btn.setOnAction(event -> {
            if (validate(widthTextField, "[0-9]+") && validate(heightTextField, "[0-9]+") &&
                    validate(startEnergyTextField, "[0-9]+") && validate(moveEnergyTextField, "[0-9]+") &&
                    validate(plantEnergyTextField, "[0-9]+") && validate(jungleRatioTextField, "[0-9][0-9]?|100") &&
                    validate(initialAnimalsTextField, "[0-9]+")) {

                int width = Integer.parseInt(widthTextField.getText());
                int height = Integer.parseInt(heightTextField.getText());
                int startEnergy = Integer.parseInt(startEnergyTextField.getText());
                int moveEnergy = Integer.parseInt(moveEnergyTextField.getText());
                int plantEnergy = Integer.parseInt(plantEnergyTextField.getText());
                double jungleRatio = (double) Integer.parseInt(jungleRatioTextField.getText())/100;
                int initialAnimals = Integer.parseInt(initialAnimalsTextField.getText());
                boolean isMagicFolded = foldedCheckBox.isSelected();
                boolean isMagicBounded = boundedCheckBox.isSelected();

                SimulationStage simulationStage = new SimulationStage();
                //width, height, startEnergy, moveEnergy, plantEnergy, jungleRatio, initialAnimals, isMagicFolded, isMagicBounded
                simulationStage.showMainStage();
            }
        });

        HBox hbBtn = new HBox(20);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 0, 11, 2, 1);

        return grid;
    }

    public boolean validate(TextField textField, String regex) {
        // checks if TextField input is valid based on regex

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
