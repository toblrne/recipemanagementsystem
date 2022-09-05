package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    // Adapted from JSONSerializationDemo
    @Test
    void testReadNonExistentFile() {
        JsonReader reader = new JsonReader("./data/ajsfnsdlkg.json");
        try {
            RecipeList rl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    // Adapted from JSONSerializationDemo
    @Test
    void testReadEmptyRecipeList() {
        JsonReader reader = new JsonReader("./data/testReadEmptyRecipeList.json");
        try {
            RecipeList rl = reader.read();
            assertEquals("my recipes", rl.getName());
            assertEquals(0, rl.getLength());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    // Adapted from JSONSerializationDemo
    @Test
    void testReadNonEmptyRecipeList() {
        JsonReader reader = new JsonReader("./data/testReadNonEmptyRecipeList.json");
        try {
            RecipeList rl = reader.read();
            assertEquals("my recipes", rl.getName());
            assertEquals(2, rl.getLength());
            List<Recipe> recipes = rl.getRecipes();
            checkProperties("burger1", "burger1", 1, recipes.get(0));
            checkProperties("burger2", "burger2", 0, recipes.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    // EFFECT: helper function to test if properties are equal
    public void checkProperties(String name, String description, int votes, Recipe recipe) {
        assertEquals(name, recipe.getName());
        assertEquals(description, recipe.getDescription());
        assertEquals(votes, recipe.getVotes());

    }



}
