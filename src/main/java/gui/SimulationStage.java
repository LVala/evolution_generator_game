package gui;

import evogen.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class App{

    public GridPane createInterfaceGrid(int era, List<Integer> animals, List<Integer> plants, List<Integer> energy,
                                        List<Integer> lifespan, List<Integer> children) {

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(20);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 10, 25, 25));

        LineChart<Number, Number> lineChart = Chart.createChart(era, animals, plants, energy, lifespan, children);
        grid.add(lineChart, 0, 0, 2, 1);

        // DATA

        String fontName = "Tahoma";
        int fontSize = 16;

        Label curEra = new Label("Era:");
        curEra.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        grid.add(curEra, 0, 1);
        Label curEraNumber = new Label(String.format("%d", era));
        curEraNumber.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        grid.add(curEraNumber, 1, 1);

        Label curAnimals = new Label("Number of animals:");
        curAnimals.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        grid.add(curAnimals, 0, 2);
        Label curAnimalsNumber = new Label(String.format("%d", animals.get(animals.size() - 1)));
        curAnimalsNumber.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        grid.add(curAnimalsNumber, 1, 2);

        Label curPlants = new Label("Number of plants:");
        curPlants.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        grid.add(curPlants, 0, 3);
        Label curPlantsNumber = new Label(String.format("%d", plants.get(animals.size() - 1)));
        curPlantsNumber.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        grid.add(curPlantsNumber, 1, 3);

        Label curEnergy = new Label("Average energy level:");
        curEnergy.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        grid.add(curEnergy, 0, 4);
        Label curEnergyNumber = new Label(String.format("%d", energy.get(animals.size() - 1)));
        curEnergyNumber.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        grid.add(curEnergyNumber, 1, 4);

        Label curLifespan = new Label("Average animal lifespan:");
        curLifespan.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        grid.add(curLifespan, 0, 5);
        Label curLifespanNumber = new Label(String.format("%d", lifespan.get(animals.size() - 1)));
        curLifespanNumber.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        grid.add(curLifespanNumber, 1, 5);

        Label curChildren = new Label("Average number of children:");
        curChildren.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        grid.add(curChildren, 0, 6);
        Label curChildrenNumber = new Label(String.format("%d", children.get(animals.size() - 1)));
        curChildrenNumber.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        grid.add(curChildrenNumber, 1, 6);

        // BUTTONS
        Button stopStart = new Button("Stop the simulation");
        stopStart.setFont(Font.font(fontName, FontWeight.NORMAL, 22));
        stopStart.setPrefSize(250,60);
        stopStart.setOnAction(event -> {
            //TODO stop start simulation
        });
        HBox hbStopStart = new HBox(30);
        hbStopStart.setAlignment(Pos.CENTER);
        hbStopStart.getChildren().add(stopStart);
        grid.add(hbStopStart, 1, 7, 1, 1);

        Button toCsv = new Button("To CSV file");
        toCsv.setFont(Font.font(fontName, FontWeight.NORMAL, 22));
        toCsv.setPrefSize(250,60);
        toCsv.setOnAction(event -> {
            //TODO convert data to csv
        });
        HBox hbToCsv = new HBox(30);
        hbToCsv.setAlignment(Pos.CENTER);
        hbToCsv.getChildren().add(toCsv);
        grid.add(hbToCsv, 0, 7, 1, 1);

        return grid;
    }

    public GridPane createMapGrid(AbstractWorldMap map) {
        //TODO mapa

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new Insets(25, 25, 25, 10));

       int gridElemHeight = 400/map.height;
       int gridElemWidth = 400/map.width;

        for (int i = 0; i < map.height; i++) {
            for (int j = 0; j < map.width; j++) {
                Vector2d vec = new Vector2d(j, i);
                StackPane stack;

                if (map.isOccupiedByAnimal(vec)) {
                    Animal animal = map.getStrongestAnimalsAt(vec).get(0);
                    stack = GuiMapElement.getImage(gridElemWidth, gridElemHeight, animal, map.startEnergy, map.isInJungle(vec));
                }
                else if (map.isOccupiedByPlant(vec)) {
                    Plant plant = map.getPlantAt(vec);
                    stack = GuiMapElement.getImage(gridElemWidth, gridElemHeight, plant, map.startEnergy, map.isInJungle(vec));
                }
                else {
                    stack = GuiMapElement.getImage(gridElemWidth, gridElemHeight, null, map.startEnergy, map.isInJungle(vec));
                }
                grid.add(stack, j, i);
            }
        }

        return grid;
    }

    public void showMainStage() {
        Stage mainStage = new Stage();
        mainStage.setTitle("Evolution Generator");

        HBox hbox = new HBox(8);
        HBox leftSim = new HBox(8);
        HBox rightSim = new HBox(8);

        int era = 9;
        List<Integer> animals = Arrays.asList(new Integer[]{5,7,4,6,5,7,6,5,4});
        List<Integer> plants = Arrays.asList(new Integer[]{2,3,4,2,1,3,4,2,3});
        List<Integer> energy = Arrays.asList(new Integer[]{30,35,34,36,37,40,39,37,38});
        List<Integer> lifespan = Arrays.asList(new Integer[]{5,3,6,7,5,6,4,2,4});
        List<Integer> children = Arrays.asList(new Integer[]{1,1,1,0,2,1,2,1,1});

        leftSim.getChildren().add(createInterfaceGrid(era, animals, plants, energy, lifespan, children));
        leftSim.getChildren().add(createMapGrid(new BoundedMap(40,20,10,10,10,0.3, 10)));
//        rightSim.getChildren().add(createInterfaceGrid());


        hbox.getChildren().addAll(leftSim, rightSim);

        Scene scene = new Scene(hbox, 2000, 700);
        mainStage.setScene(scene);
        mainStage.show();
    }
}
