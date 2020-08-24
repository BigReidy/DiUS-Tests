package dius.test.bigreidy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
     * note: should consider Polymorphism of a 'lastFrame' implementaiton
     * @param isLastFrame last frame indicator for behaviour
     */
    public Frame(boolean isLastFrame) {
        frameScoreMap = new HashMap<>();
        lastFrame = isLastFrame;
        bowlIdx = 0;
    }

    /**
     * Check if we can record more scores, on the last frame we can record 3 if they strike twice
     *
     * @return whether you can take another bowl this Frame
     */
    public boolean canBowlAgain() {
        if (lastFrame) {
            if (!isStrike() && !isSpare()) {
                //If you get a strike or spare, you get all 3 roles, therefore, if neither, only 2 rolls
                return bowlIdx < 2;
            }
            return bowlIdx < 3;
        }
        if (isStrike()) {
            return false;
        }
        return bowlIdx < 2;
    }

    /**
     * bowl for a frame
     *
     * @param score number of pins knocked down
     * @throws IllegalStateException This function was called in an incorrect state
     */
    public void bowl(int score) throws IllegalStateException, IllegalArgumentException {
        if (!canBowlAgain()) {
            throw new IllegalStateException("Cannot bowl again for this frame");
        }
        if (score < 0) {
            throw new IllegalArgumentException("Cannot bowl negative numbers of pins");
        }
        if (score > 10) {
            throw new IllegalArgumentException("Cannot Bowl More Than 10 pins in a single bowl");
        }
        if (!lastFrame) {
            if (bowlIdx == 1 && (frameScoreMap.get(0) + score) > 10) {
                throw new IllegalArgumentException("Cannot Bowl More Than 10 pins total for a frame");
            }
        } else {
            if (bowlIdx == 1) {
                // If on the second ball, and the first one's a strike, then we don't sum it
                if (!isStrike() && (frameScoreMap.get(0) + score) > 10) {
                    throw new IllegalArgumentException("Cannot Bowl More Than 10 pins total for a frame");
                }
            }
            if (bowlIdx == 2 && isStrike()) {
                // IF on the third ball, and the first was a strike, we need to check maximum when second isn't a strike
                int secondBowl = frameScoreMap.get(1);
                if (!isLastFrameSecondStrike() && (secondBowl + score) > 10) {
                    throw new IllegalArgumentException("Cannot Bowl More Than 10 pins total for a frame");
                }
            }

        }
        frameScoreMap.put(this.bowlIdx, score);
        bowlIdx++;
    }

    /**
     * Return the total score for this frame
     *
     * @return sum of the scored values
     */
    public List<Integer> getScores() {
        return new ArrayList<>(frameScoreMap.values());
    }

    /**
     * if the first bowl of a frame is a 10 (all pins), it is a strike
     *
     * @return whether it is a strike
     */
    public boolean isStrike() {
        if (frameScoreMap.size() < 1) {
            return false;
        }
        return frameScoreMap.get(0) == 10;
    }

    /**
     * if the last frame and a strike on the second ball
     * @return whether it is a strike
     */
    public boolean isLastFrameSecondStrike() {
        if (!lastFrame || frameScoreMap.size() < 2) {
            return false;
        }
        return frameScoreMap.get(1) == 10;
    }

    /**
     * if the last frame and a strike on the third ball
     * @return whether it is a strike
     */
    public boolean isLastFrameThirdStrike() {
        if (!lastFrame || frameScoreMap.size() < 3) {
            return false;
        }
        return frameScoreMap.get(2) == 10;
    }

    /**
     * if both the bowls of a standard frame is 10 (all pins knocked down over 2 bowls), it is a spare
     *
     * @return whether it is a spare
     */
    public boolean isSpare() {
        if (frameScoreMap.size() < 2) {
            return false;
        }
        int total = frameScoreMap.get(0) + frameScoreMap.get(1);
        return total == 10;
    }

    /**
     * Visual printer of the Frame.
     *
     * @return formatted output of the frame
     */
    public String getPrintFriendlyText() {
        if (isStrike()) return "X";
        if (isSpare()) return frameScoreMap.get(0) + ",\\";
        String values = frameScoreMap.values().stream().map(Object::toString).collect(Collectors.joining(","));
        if (canBowlAgain()) return values + ", ";
        return values;
    }
}
