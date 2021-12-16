package gui;

import java.util.Set;

public class Main {

    // without this artificial main class JavaFX
    // always produces "runtime components are missing" error
    // covered on https://github.com/javafxports/openjdk-jfx/issues/236

    public static void main(String[] args) {
        //AbstractWorldMap map = new BoundedMap(10,10,100,10,10,0.1, 10);
        SettingsStage.main(args);

    }
}
