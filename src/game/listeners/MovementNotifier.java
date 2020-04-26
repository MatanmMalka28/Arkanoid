package game.listeners;

public interface MovementNotifier {
    // Add ml as a listener to movements events.
    void addMovementListener(MovementListener ml);

    // Remove ml from the list of listeners to movements events.
    void removeMovementListener(MovementListener ml);
}
