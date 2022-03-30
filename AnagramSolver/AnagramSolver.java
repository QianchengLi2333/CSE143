//<Qiancheng Li>
//<02/20/2021>
//CSE143 Section <A>
//<TA: Lisi Case>
//<Take-home Assessment #6>
//
//A class to represent the AnagramSolver, which can 
//generate anagrams of the input text.
import java.util.*;

public class AnagramSolver {
    private Map<String, LetterInventory> storage;
    private List<String> dictionary;

    //Construct an instance of AnagramSolver, which uses 
    //a given dictionary of words for generating anagrams.
    //The dictionary is a nonempty collection of nonempty strings without duplicates.
    //The dictionary will not be changed.
    //Parameter:
    //    List<String> dictionary-a list of strings that will be stored to generate anagrams
    public AnagramSolver(List<String> dictionary) {
        this.dictionary = dictionary;
        storage = new HashMap<String, LetterInventory>();
        for (String word : dictionary) {
            LetterInventory letters = new LetterInventory(word);
            storage.put(word, letters);
        }
    }

    //Generate anagrams of the given string, 
    //which means combinations of words that have the same letters as the given string.
    //All combinations of words from the dictionary 
    //that includes at most max words will be generated.
    //A max of 0 means no limit for the words included.
    //Throw an IllegalArgumentException if max is less than 0.
    //Parameters:
    //    String text-the given string whose anagrams will be generated
    //    int max-the maximum number of words the combination can include
    public void print(String text, int max) {
        if (max < 0) {
            throw new IllegalArgumentException();
        }
        LetterInventory soFar = new LetterInventory(text);
        List<String> prunedDict = new ArrayList<String>();
        for (String word : dictionary) {
            LetterInventory letters = storage.get(word);
            if (soFar.subtract(letters) != null) {
                prunedDict.add(word);
            }
        }
        print(text, max, soFar, new ArrayList<String>(), prunedDict);
    }

    //Generate anagrams of the given string, 
    //which means combinations of words that have the same letters as the given string.
    //All combinations of words from the dictionary 
    //that include at most max words will be generated.
    //A max of 0 means no limit for the words included.
    //Parameters:
    //    String text-the given string whose anagrams will be generated
    //    int max-the maximum number of words the combination can include
    //    LetterInventory soFar-the LetterInventory of the given toString
    //    List<String> anagram-the combination of words to be generated
    //    List<String> prunedDict-a pruned dictionary that only contains 
    //                            words relevant to the given string
    private void print(String text, int max, LetterInventory soFar, 
                       List<String> anagram, List<String> prunedDict) {
        if (soFar.isEmpty()) {
            System.out.println(anagram);
        } else if (max == 0 || anagram.size() < max) {
            for (String word : prunedDict) {
                LetterInventory letters = storage.get(word);
                LetterInventory futureLetters = soFar.subtract(letters);
                if (futureLetters != null) {
                    anagram.add(word);
                    print(text, max, soFar.subtract(letters), anagram, prunedDict);
                    anagram.remove(anagram.size() - 1);
                    soFar.add(letters);
                } 
            }
        }
    }
}
