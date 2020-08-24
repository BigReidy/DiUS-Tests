package dius.test.bigreidy;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FrameTest {

    @Test
    public void testSimpleFrame() {
        Frame frame = new Frame(false);
        int firstScore = 1;
        int secondScore = 2;
        frame.bowl(firstScore);
        frame.bowl(secondScore);
        List<Integer> frameScore = frame.getScores();
        assertEquals(2, frameScore.size());
        assertEquals(firstScore,frameScore.get(0));
        assertEquals(secondScore,frameScore.get(1));
    }

    /**
     * Check Standard frame - 0 bowls, can bowl again
     */
    @Test
    public void testNoBowls() {
        Frame frame = new Frame(false);
        List<Integer> frameScore = frame.getScores();
        assertEquals(0, frameScore.size());
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
        frame.bowl(firstScore);
        List<Integer> frameScore = frame.getScores();
        assertEquals(1, frameScore.size());
        assertEquals(firstScore,frameScore.get(0) );
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
        List<Integer> frameScore = frame.getScores();
        assertEquals(2, frameScore.size());
        assertEquals(firstScore,frameScore.get(0));
        assertEquals(secondScore,frameScore.get(1) );
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
        List<Integer> frameScore = frame.getScores();
        assertEquals(1, frameScore.size());
        assertEquals(firstScore,frameScore.get(0));
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
        frame.bowl(firstScore);
        frame.bowl(secondScore);
        List<Integer> frameScore = frame.getScores();
        assertEquals(2, frameScore.size());
        assertEquals(firstScore,frameScore.get(0));
        assertEquals(secondScore,frameScore.get(1) );
        assertFalse(frame.isSpare());
        assertFalse(frame.isStrike());
        assertFalse(frame.canBowlAgain());
    }

    /**
     * Sanity check - cannot bowl negative
     */
    @Test
    public void testNegativeBowl(){
        final String errorText = "Cannot bowl negative numbers of pins";
        Frame frame = new Frame(false);
        int negativeScore = -1;
        try{
            frame.bowl(negativeScore);
            fail(String.format("Expected \"%s\"",errorText));
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
        final String errorText = "Cannot Bowl More Than 10 pins in a single bowl";
        Frame frame = new Frame(false);
        int singleMassiveScore = 11;
        try{
            frame.bowl(singleMassiveScore);
            fail(String.format("Expected \"%s\"",errorText));
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
            fail(String.format("Expected \"%s\"",errorText));
        }catch (IllegalArgumentException ex){
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertEquals(errorText, ex.getMessage());
        }
    }

    /**
     * Sanity check last frame - no third ball on no-strike/no-spare
     */
    @Test
    public void testLastFrameNoThirdBall(){
        final String errorText = "Cannot Bowl More times without strike or spare";
        Frame frame = new Frame(true);
        int firstScore = 5;
        int secondScore = 4;
        int thirdScore = 1;
        frame.bowl(firstScore);
        frame.bowl(secondScore);
        assertFalse(frame.canBowlAgain());
        try{
            frame.bowl(thirdScore);
            fail(String.format("Expected \"%s\"",errorText));
        }catch (IllegalArgumentException ex){
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertEquals(errorText, ex.getMessage());
        }
    }

    /**
     * Sanity check last frame - third ball on spare
     */
    @Test
    public void testLastFrameThirdBallSpare(){
        Frame frame = new Frame(true);
        int firstScore = 5;
        int secondScore = 5;
        int thirdScore = 1;
        frame.bowl(firstScore);
        frame.bowl(secondScore);
        assertTrue(frame.canBowlAgain());
        frame.bowl(thirdScore);
        assertFalse(frame.canBowlAgain());
        List<Integer> frameScore = frame.getScores();
        assertEquals(3, frameScore.size());
        assertEquals(firstScore,frameScore.get(0));
        assertEquals(secondScore,frameScore.get(1) );
        assertEquals(thirdScore,frameScore.get(2) );
    }
}