package game.listeners;

public interface MovementListener {
    // This method is called whenever the Paddle object is moved.
    // The hitter parameter is the Ball that's doing the hitting.
    void movementEvent();
}
