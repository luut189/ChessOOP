package engine;

import engine.utils.Flag;

public class Move {
    
    private int startPosition;
    private int targetPosition;
    private Flag flag;

    private final int rankMask = 0b11110000;
    private final int fileMask = 0b00001111;

    public Move(int startRank, int startFile, int endRank, int endFile, Flag flag) {
        this.startPosition = (startRank << 4) | startFile;
        this.targetPosition = (endRank << 4) | endFile;
        this.flag = flag;
    }

    public int getStartRank() {
        return (this.startPosition & rankMask) >> 4;
    }

    public int getStartFile() {
        return this.startPosition & fileMask;
    }

    public int getTargetRank() {
        return (this.targetPosition & rankMask) >> 4;
    }

    public int getTargetFile() {
        return this.targetPosition & fileMask;
    }

    public Flag getFlag() {
        return this.flag;
    }
}
