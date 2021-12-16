package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AnimalInfoGrid {
    //TODO to zrobic
    public static final String fontName = "Tahoma";
    public static final int fontSize = 16;

    public void createAnimalInfo(GridPane grid) {

        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(20);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 25, 25, 25));

        //TODO info o zaznaczonym zwierzu
        Label descendants= new Label("Number of all descendants:");
        descendants.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        grid.add(descendants, 0, 1);
        Label descendantsCount = new Label("Number of all descendands:");
        grid.add(descendantsCount, 1, 1);

        Label heightLabel = new Label("Map height:");
        heightLabel.setFont(Font.font(fontName, FontWeight.NORMAL, fontSize));
        grid.add(heightLabel, 0, 2);
        TextField heightTextField = new TextField("100");
        grid.add(heightTextField, 1, 2);
    }
}
