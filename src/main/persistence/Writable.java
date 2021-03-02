package persistence;

import org.json.JSONObject;

public interface Writable {
    //EFFECTS: returns this object as a JSONOject
    JSONObject toJson();
}
