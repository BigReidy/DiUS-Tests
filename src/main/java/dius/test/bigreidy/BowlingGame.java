package dius.test.bigreidy;

import java.util.HashMap;
import java.util.Map;

public class BowlingGame {

    private final Map<Integer,Frame> frameMap;
    private int score = 0;
    private int frameIdx = 0;

    public BowlingGame (){
        frameMap = new HashMap<>();
        frameMap.put(frameIdx, new Frame(false));
    }

    /**
     *
     * @param noOfPins
     */
    public void roll(int noOfPins) {
        //temp code
        score = score+1;
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
}
