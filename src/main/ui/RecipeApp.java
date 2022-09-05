package ui;


import model.*;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class RecipeApp {

    // Adapted from JSONSerializationDemo and Teller Project

    private static final String JSON_STORE = "./data/recipestorage.json";
    private RecipeList recipeList;
    private Recipe recipe;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public RecipeApp() throws FileNotFoundException {
        runApp();
    }

    // Adapted from JSONSerializationDemo and Teller Project
    private void init() {
        recipeList = new RecipeList("my recipes");
        recipe = new Recipe("", "", 0);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

    }


    // adapted from Teller project and JSONSerializationDemo
    private void runApp() {
        boolean running = true;
        String command;

        init();
        displayMenu();

        while (running) {
            command = input.next();

            if (command.equals("q")) {
                running = false;
            } else {
                handleCommand(command);
            }
        }

        System.out.println("\nExiting");
    }

    // adapted from Teller project
    private void handleCommand(String command) {
        if (command.equals("d")) {
            displayRecipes();
        } else if (command.equals("a")) {
            addRecipe();
        } else if (command.equals("z")) {
            saveRecipes();
        } else if (command.equals("l")) {
            loadRecipes();
        } else {
            System.out.println("try another key");
        }
    }

    // EFFECTS: displays the recipe names, and allows user to select a recipe
    private void displayRecipes() {
        recipeList.getAllRecipeNames();
        if (recipeList.getLength() > 0) {
            System.out.println("type s to select a recipe");
            if (input.next().equals("s")) {
                selectRecipe();
            }
        } else {
            System.out.println("No recipes added! type a to add a new recipe");
        }
    }


    // EFFECTS: shows default display menu
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\td -> display recipes");
        System.out.println("\ta -> add a recipe");
        System.out.println("\tz -> save recipes to file");
        System.out.println("\tl -> load recipes from file");
        System.out.println("\tq -> quit");
    }


    // EFFECTS:  lets user select a recipe to view in more detail
    private void selectRecipe() {
        System.out.println("Type the name of the recipe you want to select");
        String name = input.next();
        Recipe selectedRecipe = recipeList.getRecipe(name);
        System.out.println(selectedRecipe.getName());
        System.out.println(selectedRecipe.getDescription());
        System.out.println(selectedRecipe.getVotes());
        System.out.println(selectedRecipe.getComments());
        System.out.println("vote by pressing v");
        if (input.next().equals("v")) {
            System.out.println("1 to upvote, 0 to downvote");
            if (input.next().equals("1")) {
                selectedRecipe.upVote();
            } else if (input.next().equals("0")) {
                selectedRecipe.downVote();
            }
        }

    }

    // EFFECTS: adds a recipe to the ui

    private void addRecipe() {
        Recipe newRecipe = new Recipe("", "", 0);
        System.out.println("Type the name of your recipe!");
        String name = input.next();
        newRecipe.setName(name);
        System.out.println("Type the description of your recipe!");
        String description = input.next();
        newRecipe.setDescription(description);
        this.recipeList.addRecipeToList(newRecipe);

    }

    // Adapted from JSONSerializationDemo
    // EFFECTS: saves the recipes to file
    private void saveRecipes() {
        try {
            jsonWriter.open();
            jsonWriter.write(recipeList);
            jsonWriter.close();
            System.out.println("Saved the recipes to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads recipes from file
    private void loadRecipes() {
        try {
            recipeList = jsonReader.read();
            System.out.println("Loaded recipes from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}




