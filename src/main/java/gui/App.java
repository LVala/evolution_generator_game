package gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App extends Application {

    public XYChart.Series createSeries(int era, List<Integer> data) {
        XYChart.Series series = new XYChart.Series();

        for (int i = 0; i < era; i++) {
            series.getData().add(new XYChart.Data(i+1, data.get(i)));
        }

        return series;
    }

    public LineChart<Number, Number> createChart(int era, List<Integer> animals, List<Integer> plants, List<Integer> energy,
                                                 List<Integer> lifespan, List<Integer> children) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Era");
        LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

        XYChart.Series series1 = createSeries(era, animals);
        series1.setName("Number of animals");

        XYChart.Series series2 = createSeries(era, plants);
        series2.setName("Number of plants");

        XYChart.Series series3 = createSeries(era, energy);
        series3.setName("Average energy");

        XYChart.Series series4 = createSeries(era, lifespan);
        series4.setName("Average lifespan");

        XYChart.Series series5 = createSeries(era, children);
        series5.setName("Average number of children");

        lineChart.getData().addAll(series1, series2, series3, series4, series5);
        lineChart.setCreateSymbols(false);

        return lineChart;
    }

    public GridPane createInterfaceGrid() {

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(20);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
//        grid.setGridLinesVisible(true);

        //######################
        int era = 9;
        List<Integer> animals = Arrays.asList(new Integer[]{5,7,4,6,5,7,6,5,4});
        List<Integer> plants = Arrays.asList(new Integer[]{2,3,4,2,1,3,4,2,3});
        List<Integer> energy = Arrays.asList(new Integer[]{30,35,34,36,37,40,39,37,38});
        List<Integer> lifespan = Arrays.asList(new Integer[]{5,3,6,7,5,6,4,2,4});
        List<Integer> children = Arrays.asList(new Integer[]{1,1,1,0,2,1,2,1,1});
        //#####################

        LineChart<Number, Number> lineChart = createChart(era, animals, plants, energy, lifespan, children);
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

        // BUTTON
        Button btn = new Button("Stop the simulation");
        btn.setFont(Font.font(fontName, FontWeight.NORMAL, 22));
        btn.setPrefSize(250,60);
        btn.setOnAction(new EventHandler<>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                //TODO stop simulation
            }
        });
        HBox hbBtn = new HBox(30);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 0, 7, 2, 1);

        return grid;
    }

    public GridPane createMapGrid() {
        //TODO mapa
        return new GridPane();
    }

    public  void start(Stage primaryStage) {
        primaryStage.setTitle("Evolution Generator");

        HBox hbox = new HBox(8);
        HBox leftSim = new HBox(8);
        HBox rightSim = new HBox(8);

        leftSim.getChildren().add(createInterfaceGrid());
//        rightSim.getChildren().add(createInterfaceGrid());


        hbox.getChildren().addAll(leftSim, rightSim);

        Scene scene = new Scene(hbox, 2000, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
