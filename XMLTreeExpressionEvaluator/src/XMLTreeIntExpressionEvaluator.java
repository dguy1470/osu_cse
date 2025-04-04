import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author David Neal Jr.
 *
 */
public final class XMLTreeIntExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeIntExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static int evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";

        int value = 0;
        //if exp has less than 1 child then it's just a single number
        if (exp.numberOfChildren() < 1) {
            value = Integer.parseInt(exp.attributeValue("value"));
        } else {
            //recursive step
            //Uses recursion to assign the two values to be our operands
            int operand1 = evaluate(exp.child(0));
            int operand2 = evaluate(exp.child(1));

            //XMLTree label = new XMLTree1(exp.label()); <Creates a bug
            //use exp.label() to decide what we are doing to the operands
            //times, divide, plus, or minus

            if (exp.label().equals("times")) {
                value = operand1 * operand2;
            } else if (exp.label().equals("divide")) {
                value = operand1 / operand2;
            } else if (exp.label().equals("plus")) {
                value = operand1 + operand2;
            } else if (exp.label().equals("minus")) {
                value = operand1 - operand2;
            }

        }
        return value;

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}
