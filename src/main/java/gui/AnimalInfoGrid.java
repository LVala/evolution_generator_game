package gui;

import evogen.Animal;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AnimalInfoGrid {
    //TODO to zrobic
    public static final String fontName = "Tahoma";
    public static final int fontSize = 16;

    public final GridPane animalInfoGrid = new GridPane();

    public AnimalInfoGrid() {
        this.animalInfoGrid.setAlignment(Pos.TOP_CENTER);
        this.animalInfoGrid.setHgap(20);
        this.animalInfoGrid.setVgap(20);
        this.animalInfoGrid.setPadding(new Insets(10, 25, 25, 10));

        Label mainLabel = new Label("Animal Info");
        mainLabel.setFont(Font.font(fontName, FontWeight.NORMAL, 20));
        this.animalInfoGrid.add(mainLabel, 0, 0, 2, 1);
        GridPane.setHalignment(mainLabel, HPos.CENTER);

        Label genotypeLabel = new Label("Genotype:");
        genotypeLabel.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        this.animalInfoGrid.add(genotypeLabel, 0, 1);

        Label childrenLabel = new Label("Number of children:");
        childrenLabel.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        this.animalInfoGrid.add(childrenLabel, 0, 2);

        Label descendantsLabel = new Label("Number of descendants:");
        descendantsLabel.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        this.animalInfoGrid.add(descendantsLabel, 0, 3);

        Label deathEraLabel = new Label("Era of animal death:");
        deathEraLabel.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        this.animalInfoGrid.add(deathEraLabel, 0, 4);
    }

    public void createAnimalInfo(Animal animal) {
        //TODO info o zaznaczonym zwierzu

        Label genotypeValue = new Label(animal.getGenotype().toString());
        genotypeValue.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        this.animalInfoGrid.add(genotypeValue, 1,1);

        Label childrenValue = new Label(Integer.toString(animal.getTrackedChildrenNumber()));
        childrenValue.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        this.animalInfoGrid.add(childrenValue, 1,2);

        Label descendantsValue = new Label(Integer.toString(animal.getTrackedDescendantsNumber()));
        descendantsValue.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        this.animalInfoGrid.add(descendantsValue, 1,3);

        Label deathEraValue = new Label(Integer.toString(animal.getDeathEra()));
        deathEraValue.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        this.animalInfoGrid.add(deathEraValue, 1,4);
    }
}
