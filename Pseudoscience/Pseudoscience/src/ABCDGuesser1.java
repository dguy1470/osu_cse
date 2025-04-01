import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * Put a short phrase describing the program here.
 *
 * @author David Neal Jr.
 *
 */
public final class ABCDGuesser1 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ABCDGuesser1() {
    }

    /**
     * Repeatedly asks the user for a positive real number until the user enters
     * one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @param x
     *            the users first input
     * @return a positive real number entered by the user
     */
    private static double getPositiveDouble(String x, SimpleReader in,
            SimpleWriter out) {
        //Set variable userInput to users first input
        String userInput = x;
        //Declare variable to store userInput as a double
        Double userNum;
        //If UserInput is a valid double check it's positive
        // if not have them repeat process
        if (FormatChecker.canParseDouble(userInput)) {
            userNum = Double.parseDouble(userInput);
            while (userNum <= 0) {
                out.println("Real Number must be Positive! ");
                userInput = in.nextLine();
                userNum = getPositiveDouble(userInput, in, out);
            }
            //If UserInput is not a valid Double
            // have them repeat process until it's acceptable
        } else {
            while (!FormatChecker.canParseDouble(userInput)) {
                out.println("Please enter a REAL NUMBER!");
                userInput = in.nextLine();
            }
            userNum = getPositiveDouble(userInput, in, out);
        }
        return userNum;
    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number not equal to 1.0 entered by the user
     */
    private static double getPositiveDoubleNotOne(SimpleReader in,
            SimpleWriter out) {
        //Ask user for a number and stor it as x
        out.println("Enter number that means something to you: ");
        String x = in.nextLine();
        //use getPositiveDouble method to ensure x is a positive double
        //and store it as posDoub
        Double posDoub = getPositiveDouble(x, in, out);
        // If posDoub equals 1 make the user try again
        while (posDoub == 1) {
            out.println("Number can't equal 1");
            x = in.nextLine();
            posDoub = getPositiveDouble(x, in, out);
        }
        return posDoub;

    }

    /**
     * Takes the users 4 meaningful numbers and uses them to calculate the users
     * chosen number with the help of the de Jager formula.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @param userNum
     *            user value we are trying to calculate
     * @param w
     *            users first of 4 numbers we used to calculate userNum
     * @param x
     *            users second of 4 numbers we used to calculate userNum
     * @param y
     *            users third of 4 numbers we used to calculate userNum
     * @param z
     *            users fourth of 4 numbers we used to calculate userNum
     */

    private static void calculation(SimpleReader in, SimpleWriter out,
            Double userNum, Double w, Double x, Double y, Double z) {
        // these variables will store our best values for a-d
        double aVal = 0, bVal = 0, cVal = 0, dVal = 0;
        // these values will represent a-d (our power numbers)
        double a = 0.0, b = 0.0, c = 0.0, d = 0.0;
        // loop counting variables
        int h = 0, i = 0, j = 0, k = 0;
        // an array that stores our 17 possible values for a, b, c, and d
        final double[] powNums = { -5, -4, -3, -2, -1, -(1 / 2), -(1 / 3),
                -(1 / 4), 0, (1 / 4), (1 / 3), (1 / 2), 1, 2, 3, 4, 5 };
        // set a-d to powerNumb values that increment as the loop progresses
        a = powNums[h];
        b = powNums[i];
        c = powNums[j];
        d = powNums[k];

        // Make a first estimates to compare to other estimates later
        double currEstimate = Math.pow(w, a) * Math.pow(x, b) * Math.pow(y, c)
                * Math.pow(z, d);
        // Find the difference between our current estimate and the true value
        double diff = (currEstimate - userNum);

        while (k < powNums.length) {
            while (j < powNums.length) {
                while (i < powNums.length) {
                    while (h < powNums.length) {
                        //Set a-d to powerNum values as loop progresses
                        a = powNums[h];
                        b = powNums[i];
                        c = powNums[j];
                        d = powNums[k];
                        /*
                         * get a new estimate as loop progresses, if the
                         * difference between userNum and our currEstiamte
                         * decreases that becomes our new best estimate
                         */
                        currEstimate = (Math.pow(w, a) * Math.pow(x, b)
                                * Math.pow(y, c) * Math.pow(z, d)) - userNum;
                        if (Math.abs(currEstimate) < Math.abs(diff)) {
                            diff = currEstimate;
                            // store the powNum values that give us
                            // the smallest difference
                            aVal = powNums[h];
                            bVal = powNums[i];
                            cVal = powNums[j];
                            dVal = powNums[k];

                            //increment loop counters as loop progresses
                        }
                        h++;
                    }
                    i++;
                    h = 0;
                }
                j++;
                i = 0;
            }
            k++;
            j = 0;
        }

        final double error = (diff / userNum) * 100;

        out.println("exponent of a: " + aVal);
        out.println("exponent of b: " + bVal);
        out.println("exponent of c: " + cVal);
        out.println("exponent of d: " + dVal);

        // show user the final number we came up with
        // as well as the error relative to the userNum
        out.println("User num was: " + userNum);
        out.println("Calculation is: " + (diff + userNum));
        out.println("Error is: " + error);

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
        //Declare number we will calculate and sotre it as userNum
        Double userNum;
        //Declare user's 4 meaningful numbers (w,x,y,z)
        Double w, x, y, z;

        out.println("Enter number to be calculated: ");
        String userInput = in.nextLine();
        userNum = getPositiveDouble(userInput, in, out);

        w = getPositiveDoubleNotOne(in, out);

        x = getPositiveDoubleNotOne(in, out);

        y = getPositiveDoubleNotOne(in, out);

        z = getPositiveDoubleNotOne(in, out);

        calculation(in, out, userNum, w, x, y, z);

        // out.println(userNum + "\n" + w + "\n" + x + "\n" + y + "\n" + z);

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
