//<Qiancheng Li>
//<02/05/2021>
//CSE143 Section <A>
//<TA: Lisi Case>
//<Take-home Assessment #5>
//
//A class to represent the GrammarSolver, which can 
//generate grammar element according to the user's input.
import java.util.*;

public class GrammarSolver {
    private SortedMap<String, String[]> grammar;

    //Construct an instance of GrammarSolver, which restores rules of the grammar of the BNF format.
    //Throw an IllegalArgumentException if the list of rules is empty, 
    //or if there are two or more definitions of one element.
    //Parameter:
    //    List<String> rules-the list of strings that contains rules of the grammar.
    //    Rules are in the BNF format.                  
    public GrammarSolver(List<String> rules) {
        if (rules.isEmpty()) {
            throw new IllegalArgumentException();
        }
        grammar = new TreeMap<String, String[]>();
        for (int i = 0; i < rules.size(); i++) {
            String rule = rules.get(i);
            String[] pieces = rule.split("::=");
            String symbol = pieces[0];
            if (grammar.containsKey(symbol)) {
                throw new IllegalArgumentException(); 
            }
            String remaining = pieces[1];
            String[] composition = remaining.split("\\|"); 
            grammar.put(symbol, composition);
        }
    }

    //Check if the specific symbol is a non-terminal in the grammar.
    //Return true if the symbol is included as a non-terminal in grammar, and false otherwise.
    //Parameter:
    //    String symbol-the symbol to be checked
    public boolean grammarContains(String symbol) {
        return grammar.containsKey(symbol);
    }

    //Show all non-terminals of this grammar.
    //Return the string representation of all non-terminals, which will be in a sorted sequence and
    //separated by commas inside square brackets.  
    public String getSymbols() {
        return grammar.keySet().toString();
    }

    //Generate random instances of the given symbol for specific times.
    //Throw an IllegalArgumentException if times is less than zero, 
    //or if the grammar does not contain the asked symbol.
    //Return a string list that stores each random instance of the symbol with one space separating each other.
    //No leading or tailing space
    //Parameters:
    //    String symbol-the specific type of the non-terminal element the client wants to generate
    //    int times-the specific times this method will generate random instance of the symbol   
    public String[] generate(String symbol, int times) {
        if (times < 0 || !grammarContains(symbol)) {
            throw new IllegalArgumentException();
        }
        String[] result = new String[times];
        for (int i = 0; i < times; i++) {
            result[i] = generateSymbol(symbol);
        }
        return result;
    }

    //Generate only one instance of the given symbol.
    //Return the generated symbol instance as a string.
    //Parameter:
    //    String symbol-the specific type of the non-terminal element the client wants to generate
    private String generateSymbol(String symbol) {
        return generateSymbol(symbol, "");
    }

    //Generate only one instance of the given symbol.
    //Return the generated symbol instance as a string.
    //Parameters:
    //    String symbol-the specific type of the non-terminal element
    //    String result-the result string representing the symbol instance
    private String generateSymbol(String symbol, String result) {
        if (!grammarContains(symbol)) {
            result += symbol;
        } else {
            String[] composition = grammar.get(symbol);
            int choice = composition.length;
            int numRandom = (int)(Math.random() * choice);
            String[] subComposition = composition[numRandom].split("\\s+");
            for (int i = 0; i < subComposition.length; i++) {
                result += generateSymbol(subComposition[i]) + " ";
            }
        }
        result = result.trim();
        return result;
    }
}
