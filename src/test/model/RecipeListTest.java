package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeListTest {

    RecipeList rl1;
    Recipe r1;
    Recipe r2;

    @BeforeEach
    void runBefore() {
        rl1 = new RecipeList("my recipes");
        r1 = new Recipe("burger", "example desc", 0);
        r2 = new Recipe("sandwich", "example desc", 0);
    }

    @Test
    void testAddRecipeToList() {
        assertEquals(rl1.getLength(), 0);
        rl1.addRecipeToList(r1);
        assertEquals(rl1.getLength(), 1);
    }

    @Test
    void testGetRecipeInList() {
        rl1.addRecipeToList(r1);
        assertEquals(rl1.getRecipe("burger"), r1);

    }

    @Test
    void testGetRecipeNotInList() {
        rl1.addRecipeToList(r1);
        assertNull(rl1.getRecipe("sandwich"));

    }

    @Test
    void testGetRecipeInListWithTwoElements() {
        rl1.addRecipeToList(r1);
        rl1.addRecipeToList(r2);
        assertEquals(rl1.getRecipe("sandwich"), r2);

    }

    @Test
    void testGetAllRecipeNames() {
        assertEquals(rl1.getAllRecipeNames(), "");

    }



}
