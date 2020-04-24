package game.managers;

public class Counter {
    private int value;

    public Counter() {
        this(0);
    }

    public Counter(int value) {
        this.value = value;
    }

    // get current count.
    public int getValue() {
        return this.value;
    }

    // add number to current count.
    public void increase(int number) {
        this.value += number;
    }

    // subtract number from current count.
    public void decrease(int number) {
        this.value -= number;
    }
}
