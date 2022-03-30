//<Qiancheng Li>
//<02/27/2021>
//CSE143 Section <A>
//<TA: Lisi Case>
//<Take-home Assessment #7>
//
//A class to represent the QuestionGame.
//It is a game to guess an object through yes/no questions. 
import java.util.*;
import java.io.*;

public class QuestionsGame {
    private QuestionNode computer;
    private Scanner console;

    //Construct an instance of QuestionGame.
    //It is initialized to represent the object computer.
    public QuestionsGame() {
        computer = new QuestionNode("computer");
        console = new Scanner(System.in);
    }

    //Read a new set of questions and answers from a file and replace the current one with it.
    //The file is legal and in standard form consisting of line pairs whose first line indicates
    //whether it's a question or answer, and the next line contains the content of the text.
    //Parameter:
    //    Scanner input-the scanner that links to the file
    public void read(Scanner input) {
        computer = readTree(input);
    }

    //Read a new set of questions and answers from a file and replace the current one with it.
    //The file is legal and in standard form consisting of line pairs whose first line indicates
    //whether it's a question or answer, and the next line contains the content of the text.
    //Return the current QuestionNode we constructed accoding to information from the file.
    //Parameter:
    //    Scanner input-the scanner that links to the file
    private QuestionNode readTree(Scanner input) {
        String type = input.nextLine();
        String text = input.nextLine();
        QuestionNode currNode = new QuestionNode(text);
        if (type.equals("Q:")) {
            currNode.left = readTree(input);
            currNode.right = readTree(input);
        }
        return currNode;
    }

    //Store and write out the current set of questions and answers to an output file.
    //Then players can play another game with this set of questions and answers.
    //The output file will be in the standard format, which consists of line pairs 
    //whose first line indicates whether it's a question or answer, 
    //and the next line contains the content of the text.
    //Parameter:
    //    PrintStream output-the PrintStream that links to the output file
    public void write(PrintStream output) {
        writeTree(output, computer);
    }

    //Store and write out the current set of questions and answers to an output file.
    //Then players can play another game with this set of questions and answers.
    //The output file will be in the standard format, which consists of line pairs 
    //whose first line indicates whether it's a question or answer, 
    //and the next line contains the content of the text.
    //Parameter:
    //    PrintStream output-the PrintStream that links to the output file
    //    QuestionNode currNode-the starting node to be written
    private void writeTree(PrintStream output, QuestionNode currNode) {
        if (currNode.left == null && currNode.right == null) {
            output.println("A:\n" + currNode.text);
        } else {
            output.println("Q:\n" + currNode.text);
            writeTree(output, currNode.left);
            writeTree(output, currNode.right);
        }
    }

    //Play questions game with users using the current set of questions and answers.
    //It will keep asking yes/no questions until reaching an answer.
    //If the computer wins the game, it will report a message saying so.
    //Otherwise, it asks the player what is the object, a question to distinguish this object,
    //and the answer to this question and adds in this new pair of question and answer, so it
    //can distinguish this new object in the future games.
    public void askQuestions() {
        computer = askQuestions(computer);
    }

    //Play questions game with users using the current set of questions and answers.
    //It will keep asking yes/no questions until reaching an answer.
    //If the computer wins the game, it will report a message saying so.
    //Otherwise, it asks the player what is the object, a question to distinguish this object,
    //and the answer to this question and adds in this new pair of question and answer, so it
    //can distinguish this new object in the future games. 
    //Return the current QuestionNode we are accessing.
    //Parameter:
    //    QuestionNode currNode: the node to start asking questions from
    private QuestionNode askQuestions(QuestionNode currNode) {
        if (currNode != null) {
            if (currNode.left == null && currNode.right == null) {
                String check = "Would your object happen to be " + currNode.text + "?";
                if (yesTo(check)) {
                    System.out.println("Great, I got it right!");
                } else {
                    System.out.print("What is the name of your object? ");
                    String object = console.nextLine();
                    System.out.println("Please give me a yes/no question that");
                    System.out.println("distinguishes between your object");
                    System.out.print("and mine--> ");
                    String question = console.nextLine();
                    String userAns = "And what is the answer for your object?";
                    if (yesTo(userAns)) {
                        currNode = new QuestionNode(question, 
                                                    new QuestionNode(object), currNode);
                    } else {
                        currNode = new QuestionNode(question, 
                                                    currNode, new QuestionNode(object));
                    }
                }
            } else if (yesTo(currNode.text)){
                currNode.left = askQuestions(currNode.left);
            } else {
                currNode.right = askQuestions(currNode.right);
            }
        }
        return currNode;
    }

    // Do not modify this method in any way
    // post: asks the user a question, forcing an answer of "y" or "n";
    //       returns true if the answer was yes, returns false otherwise
    public boolean yesTo(String prompt) {
        System.out.print(prompt + " (y/n)? ");
        String response = console.nextLine().trim().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Please answer y or n.");
            System.out.print(prompt + " (y/n)? ");
            response = console.nextLine().trim().toLowerCase();
        }
        return response.equals("y");
    }
}
