package persistence;

import model.GameStatus;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/*
 * Represents a writer that writes a JSON representation of the game status to file
 *
 * Citation: Based on code from JsonSerializationDemo.JsonWriter.java
 *           URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */
public class JsonWriter {
    private PrintWriter writer;
    private String destination;

    //EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    //MODIFIES: this
    //EFFECTS: opens writer; throws FileNotFoundException if file can not be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    //MODIFIES: this
    //EFFECTS: writes a JSON representation of the game status to file
    public void write(GameStatus gs) {
        JSONObject json = gs.toJson();
        saveToFile(json.toString());
    }

    //MODIFIES: this
    //EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    //MODIFIES: this
    //EFFECTS: writes the json string to file
    public void saveToFile(String json) {
        writer.print(json);
    }
}
