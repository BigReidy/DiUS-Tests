package dius.test.bigreidy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FrameTest {

    @Test
    public void testSimpleFrame() {
        Frame frame = new Frame(false);
        int firstScore = 1;
        int secondScore = 2;
        int totalScore = firstScore + secondScore;
        frame.bowl(firstScore);
        frame.bowl(secondScore);
        int frameScore = frame.getScore();
        assertEquals(totalScore, frameScore);
    }

    /**
     * Check Standard frame - 0 bowls, can bowl again
     */
    @Test
    public void testNoBowls() {
        Frame frame = new Frame(false);
        int totalScore = 0;
        int frameScore = frame.getScore();
        assertEquals(totalScore, frameScore);
        assertFalse(frame.isSpare());
        assertFalse(frame.isStrike());
        assertTrue(frame.canBowlAgain());
    }

    /**
     * Check Standard frame - 1 bowls, can bowl again
     */
    @Test
    public void testOneBowls() {
        Frame frame = new Frame(false);
        int firstScore = 5;
        int totalScore = firstScore;
        frame.bowl(firstScore);
        int frameScore = frame.getScore();
        assertEquals(totalScore, frameScore);
        assertFalse(frame.isSpare());
        assertFalse(frame.isStrike());
        assertTrue(frame.canBowlAgain());
    }

    /**
     * Check Standard frame - cannot bowl again - spare
     * If in 2 tries, the bowler knocks down all the pins, it is a spare.
     */
    @Test
    public void testSpare() {
        Frame frame = new Frame(false);
        int firstScore = 5;
        int secondScore = 5;
        int totalScore = firstScore + secondScore;
        frame.bowl(firstScore);
        frame.bowl(secondScore);
        int frameScore = frame.getScore();
        assertEquals(totalScore, frameScore);
        assertTrue(frame.isSpare());
        assertFalse(frame.isStrike());
        assertFalse(frame.canBowlAgain());

    }

    /**
     * Check Standard frame - cannot bowl again - strike
     * If in one try, the bowler knocks down all the pins, it is a strike.
     */
    @Test
    public void testStrike() {
        Frame frame = new Frame(false);
        int firstScore = 10;
        frame.bowl(firstScore);
        int frameScore = frame.getScore();
        assertEquals(firstScore, frameScore);
        assertTrue(frame.isStrike());
        assertFalse(frame.isSpare());
        assertFalse(frame.canBowlAgain());
    }

    /**
     * Check Standard frame - cannot bowl again
     */
    @Test
    public void testNeitherStrikeNorSpare() {
        Frame frame = new Frame(false);
        int firstScore = 5;
        int secondScore = 4;
        int totalScore = firstScore + secondScore;
        frame.bowl(firstScore);
        frame.bowl(secondScore);
        int frameScore = frame.getScore();
        assertEquals(totalScore, frameScore);
        assertFalse(frame.isSpare());
        assertFalse(frame.isStrike());
        assertFalse(frame.canBowlAgain());
    }

    /**
     * Sanity check - cannot bowl negative
     */
    @Test
    public void testNegativeBowl(){
        final String errorText = "Cannot Bowl Negatives";
        Frame frame = new Frame(false);
        int negativeScore = -1;
        try{
            frame.bowl(negativeScore);
            fail("Expected "+errorText);
        }catch (IllegalArgumentException ex){
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertEquals(errorText, ex.getMessage());
        }
    }

    /**
     * Sanity check - cannot bowl above 10
     */
    @Test
    public void testMaximumPinSingle(){
        final String errorText = "Cannot Bowl More Than 10 pins in a single frame";
        Frame frame = new Frame(false);
        int singleMassiveScore = 11;
        try{
            frame.bowl(singleMassiveScore);
            fail("Expected "+errorText);
        }catch (IllegalArgumentException ex){
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertEquals(errorText, ex.getMessage());
        }
    }

    /**
     * Sanity check - cannot bowl above 10 total pins (notLastFrame)
     */
    @Test
    public void testMaximumPinTotal(){
        final String errorText = "Cannot Bowl More Than 10 pins total for a frame";
        Frame frame = new Frame(false);
        int firstScore = 5;
        int secondScore = 6;
        frame.bowl(firstScore);
        try{
            frame.bowl(secondScore);
            fail("Expected "+errorText);
        }catch (IllegalArgumentException ex){
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertEquals(errorText, ex.getMessage());
        }
    }
}