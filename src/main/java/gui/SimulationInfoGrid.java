package gui;

import evogen.Genotype;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

public class SimulationInfoGrid {
    public static final String fontName = "Tahoma";
    public static final int fontSize = 16;

    public final GridPane simulationInfoGrid = new GridPane();

    private final List<Integer> animals;
    private final List<Integer> plants;
    private final List<Integer> energy;
    private final List<Integer> lifespan;
    private final List<Integer> children;

    public SimulationInfoGrid(List<Integer> animals, List<Integer> plants, List<Integer> energy,
                              List<Integer> lifespan, List<Integer> children) {
        this.simulationInfoGrid.setAlignment(Pos.TOP_CENTER);
        this.simulationInfoGrid.setHgap(20);
        this.simulationInfoGrid.setVgap(10);
        this.simulationInfoGrid.setPadding(new Insets(25, 10, 25, 25));

        this.animals = animals;
        this.plants = plants;
        this.energy = energy;
        this.lifespan = lifespan;
        this.children = children;

        Label curEraLabel = new Label("Era:");
        Label curAnimalsLabel = new Label("Number of animals:");
        Label curPlantsLabel = new Label("Number of plants:");
        Label curEnergyLabel = new Label("Average energy level:");
        Label curLifespanLabel = new Label("Average animal lifespan:");
        Label curChildrenLabel = new Label("Average number of children:");
        Label curMostCommonGenotype = new Label("Most common genotypes: ");

        Label[] labels = {curEraLabel, curAnimalsLabel, curPlantsLabel, curEnergyLabel, curLifespanLabel, curChildrenLabel};

        for (int i = 0; i < 6; i++) {
            labels[i].setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
            this.simulationInfoGrid.add(labels[i], 0, i);
        }
        curMostCommonGenotype.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        this.simulationInfoGrid.add(curMostCommonGenotype, 0, 6, 2, 1);
    }

    public void updateSimulationInfoGrid(int era, List<Genotype> mostCommonGenotypes) {

        this.simulationInfoGrid.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 1);

        Label curEraTextValue = new Label(String.format("%d", era));
        Label curAnimalsTextValue = new Label(String.format("%d", animals.get(animals.size() - 1)));
        Label curPlantsTextValue = new Label(String.format("%d", plants.get(animals.size() - 1)));

        Label curEnergyTextValue;
        if (energy.get(animals.size() - 1) == -1) curEnergyTextValue = new Label("N/A");
        else curEnergyTextValue = new Label(String.format("%d", energy.get(animals.size() - 1)));

        Label curLifespanTextValue;
        if (lifespan.get(animals.size() - 1) == -1) curLifespanTextValue = new Label("N/A");
        else curLifespanTextValue = new Label(String.format("%d", lifespan.get(animals.size() - 1)));

        Label curChildrenValue;
        if (children.get(animals.size() - 1) == -1) curChildrenValue = new Label("N/A");
        else curChildrenValue = new Label(String.format("%d", children.get(animals.size() - 1)));

        Label[] values = {curEraTextValue, curAnimalsTextValue, curPlantsTextValue, curEnergyTextValue,
                curLifespanTextValue, curChildrenValue};

        for (int i = 0; i < 6; i++) {
            values[i].setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
            this.simulationInfoGrid.add(values[i], 1, i);
        }

        VBox genotypesBox = new VBox();
        for (Genotype genotype : mostCommonGenotypes) {
            genotypesBox.getChildren().add(new Label(genotype.toString()));
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(genotypesBox);
        scrollPane.setMaxHeight(200);

        this.simulationInfoGrid.add(scrollPane, 0, 7, 2, 1);
    }
}
