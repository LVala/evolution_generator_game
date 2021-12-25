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
    private final MapElement mapElement;

    public MapGrid(AbstractWorldMap map, AnimalInfoGrid animalInfoGrid) {
        this.mapGrid.setAlignment(Pos.TOP_CENTER);
        this.mapGrid.setPadding(new Insets(25, 25, 10, 10));

        this.gridElemHeight = 300/map.height;
        this.gridElemWidth = 300/map.width;

        this.map = map;
        this.mapElement = new MapElement(gridElemWidth, gridElemHeight, this.map.startEnergy, animalInfoGrid);
    }

    public void createMapGrid() {

        for (int i = 0; i < this.map.height; i++) {
            for (int j = 0; j < this.map.width; j++) {
                Vector2d position = new Vector2d(j, i);
                StackPane stack;

                if (this.map.isOccupiedByAnimal(position, 1)) {
                    Animal animal = this.map.getStrongestAnimalAt(position);
                    stack = mapElement.getImage(animal, this.map.isInJungle(position));
                }
                else if (this.map.isOccupiedByPlant(position)) {
                    Plant plant = this.map.getPlantAt(position);
                    stack = mapElement.getImage(plant, this.map.isInJungle(position));
                }
                else {
                    stack = mapElement.getImage(null, this.map.isInJungle(position));
                }
                this.mapGrid.add(stack, j, i);
            }
        }
    }
}
