package gui;

import evogen.SimulationEngine;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

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

        SimulationBox foldedSimulationBox = new SimulationBox(this.foldedEngine);
        SimulationBox boundedSimulationBox = new SimulationBox(this.boundedEngine);

        this.foldedEngine.setSimulationGuiBox(foldedSimulationBox);
        this.boundedEngine.setSimulationGuiBox(boundedSimulationBox);

        HBox mainHBox = new HBox(10);
        mainHBox.getChildren().addAll(foldedSimulationBox.simulationBox, boundedSimulationBox.simulationBox);

        Scene scene = new Scene(mainHBox, 1500, 700);
        mainStage.setScene(scene);
        mainStage.show();
    }
}