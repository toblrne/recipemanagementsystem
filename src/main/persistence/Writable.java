package persistence;

import org.json.JSONObject;

// taken from JSONSerializationDemo

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
