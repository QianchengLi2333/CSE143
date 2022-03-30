//<Qiancheng Li>
//<03/06/2021>
//CSE143 Section <A>
//<TA: Lisi Case>
//<Take-home Assessment #8>
//
//A class to represent the HuffmanNode, a node on the tree used in
//HuffmanCode.
import java.util.*;

public class HuffmanNode implements Comparable<HuffmanNode> {
    public char letter;
    public int frequency;
    public HuffmanNode left;
    public HuffmanNode right;

    //Construct a QuestionNode with the given character and frequency.
    //Its two child nodes will be set to null by default.
    //Parameters:
    //    char letter-the given character
    //    int frequency-the character's usage frequency
    public HuffmanNode(char letter, int frequency) {
        this(letter, frequency, null, null);
    }

    //Construct a QuestionNode with the given character and frequency 
    //and associate it with its two child nodes.
    //Parameters:
    //    character letter-the given character
    //    int frequency-the character's usage frequency
    //    HuffmanNode left-the left child of this HuffmanNode
    //    HuffmanNode right-the right child of this HuffmanNode
    public HuffmanNode(char letter, int frequency, HuffmanNode left, HuffmanNode right) {
        this.letter = letter;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    //Compare two HuffmanNodes.
    //Return an integer less than 0 to show this HuffmanNode is less,
    //an integer bigger than 0 to show it's bigger, and a 0 if they
    //have a tie.
    //Parameter:
    //    HuffmanNode other-another HuffmanNode to be compared
    public int compareTo(HuffmanNode other) {
        return this.frequency - other.frequency;
    }
}
