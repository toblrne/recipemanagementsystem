package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecipeTest {

    Recipe r1;
    Recipe r2;
    Recipe r3;

    @BeforeEach
    void runBefore() {
        r1 = new Recipe("burger", "example desc", 0);
        r2 = new Recipe("sandwich", "example desc", 0);
        r3 = new Recipe("pizza", "example desc", 0);
    }

    @Test
    void testGetCommentsNone() {
        assertEquals("no comments", r1.getComments());
    }

    @Test
    void getComments() {
        r1.addComment("Hello");
        assertEquals(r1.getComments(), "Hello ");
    }

    @Test
    void testGetCommentsTwo() {
        r1.addComment("Hello");
        r1.addComment("Hello Again");
        assertEquals(r1.getComments(), "Hello Hello Again ");
    }



    @Test
    void testUpVote() {
        r1.upVote();
        assertEquals(r1.getVotes(), 1 );
    }

    @Test
    void testDownVote() {
        r1.downVote();
        assertEquals(r1.getVotes(), -1);
    }
}
