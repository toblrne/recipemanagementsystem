package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

public class Recipe implements Writable {

    private String name;
    private String description;
    private int votes;
    private ArrayList<String> comments;

    public Recipe(String name, String description, int votes) {
        this.name = name;
        this.description = description;
        this.votes = votes;
        this.comments = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public int getVotes() {
        return this.votes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getComments() {
        if (comments.size() != 0) {
            StringBuilder allComments = new StringBuilder();
            for (String comment : comments) {
                allComments.append(comment).append(" ");

            }
            return allComments.toString();
        }
        return "no comments";
    }

    // MODIFIES: this
    // EFFECT: adds one to vote
    public void upVote() {
        this.votes++;
    }

    // MODIFIES: this
    // EFFECT: subtracts one to vote

    public void downVote() {
        this.votes--;
    }


    // Adapted from JSONSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("description", description);
        json.put("votes", votes);
        // comments to be added later
        return json;
    }
}




