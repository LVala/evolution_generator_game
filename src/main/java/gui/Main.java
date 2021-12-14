package gui;

public class Main {

    // without this artificial main class JavaFX
    // always produces "runtime components are missing" error
    // covered on https://github.com/javafxports/openjdk-jfx/issues/236

    public static void main(String[] args) {
        App.main(args);
    }
}
