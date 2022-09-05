package ui;

import model.*;
import model.Event;
import persistence.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;


public class RecipeAppGUI extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/recipestorage.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JLabel recipeName;
    private JTextField recipeNameField;
    private JLabel recipeDesc;
    private JTextField recipeDescField;
    private JTextField searchRecipeField;
    private JList listOfRecipes;
    private DefaultListModel model1;
    private RecipeList recipeList = new RecipeList("my recipes");
    private RecipeList tempList;
    private JLabel searchedName;
    private JLabel searchedDesc;
    private JLabel searchedVotes;

    // image functionality from https://www.youtube.com/watch?v=yGcYoz0s94E
    // https://media.istockphoto.com/vectors/recipe-book-hand-drawn-cover-vector-illustration-vector
    // based off of https://stackoverflow.com/questions/6578205/swing-jlabel-text-change-on-the-running-application.
    // but wrote in the context of own code
    // used oracle Docs

    // EFFECTS: sets up GUI and adds some components to screen
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public RecipeAppGUI() {
        super("Recipe App");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 700));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        handleJson();
        handleListOfRecipes();
        handleEnterRecipeName();
        handleEnterRecipeDescription();
        handleSubmitBoth();
        handleSearchRecipe();
        setLocationRelativeTo(null);


        // prints event log to console when window is closed
        // adapted from
        // https://stackoverflow.com/questions/57720396/difficulty-in-using-addwindowlistener
        // https://www.tabnine.com/code/java/methods/java.awt.Window/addWindowListener
        // wrote in the context of own code
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (Event event : EventLog.getInstance()) {
                    System.out.println(event);
                }
            }
        });

        setVisible(true);
        setResizable(true);
    }

    // EFFECTS: handles action events
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("addName")) {
            recipeName.setText(recipeNameField.getText());
        } else if (e.getActionCommand().equals("addDesc")) {
            recipeDesc.setText(recipeDescField.getText());
        } else if (e.getActionCommand().equals("submitBoth")) {
            addRecipe();
        } else if (e.getActionCommand().equals("search")) {
            displaySearchedRecipe();
        } else if (e.getActionCommand().equals("upvote")) {
            handleUpVote();
        } else if (e.getActionCommand().equals("downvote")) {
            handleDownVote();
        } else if (e.getActionCommand().equals("save")) {
            handleSave();
        } else if (e.getActionCommand().equals("load")) {
            handleLoad();
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up enter recipe name button and action listener
    public void handleEnterRecipeName() {
        JButton nameBtn = new JButton("Enter Recipe Name");
        nameBtn.setActionCommand("addName");
        nameBtn.addActionListener(this);
        recipeName = new JLabel("Burger");
        recipeNameField = new JTextField(10);
        add(recipeNameField);
        add(nameBtn);
        add(recipeName);
        pack();
    }

    // MODIFIES: this
    // EFFECTS: sets up enter recipe description button and action listener
    public void handleEnterRecipeDescription() {
        JButton descBtn = new JButton("Enter Recipe Description");
        descBtn.setActionCommand("addDesc");
        descBtn.addActionListener(this);
        recipeDesc = new JLabel("Step 1...");
        recipeDescField = new JTextField(10);
        add(recipeDescField);
        add(descBtn);
        add(recipeDesc);
        pack();
    }

    // MODIFIES: this
    // EFFECTS: sets up submit both button and action listener
    public void handleSubmitBoth() {
        JButton submitBtn = new JButton("Click to submit both!");
        submitBtn.setActionCommand("submitBoth");
        submitBtn.addActionListener(this);
        add(submitBtn);
        pack();
    }

    // EFFECTS: creates the list to display the list of recipes
    public void handleListOfRecipes() {
        model1 = new DefaultListModel<>();
        listOfRecipes = new JList<>(model1);
        JScrollPane recipeListScrollPane = new JScrollPane(listOfRecipes);
        recipeListScrollPane.setPreferredSize(new Dimension(650, 500));
        add(recipeListScrollPane, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: adds a recipe to the list
    private void addRecipe() {
        Recipe newRecipe = new Recipe("", "", 0);
        String name = recipeName.getText();
        newRecipe.setName(name);
        String description = recipeDesc.getText();
        newRecipe.setDescription(description);
        tempList = new RecipeList("My recipes");
        tempList.addRecipeToList(newRecipe);
        recipeList.addRecipeToList(newRecipe);
        addRecipeNameToList();
    }

    // EFFECTS: adds a recipe to the list on the screen
    public void addRecipeNameToList() {
        for (Recipe recipe : tempList.getRecipes()) {
            model1.addElement(recipe.getName());
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up searching for the recipe
    public void handleSearchRecipe() {
        JButton searchBtn = new JButton("Search for recipe");
        searchBtn.setActionCommand("search");
        searchBtn.addActionListener(this);
        searchRecipeField = new JTextField(10);
        add(searchRecipeField);
        add(searchBtn);
        searchedName = new JLabel("name of recipe");
        searchedDesc = new JLabel("description of recipe");
        searchedVotes = new JLabel("number of votes");
        add(searchedName);
        add(searchedDesc);
        add(searchedVotes);

        JButton upvoteBtn = new JButton("upvote");
        upvoteBtn.setActionCommand("upvote");
        upvoteBtn.addActionListener(this);
        JButton downvoteBtn = new JButton("downvote");
        downvoteBtn.setActionCommand("downvote");
        downvoteBtn.addActionListener(this);
        add(upvoteBtn);
        add(downvoteBtn);
    }

    // EFFECTS: displays the information for the searched recipe on the screen
    public void displaySearchedRecipe() {
        searchedName.setText(searchRecipeField.getText());
        String searchedNameText = searchRecipeField.getText();
        Recipe foundRecipe = recipeList.getRecipe(searchedNameText);
        String foundDescription = foundRecipe.getDescription();
        searchedDesc.setText(foundDescription);
        int foundVotes = foundRecipe.getVotes();
        searchedVotes.setText(String.valueOf(foundVotes));
        System.out.println(recipeList.getAllRecipeNames());
    }

    // EFEFCTS: upvotes a recipe
    public void handleUpVote() {
        searchedName.setText(searchRecipeField.getText());
        String searchedNameText = searchRecipeField.getText();
        Recipe foundRecipe = recipeList.getRecipe(searchedNameText);
        String foundDescription = foundRecipe.getDescription();
        searchedDesc.setText(foundDescription);
        foundRecipe.upVote();
        int foundVotes = foundRecipe.getVotes();
        searchedVotes.setText(String.valueOf(foundVotes));

    }

    // EFEFCTS: downvotes a recipe
    public void handleDownVote() {
        searchedName.setText(searchRecipeField.getText());
        String searchedNameText = searchRecipeField.getText();
        Recipe foundRecipe = recipeList.getRecipe(searchedNameText);
        String foundDescription = foundRecipe.getDescription();
        searchedDesc.setText(foundDescription);
        foundRecipe.downVote();
        int foundVotes = foundRecipe.getVotes();
        searchedVotes.setText(String.valueOf(foundVotes));
    }

    // EFFECTS: loads the JSON load and save buttons onto the screen
    public void handleJson() {
        JButton loadBtn = new JButton("Load Data");
        loadBtn.setActionCommand("load");
        loadBtn.addActionListener(this);

        JButton saveBtn = new JButton("Save Data");
        saveBtn.setActionCommand("save");
        saveBtn.addActionListener(this);

        add(loadBtn);
        add(saveBtn);
    }

    // EFFECTS: handles the save function
    public void handleSave() {
        try {
            jsonWriter.open();
            jsonWriter.write(recipeList);
            jsonWriter.close();
        } catch (FileNotFoundException ex) {
            //
        }
    }

    // EFFECTS: handles the load function
    public void handleLoad() {
        try {
            recipeList = jsonReader.read();
            addLoadedRecipeNamesToList();
        } catch (IOException ex) {
            //
        }
    }

    // EFFECTS: updates the on screen list with the saved names
    public void addLoadedRecipeNamesToList() {
        for (Recipe recipe : recipeList.getRecipes()) {
            model1.addElement(recipe.getName());
        }
    }


    public static void main(String[] args) {
        new RecipeAppGUI();
    }
}






