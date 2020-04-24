package utilities;

/**
 * The enum Direction.
 */
public enum Direction {
    /**
     * Left direction.
     */
    LEFT,
    /**
     * Top direction.
     */
    TOP,
    /**
     * Right direction.
     */
    RIGHT,
    /**
     * Bottom direction.
     */
    BOTTOM,
    /**
     * None direction.
     */
    NONE,
    /**
     * Both direction.
     */
    BOTH;


    public boolean directionParallel(Direction other) {
        if (this == other) {
            return true;
        } else if (this == LEFT || this == RIGHT) {
            return other == RIGHT || other == LEFT;
        } else if (this == TOP || this == BOTTOM) {
            return other == BOTTOM || other == TOP;
        } else {
            return false;
        }
    }
}
