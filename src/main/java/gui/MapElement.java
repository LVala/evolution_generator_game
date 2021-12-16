package gui;

import evogen.Animal;
import evogen.IMapObject;
import evogen.Plant;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

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

}
