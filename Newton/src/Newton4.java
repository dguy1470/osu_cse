import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class Newton4 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton4() {
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
        /*
         * Initialize userNum variable to hold the users input and ans variable
         * that stores the return double from sqrt method
         */

        double userNum;
        double ans;
        double error;
        /*
         * Ask user for a number to find the sqrt of If the number is negative
         * the program will end At the end of each loop the user will be
         * prompted to enter a new number A negative number will end the program
         */

        out.println(
                "Enter a number (A negative number will end the program): ");
        userNum = in.nextDouble();

        out.println("Enter error value: ");
        error = in.nextDouble();

        while (userNum >= 0) {

            ans = sqrt(userNum, error);

            out.println("Square root of " + userNum + " is " + ans);

            out.println(
                    "Enter a new number (A negative number will end the program): ");
            userNum = in.nextDouble();

        }
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     *
     * @param x
     *            positive number to compute square root of
     * @return estimate of square root
     *
     */
    private static double sqrt(double x, double err) {
        /*
         * Run x through our abs function to get the absolute value Assume sqrt
         * of x is x to start (assign currGuess to x) initialize error just to
         * make it easier later
         *
         * This method isn't need now as the sqrt method will end if a number is
         * negative
         */
        x = abs(x);
        double currGuess = x;

        /*
         * while our guess is above error squared keep using newton iteration to
         * guess the sqrt
         */
        while (((currGuess * currGuess) - x) > (err * err)) {
            /*
             * we are not worried about division by zero here because if x is 0
             * we will never reach this block as the while loop will already be
             * skipped over ((0*0)-0) is 0 which is always less than our error
             */
            currGuess = ((currGuess + (x / currGuess)) / 2);

            /*
             * test code to watch currGuess change and ensure we do Newton
             * iteration correctly
             *
             * SimpleWriter out = new SimpleWriter1L(); out.println(currGuess);
             * out.close();
             */
        }
        return currGuess;

    }

    /**
     * Makes sure we have the absolute value .
     *
     * @param num
     *            number we are checking to ensure its the abs val
     * @return absolute value of num
     *
     */
    private static double abs(double num) {

        if (num < 0) {
            num = (num * (-1));
        } else {
            num = num;
        }
        return num;
    }

}
