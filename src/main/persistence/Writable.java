package persistence;

import org.json.JSONObject;

/**
 * Citation: Based on code from JsonSerializationDemo.Writable.java
 * URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */
public interface Writable {
    //EFFECTS: returns this object as a JSONObject
    JSONObject toJson();
}
