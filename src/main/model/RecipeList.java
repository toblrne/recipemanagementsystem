package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

public class RecipeList implements Writable {
    private String name;
    private ArrayList<Recipe> recipes;

    public RecipeList(String name) {
        this.name = name;
        this.recipes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    // Adapted from JSONSerializationDemo
    public List<Recipe> getRecipes() {
        return Collections.unmodifiableList(recipes);
    }


    // MODIFIES: this
    // EFFECTS: adds a recipe to the list

    public void addRecipeToList(Recipe recipe) {
        this.recipes.add(recipe);
        EventLog.getInstance().logEvent(new Event("new item " + recipe.getName() + " added to recipe list"));
    }

    // EFFECTS: searches for a recipe name in the list and outputs the recipe if found

    public Recipe getRecipe(String name) {
        for (Recipe recipe : this.recipes) {
            if (recipe.getName().equals(name)) {
                EventLog.getInstance().logEvent(new Event("recipe " + recipe.getName() + " found!"));
                return recipe;
            }
        }
        return null;
    }

    // EFFECTS: finds all the recipe names in the list

    public List<String> getAllRecipeNames() {
        List<String> listOfRecipeNames = new ArrayList<String>();
        for (Recipe recipe : recipes) {
            listOfRecipeNames.add(recipe.getName());
        }
        return listOfRecipeNames;
    }

    public int getLength() {
        return recipes.size();
    }

    // Adapted from JSONSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("recipes", recipesToJson());
        return json;
    }

    // Adapted from JSONSerializationDemo
    // EFFECTS: returns recipes in this recipeList as a JSON array
    private JSONArray recipesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Recipe r : recipes) {
            jsonArray.put(r.toJson());
        }

        return jsonArray;
    }
}
