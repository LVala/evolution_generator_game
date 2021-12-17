package gui;

import evogen.Animal;
import evogen.IMapObject;
import evogen.Plant;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;

import java.awt.*;

public class MapElement {

    public static StackPane getImage(int width, int height, IMapObject object, int maxEnergy, boolean isJungle) {
        //TODO optymalizacja
        StackPane stack = new StackPane();
        stack.setBorder(new Border(new BorderStroke(Color.web("0x000000"),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0.5))));

        Rectangle rec = new Rectangle();
        rec.setHeight(height);
        rec.setWidth(width);

        if (object instanceof Plant) {
            rec.setFill(Color.web("0x306e26"));
            stack.getChildren().add(rec);
            return stack;
        }

        if (isJungle) {
            rec.setFill(Color.web("0x88a464"));
        }
        else {
            rec.setFill(Color.web("0xd8bca4"));
        }

        stack.getChildren().add(rec);
        if (object == null) return stack;

        Animal animal = (Animal) object;
        double energyRatio = (double) animal.getEnergy()/maxEnergy;
        String color;

        if (energyRatio < 0.2) color = "0xEDBEBF";
        else if (energyRatio < 0.4) color = "0xED9597";
        else if (energyRatio < 0.6) color = "0xEB676A";
        else if (energyRatio < 0.8) color = "0xE84145";
        else color = "0xE6050A";

        Circle circle = new Circle();
        circle.setRadius(Math.min(height, width) * 0.3);
        circle.setFill(Color.web(color));
        circle.setStroke(Color.web(color));
        stack.getChildren().add(circle);

        return stack;
    }

    public static GridPane createMapLegend() {
        GridPane legendGrid = new GridPane();
        legendGrid.setHgap(10);
        legendGrid.setAlignment(Pos.CENTER);

        Rectangle plantRec = new Rectangle();
        plantRec.setHeight(10);
        plantRec.setWidth(12);
        plantRec.setFill(Color.web("0x306e26"));
        Label plantLabel = new Label("-  plant");
        legendGrid.add(plantRec, 0, 0);
        legendGrid.add(plantLabel, 1, 0);

        Rectangle jungleRec = new Rectangle();
        jungleRec.setHeight(10);
        jungleRec.setWidth(10);
        jungleRec.setFill(Color.web("0x88a464"));
        Label jungleLabel = new Label("-  jungle");
        legendGrid.add(jungleRec, 2, 0);
        legendGrid.add(jungleLabel, 3, 0);

        Rectangle steppeRec = new Rectangle();
        steppeRec.setHeight(10);
        steppeRec.setWidth(10);
        steppeRec.setFill(Color.web("0xd8bca4"));
        Label steppeLabel = new Label("-  steppe");
        legendGrid.add(steppeRec, 4, 0);
        legendGrid.add(steppeLabel, 5, 0);

        Circle animalCircle = new Circle();
        animalCircle.setRadius(5);
        animalCircle.setFill(Color.web("0xE6050A"));
        animalCircle.setStroke(Color.web("0xE6050A"));
        Label animalLabel = new Label("-  animal (darker = more energy)");
        legendGrid.add(animalCircle, 6, 0);
        legendGrid.add(animalLabel, 7, 0);

        return legendGrid;
    }
}
