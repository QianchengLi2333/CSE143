//<Qiancheng Li>
//<01/15/2021>
//CSE143 Section <A>
//<TA: Lisi Case>
//<Take-home Assessment #2>
//
//A class to represent the GuitarString——the strings of a guitar.
import java.util.*;

public class GuitarString {
    public static final double ENERGY_DECAY_FACTOR = 0.996;

    private Queue<Double> ringBuffer;

    //Constructs a GuitarString of the given frequency.
    //It will fill the ring buffer with zeros.
    //Throw an exception If the frequency is less than or equal to 0 
    //or if the resulting size of the ring buffer would be less than 2.
    //Parameter:
    //    frequency-the double which participates in calculating the capacityN;
    public GuitarString(double frequency) {
        int capacityN = (int)(Math.round(StdAudio.SAMPLE_RATE / frequency));
        if (frequency <= 0 || capacityN < 2) {
            throw new IllegalArgumentException();
        }
        ringBuffer = new LinkedList<>();
        for (int i = 0; i < capacityN; i++) {
            ringBuffer.add(0.0);
        }
    }

    //Constructs a GuitarString and initializes the contents to the values in the array.
    //Throw an exception if the array has fewer than two elements.
    //Parameter:
    //    init-the array of doubles whose values will be assigned to the ring buffer.
    public GuitarString(double[] init) {
        int capacityN = init.length;
        if (capacityN < 2) {
            throw new IllegalArgumentException();
        }
        ringBuffer = new LinkedList<>();
        for (int i = 0; i < capacityN; i++) {
            ringBuffer.add(init[i]);
        }
    }

    //Replace elements in the ring buffer with random values between -0.5 and +0.5.
    public void pluck() {
        for (int i = 0; i < ringBuffer.size(); i++) {
            double replacement = Math.random() - 0.5;
            ringBuffer.remove();
            ringBuffer.add(replacement);
        }
    }

    //Apply the Karplus-Strong update once.
    public void tic() {
        double first = ringBuffer.remove();
        double second = ringBuffer.peek();
        ringBuffer.add((first + second) / 2 * ENERGY_DECAY_FACTOR);
    }

    //Display the current sample.
    //Return the value at the front of the ring buffer.
    public double sample() {
        return ringBuffer.peek();
    }
}
