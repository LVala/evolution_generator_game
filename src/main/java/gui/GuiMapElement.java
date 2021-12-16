package gui;

import evogen.Animal;
import evogen.IMapObject;
import evogen.Plant;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GuiMapElement {

    public static StackPane getImage(int width, int height, IMapObject object, int maxEnergy, boolean isJungle) {
        //TODO kolory i gridlines
        StackPane stack = new StackPane();
        Rectangle rec = new Rectangle();
        rec.setHeight(height);
        rec.setWidth(width);

        if (object instanceof Plant) {
            rec.setFill(Color.web("0x06690B"));
            stack.getChildren().add(rec);
            return stack;
        }

        if (isJungle) {
            rec.setFill(Color.web("0x78F542"));
        }
        else {
            rec.setFill(Color.web("0x348C31"));
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
