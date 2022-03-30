import java.util.*;
import java.io.*;

//<Qiancheng Li>
//<03/06/2021>
//CSE143 Section <A>
//<TA: Lisi Case>
//<Take-home Assessment #8>
//
//A class to represent the HuffmanCode.
//It can compress messages and decompress them.
public class HuffmanCode {
    private HuffmanNode overallNode;

    //Construct an instance of HuffmanCode.
    //Parameter:
    //    int[] frequencies-a list of integers representing the usage frequency 
    //                      of the character at that index
    public HuffmanCode(int[] frequencies) {
        Queue<HuffmanNode> frequency = new PriorityQueue<>();
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] > 0) {
                frequency.add(new HuffmanNode((char) i, frequencies[i]));
            }
        }
        while (frequency.size() >= 2) {
            HuffmanNode first = frequency.remove();
            HuffmanNode second = frequency.remove();
            int sumFreq = first.frequency + second.frequency;
            HuffmanNode sum = new HuffmanNode('0', sumFreq, first, second);
            frequency.add(sum);
        }
        overallNode = frequency.remove();
    }

    //Construct an instance of HuffmanCode with information from an input .code file.
    //The file is in legal, valid, and standard format.
    //Parameter:
    //    Scanner input-a scanner to read the file
    //    Scanner input is not null but links to the file
    public HuffmanCode(Scanner input) {
        while (input.hasNextLine()) {
            int asciiValue = Integer.parseInt(input.nextLine());
            String code = input.nextLine();
            overallNode = readTree(code, overallNode, asciiValue);
        }
    }

    //Construct an instance of HuffmanCode with information from an input .code file.
    //The file is in legal, valid, and standard format.
    //Return the current HuffmanNode we constructed according to the information from the file.
    //Parameter:
    //    String code-a string indicating the direction on the tree to get the character
    //    HuffmanNode currNode-current HuffmanNode we are checking
    //    int asciiValue- the ASCII value of this character
    private HuffmanNode readTree(String code, HuffmanNode currNode, int asciiValue) {
        if (currNode == null) {
            currNode = new HuffmanNode('0', 0);
        }
        if (code.equals("")) {
            currNode = new HuffmanNode((char) asciiValue, 0);
        } else if (code.charAt(0) == '0') {
            currNode.left = readTree(code.substring(1), currNode.left, asciiValue); 
        } else if (code.charAt(0) == '1') {
            currNode.right = readTree(code.substring(1), currNode.right, asciiValue);
        }
        return currNode;
    }

    //Store the current Huffman code to an output .code file in the standard format.
    //Parameter:
    //    PrintStream output-the specific output file
    public void save(PrintStream output) {
        saveTree(output, overallNode, "");
    }

    //Store the current Huffman code to an output .code file in the standard format.
    //Parameter:
    //    PrintStream output-the specific output file
    //    HuffmanNode currNode-the current HuffmanNode we are checking
    //    String direction-a string indicating the direction on the tree to get the character
    private void saveTree(PrintStream output, HuffmanNode currNode, String direction) {
        if (currNode.left == null && currNode.right == null) {
            output.println((int) currNode.letter);
            output.println(direction);
        } else {
            saveTree(output, currNode.left, direction + "0");
            saveTree(output, currNode.right, direction + "1");    
        }
    }

    //Read individual bits from the input .short file 
    //and write its corresponding character to output file.
    //Stop reading when reaching the end of the input file.
    //Input file contains legal encoding of characters.
    //Parameters:
    //    BitInputStream input-the input stream file
    //    PrintStream output-the output file
    public void translate(BitInputStream input, PrintStream output) {
        while (input.hasNextBit()) {
            translate(input, output, overallNode);
        }
    }

    //Read individual bits from the input .short file 
    //and write its corresponding character to output file.
    //Stop reading when reaching the end of the input file.
    //Input file contains legal encoding of characters.
    //Parameters:
    //    BitInputStream input-the input stream file
    //    PrintStream output-the output file
    //    HuffmanNode currNode-the HuffmanNode we are accessing
    private void translate(BitInputStream input, PrintStream output, HuffmanNode currNode) {
        if (currNode.left == null && currNode.right == null) {
            output.write((int) currNode.letter);
        } else {
            if (input.nextBit() == 0) {
                translate(input, output, currNode.left);
            } else {
                translate(input, output, currNode.right);
            }
        }    
    }
}
