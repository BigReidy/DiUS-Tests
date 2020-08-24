package dius.test.bigreidy;

import java.util.HashMap;
import java.util.Map;

/**
 * Frame class will keep track and manage each frame of bowling.
 * Expectation is that the caller will know to check if the frame can be bowled again.
 * Lombok's getter/setter simplifier would reduce template code.
 * We're going to use generic exception here, rather than creating our own.
 */
public class Frame {

    private final Map<Integer, Integer> frameScoreMap;
    private final boolean lastFrame;
    private int bowlIdx;

    /**
     * Create a frame for bowling, indicating if it is the last frame, which has special scoring rules
     *
     * @param isLastFrame
     */
    public Frame(boolean isLastFrame) {
        frameScoreMap = new HashMap<>();
        lastFrame = isLastFrame;
        bowlIdx = 0;
    }

    /**
     * Check if we can record more scores, on the last frame we can record 3 if they strike twice
     *
     * @return
     */
    public boolean canBowlAgain() {
        if (!lastFrame && bowlIdx < 2) {
            return true;
        }
        if (lastFrame && bowlIdx < 3) {
            //TODO logic around 3rd strike score
            return true;
        }
        return false;
    }

    /**
     * bowl for a frame
     *
     * @param score number of pins knocked down
     * @throws IllegalStateException This function was called in an incorrect state
     */
    public void bowl(int score) throws IllegalStateException {
        if (!canBowlAgain()) {
            throw new IllegalStateException("Cannot bowl again for this frame");
        }
        //TODO deal with invalid score combinations (9,9) (1,10) etc.
        frameScoreMap.put(this.bowlIdx, score);
        bowlIdx++;
    }

    /**
     * Return the total score for this frame
     *
     * @return sum of the scored values
     */
    public int getScore() {
        return frameScoreMap.values().stream()
                .reduce(Integer::sum)
                .orElse(0);
    }

}