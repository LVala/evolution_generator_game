package gui;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.List;

public class Chart {
    public final LineChart<Number, Number> lineChart;

    public Chart() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Era");
        this.lineChart = new LineChart<>(xAxis, yAxis);
    }

    public XYChart.Series<Number, Number> createSeries(int era, List<Integer> data) {
        // creates series for the chart

        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        for (int i = 0; i < era; i++) {
            series.getData().add(new XYChart.Data<>(i + 1, data.get(i)));
        }

        return series;
    }

    public void createChart(int era, List<Integer> animals,
                                   List<Integer> plants, List<Integer> energy, List<Integer> lifespan, List<Integer> children) {

        XYChart.Series<Number, Number> series1 = createSeries(era, animals);
        series1.setName("Number of animals");

        XYChart.Series<Number, Number> series2 = createSeries(era, plants);
        series2.setName("Number of plants");

        XYChart.Series<Number, Number> series3 = createSeries(era, energy);
        series3.setName("Average energy");

        XYChart.Series<Number, Number> series4 = createSeries(era, lifespan);
        series4.setName("Average lifespan");

        XYChart.Series<Number, Number> series5 = createSeries(era, children);
        series5.setName("Average number of children");

        this.lineChart.getData().add(series1);
        this.lineChart.getData().add(series2);
        this.lineChart.getData().add(series3);
        this.lineChart.getData().add(series4);
        this.lineChart.getData().add(series5);

        this.lineChart.setCreateSymbols(false);
    }
}
