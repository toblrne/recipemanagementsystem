package persistence;

import model.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;


// Adapted from JSONSerializationDemo
public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            RecipeList rl = new RecipeList("my recipes");
            JsonWriter writer = new JsonWriter("./data/sadsaf!~\0sdasdad.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }


}
