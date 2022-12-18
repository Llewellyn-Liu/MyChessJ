package core.component;

/**
 * Designed for ChessPlateController to mark and store a certain move.
 */


import core.component.Chess;

public class Move {

    private Coordinate from;

    private Coordinate target;

    private boolean withConquer = false;

    public Move(Coordinate from, Coordinate target) {
        this.from = from;
        this.target = target;
    }

    public Move(Coordinate from, Coordinate target, boolean withConquer) {
        this.from = from;
        this.target = target;
        this.withConquer = withConquer;
    }


    public Coordinate getFrom() {
        return from;
    }

    public void setFrom(Coordinate from) {
        this.from = from;
    }

    public Coordinate getTarget() {
        return target;
    }

    public void setTarget(Coordinate target) {
        this.target = target;
    }

    public boolean withConquer() {
        return withConquer;
    }

    public void setWithConquer(boolean withConquer) {
        this.withConquer = withConquer;
    }
}
