package gui;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.List;

public class Chart {
    private final LineChart<Number, Number> lineChart;

    private final List<Integer> animals;
    private final List<Integer> plants;
    private final List<Integer> energy;
    private final List<Integer> lifespan;
    private final List<Integer> children;

    private final XYChart.Series<Number, Number> animalSeries = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> plantsSeries = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> energySeries = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> lifespanSeries = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> childrenSeries = new XYChart.Series<>();

    public Chart(List<Integer> animals, List<Integer> plants, List<Integer> energy,
                 List<Integer> lifespan, List<Integer> children, String chartName) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Era");
        this.lineChart = new LineChart<>(xAxis, yAxis);
        this.lineChart.setTitle(chartName);

        this.animals = animals;
        this.plants = plants;
        this.energy = energy;
        this.lifespan = lifespan;
        this.children = children;

        this.animalSeries.setName("Number of animals");
        this.plantsSeries.setName("Number of plants");
        this.energySeries.setName("Average energy");
        this.lifespanSeries.setName("Average lifespan");
        this.childrenSeries.setName("Average number of children");

        this.updateChart(0);

        this.lineChart.getData().add(animalSeries);
        this.lineChart.getData().add(plantsSeries);
        this.lineChart.getData().add(energySeries);
        this.lineChart.getData().add(lifespanSeries);
        this.lineChart.getData().add(childrenSeries);

        this.lineChart.setCreateSymbols(false);
    }

    public LineChart<Number, Number> getChart() {
        return this.lineChart;
    }

    public void updateChart(int era) {
        this.animalSeries.getData().add(new XYChart.Data<>(era, animals.get(era)));
        this.plantsSeries.getData().add(new XYChart.Data<>(era, plants.get(era)));
        this.energySeries.getData().add(new XYChart.Data<>(era, energy.get(era)));
        this.lifespanSeries.getData().add(new XYChart.Data<>(era, lifespan.get(era)));
        this.childrenSeries.getData().add(new XYChart.Data<>(era, children.get(era)));
    }
}
