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
    private Chart chart;
    private SimulationInfoGrid simulationInfoGrid;
    private MapGrid mapGrid;
    private AnimalInfoGrid animalInfoGrid;

    public SimulationBox(IEngine simulationEngine) {

        //TODO do usuniecia, wyciagenia danych z engine
        int era = 9;
        List<Integer> animals = Arrays.asList(new Integer[]{5,7,4,6,5,7,6,5,4});
        List<Integer> plants = Arrays.asList(new Integer[]{2,3,4,2,1,3,4,2,3});
        List<Integer> energy = Arrays.asList(new Integer[]{30,35,34,36,37,40,39,37,38});
        List<Integer> lifespan = Arrays.asList(new Integer[]{5,3,6,7,5,6,4,2,4});
        List<Integer> children = Arrays.asList(new Integer[]{1,1,1,0,2,1,2,1,1});

        this.chart = new Chart(animals, plants, energy, lifespan, children);
        this.chart.createChart(era);

        this.simulationInfoGrid = new SimulationInfoGrid(animals, plants, energy, lifespan, children);
        this.simulationInfoGrid.createSimulationInfoGrid(era);

        HBox buttons = createButtonsHBox();

        //TODO do usuniecia
        AbstractWorldMap map = new FoldedMap(70,70,10,10,10,0.3,10);
        this.mapGrid = new MapGrid(map);
        this.mapGrid.createMapGrid();

        this.animalInfoGrid = new AnimalInfoGrid();
        this.animalInfoGrid.createAnimalInfo(new Animal(new Vector2d(1,1), 3, Genotype.generateRandomGenotype(),
                map));


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
