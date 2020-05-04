package game.listeners;

/**
 * The interface Movement notifier.
 */
public interface MovementNotifier {
    /**
     * Add ml as a listener to movements events.
     *
     * @param ml the movement listener to be added.
     */

    void addMovementListener(MovementListener ml);


    /**
     * Remove ml from the list of listeners to movements events.
     *
     * @param ml the movement listener to be removed.
     */
    void removeMovementListener(MovementListener ml);
}
