//<Qiancheng Li>
//<01/15/2021>
//CSE143 Section <A>
//<TA: Lisi Case>
//<Take-home Assessment #2>
//
//A class to represent the Guitar37--a guitar that has 37 guitar strings.
import java.util.*;

public class Guitar37 implements Guitar {
    public static final String KEYBOARD =
        "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";  // keyboard layout

    private GuitarString[] guitar; 
    private int times;

    //Construct a new Guitar37.
    public Guitar37() {
        times = 0;
        guitar = new GuitarString[KEYBOARD.length()];
        for (int i = 0; i < KEYBOARD.length(); i++) {
            double respectiveFrequency = 440.0 * Math.pow(2.0, (i - 24.0) / 12.0);
            guitar[i] = new GuitarString(respectiveFrequency);
        }
    }

    //Play the specific note given by its corresponding pitch.
    //Can only play pitches between -24 and 12.
    //Other illegal pitches will be ignored. 
    //Prameter:
    //    pitch-the integer to specify the corresponding string of the note
    public void playNote(int pitch) {
        if (pitch >= -24 && pitch <= 12) {
            guitar[pitch + 24].pluck(); 
        }
    }

    //Verify if this has a corresponding string for this guitar.
    //Return if this guitar can play that key.
    //Parameter:
    //    key-the character to be checked if it has its corresponding string on this guitar
    public boolean hasString(char key) {
        return (KEYBOARD.indexOf(key) != -1);
    }

    //Play the specific note given its key.
    //Throw an exception if the guitar does not have a string to play that key.
    //Parameter:
    //    key-the character to specify the string to be plucked
    public void pluck(char key) {
        if (!hasString(key)) {
            throw new IllegalArgumentException();
        }
        int stringIndex = KEYBOARD.indexOf(key);
        guitar[stringIndex].pluck();
    }

    //Get the current sound sample.
    //Return the sum of all samples from the strings of the guitar.
    public double sample() {
        double result = 0.0;
        for (int i = 0; i < KEYBOARD.length(); i++) {
            result += guitar[i].sample();
        }
        return result;
    }

    //Tic the guitar and advance the time forward one “tic”.
    public void tic() {
        for (int i = 0; i < KEYBOARD.length(); i++) {
            guitar[i].tic();
        }
        times++;
    }

    //Determine the current time.
    //Return the number of times tic has been called.
    public int time() {
        return times;
    }
}
