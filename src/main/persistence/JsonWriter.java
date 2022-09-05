package persistence;

import model.*;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Adapted from JSONSerializationDemo

public class JsonWriter {

    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // Adapted from JSONSerializationDemo
    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // Adapted from JSONSerializationDemo
    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // Adapted from JSONSerializationDemo
    // MODIFIES: this
    // EFFECTS: writes JSON representation of recipeList to file
    public void write(RecipeList rl) {
        JSONObject json = rl.toJson();
        saveToFile(json.toString(TAB));
    }

    // Adapted from JSONSerializationDemo
    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // Adapted from JSONSerializationDemo
    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }


}
