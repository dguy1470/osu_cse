import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program that reads an input file given by the user, and outputs to a file
 * (also given by the user) each individual word and how frequently the word
 * occurs. Words with capitalization will be counted as separate occurrences
 * from their lower case counterparts but with be sorted next to them
 *
 * @author David Neal JR.
 *
 */
public final class WordCount {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private WordCount() {
    }

    /**
     * Compares Strings in lexicographical order.
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            /**
             * adding .toLowerCase() here makes it so Uppercase letters that are
             * sorted end up in alphabetical order along with lower case words
             * instead of be sorted separately at the top
             */
            return o1.toLowerCase().compareTo(o2.toLowerCase());
        }
    }

    /**
     * Creates Set of separators.
     *
     * @param s
     *            The set of separators
     */
    public static void seperatorsList(Set<Character> s) {
        //add separators to the set
        s.add(' ');
        s.add((','));
        s.add(('.'));
        s.add(('!'));
        s.add(('?'));
        s.add((';'));
        s.add((':'));
        s.add(('-'));
        s.add(('/'));
    }

    /**
     * Creates html Header for file.
     *
     * @param output
     *            The html file the header is being printed to
     */
    private static void outputHeader(SimpleWriter output) {
        //Print the beginning statements for an html doc
        output.println("<!DOCTYPE html>");
        output.println("<html>");
        output.println("<head>");
        output.println("<title>WordCounter</title>");
        output.println("</head>");
        output.println("<body>");
        output.println("<h2>WordCounter</h2>");
        output.println("<table border=\"2\">");
        output.println("<tr>");
        output.println("<th>Words</th>");
        output.println("<th>Counts</th>");
        output.println("</tr>");

        //Loop to add to table

    }

    /**
     * Creates footer of html for file.
     *
     * @param output
     *            The html file the footer is being printed to
     */
    private static void outputClose(SimpleWriter output) {
        //Print the beginning statements for an html doc
        output.println("</table>");
        output.println("</body>");
        output.println("</html>");
    }

    /**
     * Creates end of html for index file.
     *
     * @param map
     *            The map storing they words, and their counts
     * @param output
     *            The html file the header is being printed to
     */
    private static void outputHTML(Map<String, Integer> map,
            SimpleWriter output) {

        //Implement comparator to sort words once they're in a queue
        Comparator<String> orderWords = new StringLT();
        //Queue words will temporarily be stored in
        Queue<String> words = new Queue1L<String>();
        //output the header of the html file
        outputHeader(output);
        //for each word in pair, add to queue;
        for (Pair<String, Integer> tempPair : map) {
            words.enqueue(tempPair.key());
        }

        /**
         * sort words in alphabetical order including uppercase words upper case
         * words are considered individual words but can be found next to their
         * lowercase counter parts
         */
        words.sort(orderWords);

        //for each word in the queue, print it to the html table with its count
        for (String str : words) {
            if (map.hasKey(str)) {
                //html table
                output.println("<tr>");
                output.println("<td>" + str + "</td>");
                output.println("<td>" + map.value(str) + "</td>");
                output.println("</tr>");
            }
        }
        //output footer after table
        outputClose(output);
    }

    /**
     * Reads through file and finds the next word or separator.
     *
     * @param text
     *            the file we are finding the word or separator in
     * @param pos
     *            starting index
     * @param separators
     *            Our Set of separators from separatorsList method
     * @return str The first word or separator found in the text
     */
    private static String nextWordOrSeperator(String text, int pos,
            Set<Character> separators) {

        String str = "" + text.charAt(pos);
        int i = 1; //increment to help increase position as needed
        //check if the starting position is a separator
        boolean isSeparator = separators.contains(text.charAt(pos));
        //tells us if the next char is a separator
        boolean nextChar;
        //if still within string continue
        if (pos + i < text.length()) {
            nextChar = separators.contains(text.charAt(pos + i));
        } else {
            //If String does not go on
            nextChar = !isSeparator;
        }

        //while the next char is not a separator and we are still within the string
        while (nextChar == isSeparator) {
            //continue adding to the word
            str = str + text.charAt(pos + i);
            i++;

            if (pos + i < text.length()) {
                nextChar = separators.contains(text.charAt(pos + i));
            } else {
                nextChar = !isSeparator;
            }
        }
        return str;
    }

    /**
     * Reads through input file and stores Words and their count in a Map.
     *
     * @param file
     *            The file being read
     * @param output
     *            the file that will be printed to after the Map is finished
     */
    private static void wordMap(SimpleReader file, SimpleWriter output) {

        String line;
        int pos;
        String token;

        //Create Set of Separators from the separatorsList method
        Set<Character> seps = new Set1L<Character>();
        seperatorsList(seps);

        //the map that will store each word and their count
        Map<String, Integer> wMap = new Map1L<String, Integer>();

        //While not at the end of the file
        while (!file.atEOS()) {

            //set position to 0 as we begin reading a new line
            line = file.nextLine();
            pos = 0;

            //while still reading a line look for the next word or separator
            while (pos < line.length()) {
                token = nextWordOrSeperator(line, pos, seps);

                pos = pos + token.length();

                //if the token is a word or separator
                if (!seps.contains(token.charAt(0))) {

                    //if the map already has the token stored, increase it's count
                    if (wMap.hasKey(token)) {
                        wMap.replaceValue(token, wMap.value(token) + 1);
                    } else {
                        wMap.add(token, 1);
                    }

                }
            }

        }
        //finally output the html
        outputHTML(wMap, output);
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

        out.print("Enter input file: ");
        SimpleReader inputFile = new SimpleReader1L(in.nextLine());

        out.print("Enter output file: ");
        SimpleWriter outputFile = new SimpleWriter1L(in.nextLine());

        wordMap(inputFile, outputFile);

        /*
         * Close input and output streams
         */

        inputFile.close();
        outputFile.close();
        in.close();
        out.close();

    }

}
