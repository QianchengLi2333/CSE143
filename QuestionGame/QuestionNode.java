//<Qiancheng Li>
//<02/27/2021>
//CSE143 Section <A>
//<TA: Lisi Case>
//<Take-home Assessment #7>
//
//A class to represent the QuestionNode, a node on the question tree that
//consists of questions or answers depending on its type and left and right
//child question node.
public class QuestionNode {
    public String text;
    public QuestionNode left;
    public QuestionNode right;

    //Construct a QuestionNode with the given text.
    //Its two child nodes will be set to null by default.
    //Parameter:
    //    String text-the given text
    public QuestionNode(String text) {
        this(text, null, null);
    }

    //Construct a QuestionNode with the given text and associate it with its two child nodes.
    //Parameters:
    //    String text-the given text
    //    QuestionNode left-the left child of this QuestionNode
    //    QuestionNode right-the right child of this QuestionNode
    public QuestionNode(String text, QuestionNode left, QuestionNode right) {
        this.text = text;
        this.left = left;
        this.right = right;
    }
}
