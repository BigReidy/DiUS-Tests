package dius.test.bigreidy;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class BowlingGameTest {

    @Test
    void testBasicFirstBowlScoreTotal() {
        int scoreIn = 3;
        BowlingGame bowlingGame = new BowlingGame();
        bowlingGame.roll(3);
        int scoreOut = bowlingGame.score();
        assertEquals(scoreIn, scoreOut);
    }

    @Test
    void testGameReachesEndState() {
        final String errorText = "Match has ended";
        BowlingGame bowlingGame = new BowlingGame();
        IntStream.rangeClosed(1, BowlingGame.maxFrames).forEach(f -> {
            bowlingGame.roll(2); //first
            bowlingGame.roll(3); //second
        });
        assertTrue(bowlingGame.isMatchEnded());
        try {
            bowlingGame.roll(4);
            fail(String.format("Expected \"%s\"", errorText));
        } catch (IllegalStateException ex) {
            assertEquals(IllegalStateException.class, ex.getClass());
            assertEquals(errorText, ex.getMessage());
        }
    }

    /**
     * Test scoring the best game
     */
    @Test
    void testGameMaxScore() {
        BowlingGame bowlingGame = new BowlingGame();
        IntStream.rangeClosed(1, 12).forEach(f -> {
            bowlingGame.roll(10);
        });
        assertTrue(bowlingGame.isMatchEnded());
        assertEquals(300, bowlingGame.score());
    }


    /**
     * Test scoring an unstarted game
     */
    @Test
    void testUnstartedGame() {
        BowlingGame bowlingGame = new BowlingGame();
        assertFalse(bowlingGame.isMatchEnded());
        assertEquals(0, bowlingGame.score());
    }


    /**
     * Test ongoing game Spare
     * E.g, if a bowler rolls, 4,6 |  5, 0
     * Their score is 20. So that's (4 + 6 + 5) + (5 + 0)
     */
    @Test
    void testStartedGameSpare() {
        BowlingGame bowlingGame = new BowlingGame();
        bowlingGame.roll(4);
        bowlingGame.roll(6);
        bowlingGame.roll(5);
        bowlingGame.roll(0);
        assertEquals(20, bowlingGame.score());
    }

    /**
     * Test ongoing game Strike
     * E.g, if a bowler rolls, 10 | 5, 4
     * Their score is 28. So that's (10 + 5 + 4) + ( 5 + 4)
     */
    @Test
    void testStartedGameStrike() {
        BowlingGame bowlingGame = new BowlingGame();
        bowlingGame.roll(10);
        bowlingGame.roll(5);
        bowlingGame.roll(4);
        assertFalse(bowlingGame.isMatchEnded());
        assertEquals(28, bowlingGame.score());
    }

    /**
     * Test ongoing game part cannot be calculated
     * E.g, if a bowler rolls, 10 | 5, 4
     * Their score is 28. So that's (10 + 5 + 4) + ( 5 + 4)
     */
    @Test
    void testStartedGameStrikeMissingSecondRoll() {
        BowlingGame bowlingGame = new BowlingGame();
        bowlingGame.roll(10);
        bowlingGame.roll(7);
        assertFalse(bowlingGame.isMatchEnded());
        assertEquals(24, bowlingGame.score());
    }
}