package dius.test.bigreidy;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BowlingGame {

    protected static final int maxFrames = 10;
    private final Map<Integer,Frame> frameMap;
    private int frameIdx = 0;

    private boolean matchEnded = false;

    public BowlingGame (){
        frameMap = new HashMap<>();
        frameMap.put(frameIdx, new Frame(false));
    }

    /**
     * returns whether the match has ended
     * @return has the matched ended
     */
    public boolean isMatchEnded() {
        return matchEnded;
    }

    /**
     * records the bowl, and will roll the frame over if needed
     * @param noOfPins knocked down in this bowl
     * @throws IllegalStateException incorrect state of game
     */
    public void roll(int noOfPins) throws IllegalStateException{
        if (matchEnded){
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
     * @return next frame
     * @throws IllegalStateException incorrect state of game
     */
    private void setupNextFrame() {
        frameIdx++;
        if (frameIdx >= maxFrames) {
            matchEnded=true;
            return;
        }
        Frame newFrame = new Frame(frameIdx == (maxFrames-1));
        frameMap.put(frameIdx, newFrame);
    }

    /**
     * Calculates the current score.
     * We could keep a cache for performance if necessary
     * @return total amount of points scored so far
     */
    public int score() {
        return frameMap.values().stream()
                .map(Frame::getScore)
                .reduce(Integer::sum)
                .orElse(0);
    }


    /**
     * Visual printer of the Frame.
     * @return formatted output of the frame
     */
    public String getPrintFriendlyText() {
        String values = frameMap.values().stream()
                .map(Frame::getPrintFriendlyText)
                .map(f-> "["+f+"]")
                .collect(Collectors.joining("\n"));
        if(isMatchEnded()) values = values + "\n Match Ended";
        return values;
    }
}
