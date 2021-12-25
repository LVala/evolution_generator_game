package gui;

import evogen.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimulationBox {
    public static final String fontName = "Tahoma";

    private final SimulationEngine simulationEngine;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private Future<?> future;
    private final Stage parentWindow;
    public final HBox simulationBox = new HBox();
    private final Chart chart;
    private final SimulationInfoGrid simulationInfoGrid;
    private final MapGrid mapGrid;
    private final AnimalInfoGrid animalInfoGrid;

    private boolean isStopped = true;

    public SimulationBox(SimulationEngine simulationEngine, Stage parentWindow) {
        this.parentWindow = parentWindow;
        this.simulationEngine = simulationEngine;
        this.chart = new Chart(simulationEngine.animals, simulationEngine.plants,
                simulationEngine.energy, simulationEngine.lifespan, simulationEngine.children, simulationEngine.getMap().getMapName());

        this.simulationInfoGrid = new SimulationInfoGrid(simulationEngine.animals, simulationEngine.plants,
                simulationEngine.energy, simulationEngine.lifespan, simulationEngine.children);
        this.simulationInfoGrid.updateSimulationInfoGrid(simulationEngine.getEra(), simulationEngine.getMap().getMostCommonGenotypes());

        // left side of the simulation box
        HBox leftButtons = createLeftButtonsHBox();

        this.animalInfoGrid = new AnimalInfoGrid();

        this.mapGrid = new MapGrid(simulationEngine.getMap(), animalInfoGrid);
        this.mapGrid.updateMapGrid();

        // right side of the simulation box
        HBox rightButtons = createRightButtonHBox();

        VBox chartAndInfoBox = new VBox();
        chartAndInfoBox.setPadding(new Insets(10, 10, 10, 10));
        chartAndInfoBox.getChildren().addAll(this.chart.getChart(), this.simulationInfoGrid.simulationInfoGrid, leftButtons);

        VBox mapAndAnimalInfoBox = new VBox();
        mapAndAnimalInfoBox.getChildren().addAll(this.mapGrid.mapGrid, MapElement.createMapLegend(), animalInfoGrid.animalInfoGrid, rightButtons);

        this.simulationBox.getChildren().addAll(chartAndInfoBox, mapAndAnimalInfoBox);
    }

    public ScheduledExecutorService getExecutor() {
        return this.executor;
    }

    public void reloadSimulationBox() {
        this.chart.updateChart(simulationEngine.getEra());
        this.simulationInfoGrid.updateSimulationInfoGrid(simulationEngine.getEra(), simulationEngine.getMap().getMostCommonGenotypes());
        this.mapGrid.updateMapGrid();
        this.animalInfoGrid.updateAnimalInfo();
    }

    private HBox createLeftButtonsHBox() {
        Button stopStart = new Button("START");
        stopStart.setFont(Font.font(fontName, FontWeight.NORMAL, 22));
        stopStart.setPrefSize(200,45);
        HBox hbStopStart = new HBox(30);
        hbStopStart.setAlignment(Pos.CENTER);
        hbStopStart.getChildren().add(stopStart);

        stopStart.setOnAction(event -> {
            if (isStopped) {
                // using ScheduledExecutorService to run one "simulation era" periodically on new thread
                this.future = this.executor.scheduleAtFixedRate(this.simulationEngine, 0, this.simulationEngine.getDelay(), TimeUnit.MILLISECONDS);
                this.isStopped = false;
                stopStart.setText("STOP");
            }
            else {
                future.cancel(true);
                this.isStopped = true;
                stopStart.setText("START");
            }
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

    private HBox createRightButtonHBox() {
        Button showGeno = new Button("Show most common genotype");

        showGeno.setFont(Font.font(fontName, FontWeight.NORMAL, 22));
        showGeno.setPrefSize(400,45);
        HBox hbButton = new HBox(30);
        hbButton.setAlignment(Pos.CENTER);
        hbButton.getChildren().add(showGeno);

        showGeno.setOnAction(event -> {
            if (this.isStopped) {
                this.mapGrid.highlightMostCommonGenotype(simulationEngine.getMap().getMostCommonGenotypes());
            }
        });

        return hbButton;
    }

    public void magicSimPopup(int cloningsLeft) {
        // popup window when animals are magically cloned

        Popup popup = new Popup();
        String mapName = this.simulationEngine.getMap().getMapName();
        Label label = new Label(String.format("Animals on %s were magically cloned! Clonings left: %d", mapName, cloningsLeft));
        label.setFont(Font.font(fontName, FontWeight.NORMAL, 22));
        label.setStyle(" -fx-background-color: white; -fx-border-color: black");
        popup.getContent().add(label);
        popup.setAutoHide(true);
        popup.show(this.parentWindow);
    }

    private void writeToFileAsCSV() throws IOException {
        // searches for free file name
        File csvOutputFile = new File("simulation_data_1.csv");
        int j = 2;
        while (csvOutputFile.exists()) {
            csvOutputFile = new File(String.format("simulation_data_%d.csv", j));
            j++;
        }
        if (!csvOutputFile.createNewFile()) throw new IOException();

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(csvOutputFile)))) {
            writer.println("era,animalNumber,plantNumber,averageEnergy,averageLifespan,averageChildrenNumber");  // csv header

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

            // average values for the last csv row
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
