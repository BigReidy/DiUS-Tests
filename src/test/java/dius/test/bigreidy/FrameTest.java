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
     * If in 2 tries, the bowler knocks down all the pins, it is a spare.
     * cannot bowl again
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
     * If in one try, the bowler knocks down all the pins, it is a strike.
     * cannot bowl again
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
     * cannot bowl again
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
}