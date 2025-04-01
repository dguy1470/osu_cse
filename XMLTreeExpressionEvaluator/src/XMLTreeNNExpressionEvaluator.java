import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author David Neal Jr.
 *
 */
public final class XMLTreeNNExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeNNExpressionEvaluator() {
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
    private static NaturalNumber evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";

        String val = "0";
        NaturalNumber value = new NaturalNumber2(val);
        //if exp has less than 1 child then it's just a single number
        if (exp.numberOfChildren() < 1) {
            val = exp.attributeValue("value");
            NaturalNumber temp = new NaturalNumber2(val);
            value.transferFrom(temp);
        } else {
            //recursive step
            //Uses recursion to assign the two values to be our operands
            NaturalNumber operand1 = evaluate(exp.child(0));
            NaturalNumber operand2 = evaluate(exp.child(1));

            //XMLTree label = new XMLTree1(exp.label()); <Creates a bug
            //use exp.label() to decide what we are doing to the operands
            //times, divide, plus, or minus

            if (exp.label().equals("times")) {
                operand1.multiply(operand2);
                value.copyFrom(operand1);

                //Report Fatal Error if subtract contract is violated
                // Can not divide by zero
            } else if (exp.label().equals("divide")) {
                if (operand2.isZero()) {
                    Reporter.fatalErrorToConsole(
                            "Division Error (Can not Divide by zero)");
                }
                operand1.divide(operand2);
                value.copyFrom(operand1);

            } else if (exp.label().equals("plus")) {
                operand1.add(operand2);
                value.copyFrom(operand1);

                //Report Fatal Error if subtract contract is violated
                // n2 must be smaller than n1
            } else if (exp.label().equals("minus")) {
                if (operand2.compareTo(operand1) > 0) {
                    Reporter.fatalErrorToConsole(
                            "subtraction Error (Can not subtract from smaller number)");
                }
                operand1.subtract(operand2);
                value.copyFrom(operand1);
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
