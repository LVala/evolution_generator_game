package gui;

import evogen.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.*;
import java.util.Arrays;
import java.util.List;


public class SimulationBox {
    public static final String fontName = "Tahoma";

    private final SimulationEngine simulationEngine;
    public final HBox simulationBox = new HBox();
    private final Chart chart;
    private final SimulationInfoGrid simulationInfoGrid;
    private final MapGrid mapGrid;
    private final AnimalInfoGrid animalInfoGrid;

    private final boolean isStopped = false;
    private Animal trackedAnimal; // TODO

    public SimulationBox(SimulationEngine simulationEngine) {

        this.simulationEngine = simulationEngine;
        this.chart = new Chart(simulationEngine.animals, simulationEngine.plants,
                simulationEngine.energy, simulationEngine.lifespan, simulationEngine.children, simulationEngine.getMap().getMapName());

        this.simulationInfoGrid = new SimulationInfoGrid(simulationEngine.animals, simulationEngine.plants,
                simulationEngine.energy, simulationEngine.lifespan, simulationEngine.children);
        this.simulationInfoGrid.createSimulationInfoGrid(simulationEngine.getEra(), simulationEngine.getMap().getMostCommonGenotype());

        HBox buttons = createButtonsHBox();

        this.animalInfoGrid = new AnimalInfoGrid();

        this.mapGrid = new MapGrid(simulationEngine.getMap(), this.animalInfoGrid);
        this.mapGrid.createMapGrid();

        VBox chartAndInfoBox = new VBox();
        chartAndInfoBox.setPadding(new Insets(10, 10, 10, 10));
        chartAndInfoBox.getChildren().addAll(this.chart.getChart(), this.simulationInfoGrid.simulationInfoGrid, buttons);

        VBox mapAndAnimalInfoBox = new VBox();
        mapAndAnimalInfoBox.getChildren().addAll(this.mapGrid.mapGrid, MapElement.createMapLegend(), this.animalInfoGrid.animalInfoGrid);

        this.simulationBox.getChildren().addAll(chartAndInfoBox, mapAndAnimalInfoBox);
    }

    public void reloadSimulationBox() {
        //TODO napisca ta metode
    }

    public HBox createButtonsHBox() {
        Button stopStart = new Button("STOP");
        stopStart.setFont(Font.font(fontName, FontWeight.NORMAL, 22));
        stopStart.setPrefSize(200,45);
        HBox hbStopStart = new HBox(30);
        hbStopStart.setAlignment(Pos.CENTER);
        hbStopStart.getChildren().add(stopStart);

        stopStart.setOnAction(event -> {
            //TODO stop start simulation
        });


        Button toCsv = new Button("To CSV file");
        toCsv.setFont(Font.font(fontName, FontWeight.NORMAL, 22));
        toCsv.setPrefSize(200,45);
        HBox hbToCsv = new HBox(30);
        hbToCsv.setAlignment(Pos.CENTER);
        hbToCsv.getChildren().add(toCsv);

        toCsv.setOnAction(event -> {
            try {
                if (this.isStopped) writeToFileAsCSV();
            } catch (IOException ex) {
                System.out.println("Unable to create simulation data file: " + ex);
            }
        });


        HBox buttonHBox = new HBox(10);
        buttonHBox.getChildren().addAll(hbToCsv, hbStopStart);
        buttonHBox.setAlignment(Pos.CENTER);
        return buttonHBox;
    }

    private void writeToFileAsCSV() throws IOException {
        File csvOutputFile = new File("simulation_data_1.csv");
        int j = 2;
        while (csvOutputFile.exists()) {
            csvOutputFile = new File(String.format("simulation_data_%d.csv", j));
            j++;
        }
        if (!csvOutputFile.createNewFile()) throw new IOException();

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(csvOutputFile)))) {
            writer.println("era,animalNumber,plantNumber,averageEnergy,averageLifespan,averageChildrenNumber");

            for (int i = 0; i <= simulationEngine.getEra(); i++) {
                writer.println(String.join(",", Arrays.stream(new Integer[]{
                        i,
                        simulationEngine.animals.get(i),
                        simulationEngine.plants.get(i),
                        simulationEngine.energy.get(i),
                        simulationEngine.lifespan.get(i),
                        simulationEngine.children.get(i)
                }).map(String::valueOf).toArray(String[]::new)));
            }

            writer.println(String.join(",", Arrays.stream(new Integer[]{
                    -1,
                    getAverage(simulationEngine.animals),
                    getAverage(simulationEngine.plants),
                    getAverage(simulationEngine.energy),
                    getAverage(simulationEngine.lifespan),
                    getAverage(simulationEngine.children),
            }).map(String::valueOf).toArray(String[]::new)));
        }
    }

    private int getAverage(List<Integer> data) {
        return (int) data.stream().filter((a) -> a != -1).mapToDouble(d -> d).average().orElse(0.0);
    }
}
