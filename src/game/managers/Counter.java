package game.managers;

/**
 * The Counter class is used to keep track of counting.
 */
public class Counter {
    private int value;

    /**
     * Instantiates a new Counter with value 0.
     */
    public Counter() {
        this(0);
    }

    /**
     * Instantiates a new Counter.
     *
     * @param value the initial count value.
     */
    public Counter(int value) {
        this.value = value;
    }

    /**
     * Gets the current count value.
     *
     * @return the current count value
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Increase the current count value by the given number.
     *
     * @param number the number to be added to the current count value.
     */

    public void increase(int number) {
        this.value += number;
    }

    /**
     * Increase current count value by 1.
     */
    public void increase() {
        this.value++;
    }

    /**
     * decrease the current count value by the given number.
     *
     * @param number the number to be subtracted from the current count value.
     */
    public void decrease(int number) {
        this.value -= number;
    }


    /**
     * Decrease current count value by 1.
     */
    public void decrease() {
        this.value--;
    }


}
