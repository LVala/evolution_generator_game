package gui;

import evogen.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Arrays;
import java.util.List;

public class SimulationBox {
    public static final String fontName = "Tahoma";

    public final HBox simulationBox = new HBox();
    private final Chart chart;
    private final SimulationInfoGrid simulationInfoGrid;
    private final MapGrid mapGrid;
    private final AnimalInfoGrid animalInfoGrid;

    public SimulationBox(SimulationEngine simulationEngine) {

        this.chart = new Chart(simulationEngine.animals, simulationEngine.plants,
                simulationEngine.energy, simulationEngine.lifespan, simulationEngine.children);
        this.chart.createChart(simulationEngine.getEra());

        this.simulationInfoGrid = new SimulationInfoGrid(simulationEngine.animals, simulationEngine.plants,
                simulationEngine.energy, simulationEngine.lifespan, simulationEngine.children);
        this.simulationInfoGrid.createSimulationInfoGrid(simulationEngine.getEra());

        HBox buttons = createButtonsHBox();

        this.mapGrid = new MapGrid(simulationEngine.getMap());
        this.mapGrid.createMapGrid();

        // TODO to do zmodernizowania
        this.animalInfoGrid = new AnimalInfoGrid();
        this.animalInfoGrid.createAnimalInfo(new Animal(new Vector2d(1,1), 3, Genotype.generateRandomGenotype(),
                simulationEngine.getMap(), 0));


        VBox chartAndInfoBox = new VBox();
        chartAndInfoBox.setPadding(new Insets(10, 10, 10, 10));
        chartAndInfoBox.getChildren().addAll(this.chart.lineChart, this.simulationInfoGrid.simulationInfoGrid, buttons);

        VBox mapAndAnimalInfoBox = new VBox();
        mapAndAnimalInfoBox.getChildren().addAll(this.mapGrid.mapGrid, MapElement.createMapLegend(), this.animalInfoGrid.animalInfoGrid);

        this.simulationBox.getChildren().addAll(chartAndInfoBox, mapAndAnimalInfoBox);
    }

    public void reloadSimulationBox() {
        //TODO napisca ta metode
    }

    public HBox createButtonsHBox() {
        // BUTTONS

        Button stopStart = new Button("STOP");
        stopStart.setFont(Font.font(fontName, FontWeight.NORMAL, 22));
        stopStart.setPrefSize(200,45);
        stopStart.setOnAction(event -> {
            //TODO stop start simulation
        });
        HBox hbStopStart = new HBox(30);
        hbStopStart.setAlignment(Pos.CENTER);
        hbStopStart.getChildren().add(stopStart);

        Button toCsv = new Button("To CSV file");
        toCsv.setFont(Font.font(fontName, FontWeight.NORMAL, 22));
        toCsv.setPrefSize(200,45);
        toCsv.setOnAction(event -> {
            //TODO convert data to csv
        });
        HBox hbToCsv = new HBox(30);
        hbToCsv.setAlignment(Pos.CENTER);
        hbToCsv.getChildren().add(toCsv);

        HBox buttonHBox = new HBox(10);
        buttonHBox.getChildren().addAll(hbToCsv, hbStopStart);
        buttonHBox.setAlignment(Pos.CENTER);
        return buttonHBox;
    }
}
