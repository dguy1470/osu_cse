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
public final class Newton1 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton1() {
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
         * Initialize play variable to tell the loop when to stop running
         * userNum variable to hold the users input and ans variable that stores
         * the return double from sqrt method
         */
        String play = ("y");
        double userNum;
        double ans;
        /*
         * At the end of the code the user will be asked if they'd like to run
         * it again if the user enters y the code will run again. Anything else
         * and the loop breaks and the code stops
         */
        while (play.equals("y")) {

            out.println("Enter a number: ");
            userNum = in.nextDouble();

            ans = sqrt(userNum);

            out.println("Square root of " + userNum + " is " + ans);

            out.println("Run again? (y/n): ");
            play = in.nextLine();
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
    private static double sqrt(double x) {
        /*
         * Run x through our abs function to get the absolute value Assume sqrt
         * of x is x to start (assign currGuess to x) initialize error just to
         * make it easier later
         */
        x = abs(x);
        double currGuess = x;
        double err = .01 / 100;

        /*
         * while our guess is above error squared keep using newton iteration to
         * guess the sqrt
         */
        while (((currGuess * currGuess) - x) > (err * err)) {
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
