package dius.test.bigreidy;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
        BowlingGame bowlingGame = new BowlingGame();
        //10 frames with 2 bowls of each less than 10 should end game.
        IntStream.rangeClosed(1,10).forEach(f->{
            bowlingGame.roll(2); //first
            bowlingGame.roll(3); //second
        });
        try{
            bowlingGame.roll(4);
            fail("Expected \"Match has ended\"");
        }catch (IllegalStateException ex){
            assertEquals(IllegalStateException.class, ex.getClass());
            assertEquals("Match has ended", ex.getMessage());
        }
    }
}