package gui;

import evogen.AbstractWorldMap;
import evogen.Animal;
import evogen.Plant;
import evogen.Vector2d;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class MapGrid {
    public final GridPane mapGrid = new GridPane();

    private final int gridElemHeight;
    private final int gridElemWidth;

    private final AbstractWorldMap map;

    public MapGrid(AbstractWorldMap map) {
        this.mapGrid.setAlignment(Pos.TOP_CENTER);
        this.mapGrid.setPadding(new Insets(25, 25, 10, 25));

        this.gridElemHeight = 400/map.height;
        this.gridElemWidth = 400/map.width;

        this.map = map;
    }

    public void createMapGrid() {

        for (int i = 0; i < this.map.height; i++) {
            for (int j = 0; j < this.map.width; j++) {
                Vector2d vec = new Vector2d(j, i);
                StackPane stack;

                if (this.map.isOccupiedByAnimal(vec)) {
                    Animal animal = this.map.getStrongestAnimalsAt(vec).get(0);
                    stack = MapElement.getImage(gridElemWidth, gridElemHeight, animal, this.map.startEnergy, this.map.isInJungle(vec));
                }
                else if (this.map.isOccupiedByPlant(vec)) {
                    Plant plant = this.map.getPlantAt(vec);
                    stack = MapElement.getImage(gridElemWidth, gridElemHeight, plant, this.map.startEnergy, this.map.isInJungle(vec));
                }
                else {
                    stack = MapElement.getImage(gridElemWidth, gridElemHeight, null, this.map.startEnergy, this.map.isInJungle(vec));
                }
                this.mapGrid.add(stack, j, i);
            }
        }
    }
}
