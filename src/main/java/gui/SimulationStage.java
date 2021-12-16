package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class SimulationStage {
    public static final String fontName = "Tahoma";

    public HBox createButtonsHBox(GridPane grid) {
        // BUTTONS

        Button stopStart = new Button("Stop the simulation");
        stopStart.setFont(Font.font(fontName, FontWeight.NORMAL, 22));
        stopStart.setPrefSize(250,45);
        stopStart.setOnAction(event -> {
            //TODO stop start simulation
        });
        HBox hbStopStart = new HBox(30);
        hbStopStart.setAlignment(Pos.CENTER);
        hbStopStart.getChildren().add(stopStart);
        grid.add(hbStopStart, 1, 7, 1, 1);

        Button toCsv = new Button("To CSV file");
        toCsv.setFont(Font.font(fontName, FontWeight.NORMAL, 22));
        toCsv.setPrefSize(250,45);
        toCsv.setOnAction(event -> {
            //TODO convert data to csv
        });
        HBox hbToCsv = new HBox(30);
        hbToCsv.setAlignment(Pos.CENTER);
        hbToCsv.getChildren().add(toCsv);
        grid.add(hbToCsv, 0, 7, 1, 1);

        HBox buttonHBox = new HBox();
        buttonHBox.getChildren().addAll(hbToCsv, hbStopStart);
        return buttonHBox;
    }

    public HBox createSimulationHBox() {
        //TODO laczy wszystkie skladniki w hbox z info, wykresem i mapa

        return new HBox();
    }

    public void reloadStage() {
        // TODO reload stage after call from engine
    }

    public void showMainStage(/*IEngine foldedSimulationEngine, IEngine boundedSimulationEngine*/) {
        Stage mainStage = new Stage();
        mainStage.setTitle("Evolution Generator");

        int era = 9;
        List<Integer> animals = Arrays.asList(new Integer[]{5,7,4,6,5,7,6,5,4});
        List<Integer> plants = Arrays.asList(new Integer[]{2,3,4,2,1,3,4,2,3});
        List<Integer> energy = Arrays.asList(new Integer[]{30,35,34,36,37,40,39,37,38});
        List<Integer> lifespan = Arrays.asList(new Integer[]{5,3,6,7,5,6,4,2,4});
        List<Integer> children = Arrays.asList(new Integer[]{1,1,1,0,2,1,2,1,1});

        HBox mainHBox = new HBox(8);
        HBox leftSim = new HBox(8);
        HBox rightSim = new HBox(8);
        mainHBox.getChildren().addAll(leftSim, rightSim);




        Scene scene = new Scene(mainHBox, 2000, 700);
        mainStage.setScene(scene);
        mainStage.show();
    }
}




//    public SimulationStage(int width, int height, int startEnergy, int moveEnergy, int plantEnergy,
//                           double jungleRatio, int initialAnimals, boolean isMagicFolded, boolean isMagicBounded) {
//        this.width = width;
//        this.height = height;
//        this.startEnergy = startEnergy;
//        this.moveEnergy = moveEnergy;
//        this.plantEnergy = plantEnergy;
//        this.jungleRatio = jungleRatio;
//        this.initialAnimals = initialAnimals;
//        this.isMagicFolded = isMagicFolded;
//        this.isMagicBounded = isMagicBounded;
//    }