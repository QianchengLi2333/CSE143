//<Qiancheng Li>
//<01/08/2021>
//CSE143 Section <A>
//<TA: Lisi Case>
//<Take-home Assessment #1>
//
//A class to represent the LetterInventory——an inventory
//of letters of the English alphabet. LetterInventory is 
//case-insensitive, and it ignores non-alphabetic characters.
import java.util.*;

public class LetterInventory {
    public static final int COUNTER = 26;

    private int[] inventory;
    private int size;

    //Construct a new instance of LetterInventory to keep track of the occurence of each letter in a string. 
    //It ignores the case of letters and any non-alphabetic characters.
    //Parameter:
    //    String data-the string whose letters will be counted
    public LetterInventory(String data) {
        inventory = new int[COUNTER];
        size = 0;
        data = data.toLowerCase();
        for (int i = 0; i < data.length(); i++) {
            char reference = data.charAt(i);
            if (reference >= 'a' && reference <= 'z') {
                inventory[reference - 'a']++;
                size++;
            }
        }
    }

    //Calculate how many of this letter (case-insensitive) are in the inventory.
    //Return the count of this letter.
    //Throw an IllegalArgumentException if the letter is a nonalphabetic character.
    //Parameter:
    //    char letter-the target character to be checked with the number
    public int get(char letter) {
        letter = Character.toLowerCase(letter);
        checkLetter(letter);
        return inventory[letter - 'a'];
    }

    //Set the count for the given letter (case-insensitive) to the given value.
    //Throw an IllegalArgumentException if the letter is a nonalphabetic characteror or if value is negative.
    //Parameters:
    //    letter-the target character whose amount is to be altered
    //    value-the given value to be set on the amount of the letter
    public void set(char letter, int value) {
        letter = Character.toLowerCase(letter);
        checkLetter(letter);
        if (value < 0) {
            throw new IllegalArgumentException();
        }
        int difference = value - get(letter);
        size += difference;
        inventory[letter - 'a'] = value;
    }

    //Returns the sum of all of the counts in this inventory.
    public int size() {
        return size;
    }

    //Return if this LetterInventory is empty.
    public boolean isEmpty() {
        return (size == 0);
    }

    //Returns a string representation of the inventory.
    //Letters are all in lowercase and sorted order and surrounded by square brackets.
    public String toString() {
        if (this.isEmpty()) {
            return "[]";
        }
        String result = "[";
        for (int i = 0; i < COUNTER; i++) {
            for (int j = 0; j < inventory[i]; j++) {
                result += (char)('a' + i);
            }
        }
        result += "]";
        return result;
    }

    //Add the count of each letter in another LetterInventory.
    //Return a new LetterInventory that represents the sum.
    //Parameter:
    //    other-another LetterInventory to be added with the current one
    public LetterInventory add(LetterInventory other) {
        LetterInventory sum = new LetterInventory("");
        for (int i = 0; i < COUNTER; i++) {
            sum.inventory[i] = this.inventory[i]; 
            sum.inventory[i] += other.inventory[i];
            sum.size += sum.inventory[i];
        }
        return sum;
    }

    //Subtract the count of each letter in another LetterInventory from that of current one.
    //Return a new LetterInventory that represents the result of subtracting.
    //Return null if any resulting count would be negative.
    //Parameter:
    //    other-another LetterInventory to be subtracted from the current one
    public LetterInventory subtract(LetterInventory other) {
        LetterInventory difference = new LetterInventory("");
        for (int i = 0; i < COUNTER; i++) {
            difference.inventory[i] = this.inventory[i]; 
            difference.inventory[i] -= other.inventory[i];
            if (difference.inventory[i] < 0) {
                return null;
            }
            difference.size += difference.inventory[i];
        }
        return difference;
    }

    //Check if the letter is an alphabetic letter.
    //Throw an IllegalArgumentException if the letter is a nonalphabetic character.
    //Parameter:
    //    letter-the character to be checked
    private void checkLetter(char letter) {
        if (letter < 'a' || letter > 'z') {
            throw new IllegalArgumentException();
        } 
    }
}
