package dius.test.bigreidy;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BowlingGame {

    protected static final int maxFrames = 10;
    private final Map<Integer, Frame> frameMap;
    private int frameIdx = 0;

    private boolean matchEnded = false;

    public BowlingGame() {
        frameMap = new HashMap<>();
        frameMap.put(frameIdx, new Frame(false));
    }

    /**
     * returns whether the match has ended
     *
     * @return has the matched ended
     */
    public boolean isMatchEnded() {
        return matchEnded;
    }

    /**
     * records the bowl, and will roll the frame over if needed
     *
     * @param noOfPins knocked down in this bowl
     * @throws IllegalStateException incorrect state of game
     */
    public void roll(int noOfPins) throws IllegalStateException {
        if (matchEnded) {
            throw new IllegalStateException("Match has ended");
        }
        Frame currentFrame = frameMap.get(frameIdx);
        currentFrame.bowl(noOfPins);

        if (!currentFrame.canBowlAgain()) {
            setupNextFrame();
        }
    }

    /**
     * Will setup the next frame in the game
     *
     * @throws IllegalStateException incorrect state of game
     */
    private void setupNextFrame() {
        frameIdx++;
        if (frameIdx >= maxFrames) {
            matchEnded = true;
            return;
        }
        Frame newFrame = new Frame(frameIdx == (maxFrames - 1));
        frameMap.put(frameIdx, newFrame);
    }

    /**
     * Calculates the current score.
     * We could keep a cache for performance if necessary
     *
     * @return total amount of points scored so far
     */
    public int score() {
        if (frameMap.size() == 0) return 0;
        return frameMap.keySet().stream()
                .map(this::calculateFrameScore)
                .reduce(Integer::sum).orElse(0);
    }

    /**
     * Quite an interesting problem to code for.
     * Steps:
     * 1. Add current frame up
     * 2. If Spare or strike, add next single roll
     * 3. If strike, add the roll after that
     * if not complete yet, exit out with a 0 bonus.
     * Last Frame doesn't do anything weird
     *
     * @param frameIndex index of frame to add up
     * @return total calculated for frame considering next frames
     */
    private int calculateFrameScore(int frameIndex) {
        Frame frame = frameMap.get(frameIndex);
        //get self score
        int frameScore = frame.getScores().stream().reduce(Integer::sum).orElse(0);
        //return that if not special
        if (!frame.isSpare() && !frame.isStrike()) return frameScore;

        //add the next point on for either strike or spare
        if (!frameMap.containsKey(frameIndex + 1)) return frameScore;
        Frame nextFrame = frameMap.get(frameIndex + 1);
        if (nextFrame.getScores().size() == 0) return frameScore;
        frameScore += nextFrame.getScores().get(0);

        //if spare, then only add the one
        if (frame.isSpare()) {
            return frameScore;
        }

        //for strike, we need to check if there's another on nextFrame, or then continue
        if (nextFrame.getScores().size() > 1) {
            frameScore += nextFrame.getScores().get(1);
        } else {
            if (!frameMap.containsKey(frameIndex + 2)) return frameScore;
            Frame frameAfter = frameMap.get(frameIndex + 2);
            if (frameAfter.getScores().size() == 0) return frameScore;
            frameScore += frameAfter.getScores().get(0);
        }
        return frameScore;
    }


    /**
     * Visual printer of the Frame.
     * Not tested, was a debug tool.
     *
     * @return formatted output of the frame
     */
    protected String getPrintFriendlyText() {
        String values = frameMap.values().stream()
                .map(Frame::getPrintFriendlyText)
                .map(f -> "[" + f + "]")
                .collect(Collectors.joining("\n"));
        if (isMatchEnded()) values = values + "\n Match Ended with score " + score();
        return values;
    }
}
