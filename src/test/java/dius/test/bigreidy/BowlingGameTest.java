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
        IntStream.rangeClosed(1,BowlingGame.maxFrames).forEach(f->{
            bowlingGame.roll(2); //first
            bowlingGame.roll(3); //second
        });
        System.out.println(bowlingGame.getPrintFriendlyText());
        assertTrue(bowlingGame.isMatchEnded());
        try{
            bowlingGame.roll(4);
            fail("Expected "+errorText);
        }catch (IllegalStateException ex){
            assertEquals(IllegalStateException.class, ex.getClass());
            assertEquals(errorText, ex.getMessage());
        }
    }
}