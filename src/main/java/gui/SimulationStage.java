package gui;

import evogen.IEngine;
import evogen.NormalSimulationEngine;
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

    public void reloadStage() {
        // TODO reload stage after call from engine
    }

    public void showMainStage(/*IEngine foldedSimulationEngine, IEngine boundedSimulationEngine*/) {
        Stage mainStage = new Stage();
        mainStage.setTitle("Evolution Generator");

        //TODO do usniecia
        IEngine engine = new NormalSimulationEngine(20,20,10,10,10,0.3,10);

        SimulationBox foldedSimulationBox = new SimulationBox(engine);
        SimulationBox boundedSimulationBox = new SimulationBox(engine);

        HBox mainHBox = new HBox();
        mainHBox.getChildren().addAll(foldedSimulationBox.simulationBox, boundedSimulationBox.simulationBox);

        Scene scene = new Scene(mainHBox, 1500, 700);
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