//<Qiancheng Li>
//<01/31/2021>
//CSE143 Section <A>
//<TA: Lisi Case>
//<Take-home Assessment #4>
//
//A class to represent the HangmanManager, which serves to play the 
//evil game of hangman.
import java.util.*;

public class HangmanManager {
    private Set<String> wordStorage;
    private int remainingGuess;
    private Set<Character> letters;
    private String currentPattern;

    //Construct a HangmanManager. It will create a set of words that  contain all 
    //words from the dictionary of the given length without duplicates.
    //Throw an IllegalArgumentException if length is less than 1 or if max is less than 0.
    //Parameters: 
    //    Collection<String> dictionary-the collection of strings to be passed, 
    //                                  which only contains non-empty and lowercase strings
    //    int length-the length of words we want to find
    //    int max-the integer that shows the maximum number of wrong guesses the player is allowed to make
    public HangmanManager(Collection<String> dictionary, int length, int max) {
        if (length < 1 || max < 0) {
            throw new IllegalArgumentException();
        }
        wordStorage = new TreeSet<String>();
        remainingGuess = max;
        letters = new TreeSet<Character>();
        currentPattern = "-";
        for (String word : dictionary) {
            if (word.length() == length) {
                wordStorage.add(word);
            }
        }
        for (int i = 1; i < length; i++) {
            currentPattern += " -";
        }
    }

    //Access the current set of words considered by the HangmanManager.
    //Return the set of words that are qualified to be the answer.
    public Set<String> words() {
        return wordStorage;
    }

    //Find out how many guesses the player can make.
    //Return the number of remaining guesses.
    public int guessesLeft() {
        return remainingGuess;
    }

    //Find out the set of letters that have been guessed by the player.
    //Return the set of characters the player has guessed.
    public Set<Character> guesses() {
        return letters;
    }

    //Find out the current pattern to be displayed for the game.
    //Letters that have not yet been guessed are displayed as a dash, 
    //and there are spaces separating the letters.
    //Throw an IllegalStateException if the set of words is empty.
    //Return the current pattern.
    public String pattern() {
        if (wordStorage.isEmpty()) {
            throw new IllegalStateException();
        }
        return currentPattern;
    }

    //Record that the player made a guess and let the game move forward.
    //After the given guess is made, if there is the occurrence of the guessed character,
    //current pattern will be updated to include that character, 
    //and the set of words being considered will be updated, eliminating choices that don't fit the pattern.
    //Otherwise, if the guessed character is not included, the remaining guesses will decrease by one.
    //In both cases the set of guessed letters will include the new guessed character.  
    //Throw an IllegalStateException if the player can make no guess, 
    //or if the set of words is empty.
    //Throw an IllegalArgumentException if the character had been guessed previously.
    //Return the number of occurrences of the guessed letter in the new pattern.
    //Parameter:
    //    char guess-the charcter guessed by the player, which is in lowercase 
    public int record(char guess) {
        if (remainingGuess < 1 || wordStorage.isEmpty()) {
            throw new IllegalStateException();
        } else if (letters.contains(guess)) {
            throw new IllegalArgumentException();
        }
        letters.add(guess);
        Map<String, Set<String>> family = new TreeMap<String, Set<String>>();
        updateMap(family, guess);
        int max = -1;
        String newPattern = "";
        for (String pattern : family.keySet()) {
            if (family.get(pattern).size() > max) {
                max = family.get(pattern).size();
                newPattern = pattern;
            }
        }
        wordStorage = family.get(newPattern);
        if (newPattern.equals(currentPattern)) {
            remainingGuess--;
        }
        currentPattern = newPattern;
        int occurrence = calculateOccurrence(guess);
        return occurrence;
    }

    //Update the map by classifying different word patterns and associating their corresponding set of strings.
    //Parameters:
    //    Map<String, Set<String>> map-the map of String to a Set of String, which is to be classified
    //    char guess-the character guessed by the player, which is in lowercase
    private void updateMap(Map<String, Set<String>> map, char guess) {
        for (String word : wordStorage) {
            String pattern = updatePattern(0, guess, word);
            for (int i = 1; i < word.length(); i++) {
                pattern += " ";
                String addition = updatePattern(i, guess, word);
                pattern += addition;
            }
            if (!map.containsKey(pattern)) {
                map.put(pattern, new TreeSet<>());
            }
            map.get(pattern).add(word);
        }
    }

    //Calculate the number of occurrences of the guessed character in the word.
    //Return the number of occurrences.
    //Parameter:
    //    char guess-the character guessed by the player, which is in lowercase
    private int calculateOccurrence(char guess) {
        int occurrence = 0;
        for (int i = 0; i < currentPattern.length(); i++) {
            if (currentPattern.charAt(i) == guess) {
                occurrence++;
            }
        }
        return occurrence;
    }

    //Append the correct character to the pattern.
    //Return the updated pattern.
    //Parameters:
    //    int index-the integer to trace the specific letter of this string
    //    char guess-the character guessed by the player, which is in lowercase
    //    String word-the string to check if it contains the guessed letter
    private String updatePattern(int index, char guess, String word) {
        String pattern = "";
        if (word.charAt(index) == guess) {
            pattern += guess;
        } else if (currentPattern.charAt(index * 2) != '-') {
            pattern += currentPattern.charAt(index * 2);
        } else {
            pattern += "-";
        }
        return pattern;
    }      
}
