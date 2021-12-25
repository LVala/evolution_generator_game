package gui;

import evogen.SimulationEngine;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimulationStage {
    private final SimulationEngine foldedEngine;
    private final SimulationEngine boundedEngine;

    public SimulationStage(SimulationEngine foldedEngine, SimulationEngine boundedEngine) {
        this.foldedEngine = foldedEngine;
        this.boundedEngine = boundedEngine;
    }

    public void showMainStage() {
        Stage mainStage = new Stage();
        mainStage.setTitle("Evolution Generator");

        SimulationBox foldedSimulationBox = new SimulationBox(this.foldedEngine, mainStage);
        SimulationBox boundedSimulationBox = new SimulationBox(this.boundedEngine, mainStage);

        this.foldedEngine.setSimulationGuiBox(foldedSimulationBox);
        this.boundedEngine.setSimulationGuiBox(boundedSimulationBox);

        HBox mainHBox = new HBox(10);
        mainHBox.getChildren().addAll(foldedSimulationBox.simulationBox, boundedSimulationBox.simulationBox);

        Scene scene = new Scene(mainHBox, 1500, 750);
        mainStage.setScene(scene);
        mainStage.show();

        mainStage.setOnCloseRequest(event -> {
            foldedSimulationBox.getExecutor().shutdown();
            boundedSimulationBox.getExecutor().shutdown();
        });
    }
}