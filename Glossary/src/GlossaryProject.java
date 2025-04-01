
import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author David Neal Jr
 *
 */
public final class GlossaryProject {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private GlossaryProject() {
    }

    /**
     * Creates html Header for index file.
     *
     * @param index
     *            The html file the header is being printed to
     */

    private static void outputHeader(SimpleWriter index) {
        /*
         * Print the beginning statements for an html doc
         */
        index.println("<!DOCTYPE html>");
        index.println("<html>");
        index.println("<head>");
        index.println("<title>Glossary</title>");
        index.println("</head>");
        index.println("<body>");
        index.println("<h1>Glossary</h1>");
        index.println("<h3>Terms</h3>");

    }

    /**
     * Creates end of html for index file.
     *
     * @param index
     *            The html file the header is being printed to
     */
    private static void outputClose(SimpleWriter index) {
        /*
         * Print the beginning statements for an html doc
         */
        index.println("</ul>");
        index.println("</body>");
        index.println("</html>");
    }

    /**
     * creates links using the map to read each term.
     *
     * @param output
     *            prints to index
     * @param termAndDefMap
     *            map of Strings <term, def>
     * @param listOfTerms
     *            queue of our terms
     */
    private static void htmlLinks(String output,
            Map<String, String> termAndDefMap, Queue<String> listOfTerms) {

        //Create temp queue so we dont change listOfTerms
        Queue<String> temp = new Queue1L<>();
        //goes through every term's definitions
        while (listOfTerms.length() > 0) {
            String term = listOfTerms.dequeue();
            String definition = termAndDefMap.value(term);
            temp.enqueue(term);
            SimpleWriter out = new SimpleWriter1L(
                    output + "/" + term + ".html");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>" + term + "</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>");
            out.println("<b>");
            out.println("<i>");
            out.println("<font color=\"red\">" + term + "</font>");
            out.println("</i>");
            out.println("</b>");
            out.println("</h2>");
            out.println("<blockquote>" + definition + "</blockquote>");
            out.println("<hr>");
            out.println(
                    "<p> Return to <a href=\"https://web.cse.ohio-state.edu/"
                            + "software/2221/web-sw1/assignments/projects/glossary/data/"
                            + "index.html\">index</a>.</p>");
            out.println("</body>");
            out.println("</html>");
            out.close();
        }

        listOfTerms.transferFrom(temp);
    }

    /**
     * Sorts our queue alphabetically.
     *
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            int compare = s1.compareTo(s2);
            return compare;
        }
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     * text[position, position + |nextWordOrSeparator|) and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     * entries(nextWordOrSeparator) intersection separators = {} and
     * (position + |nextWordOrSeparator| = |text| or
     * entries(text[position, position + |nextWordOrSeparator| + 1))
     * intersection separators /= {})
     * else
     * entries(nextWordOrSeparator) is subset of separators and
     * (position + |nextWordOrSeparator| = |text| or
     * entries(text[position, position + |nextWordOrSeparator| + 1))
     * is not subset of separators)
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        String str = ("");
        for (char c : text.substring(0, text.length()).toCharArray()) {
            boolean done = false;
            while (!done) {
                if (!separators.contains(c)) {
                    str += c;
                    done = true;

                }
            }
        }
        return str;
    }

    /**
     * Reads input file, and puts the terms and definitions into our map.
     *
     * @param termAndDefMap
     *            Map of the terms and definitions
     * @param in
     *            the text file being read
     * @return listOfTerms a queue of all terms
     *
     */
    private static Queue<String> getTermsAndDefs(
            Map<String, String> termAndDefMap, SimpleReader in) {
        Queue<String> listOfTerms = new Queue1L<>();

        //read through the file and use the isBlank() to read black spaces
        //this will tell us what is a term and what is a def

        while (!in.atEOS()) {
            String term = in.nextLine();
            String def = in.nextLine();
            String temp = " ";

            //while not being at the end of the string, if the next line is empty
            //the current temp is a definition

            while (!in.atEOS() && temp.length() > 0) {
                temp = in.nextLine();

                if (temp.length() > 0) {
                    def += temp;
                }
            }

            //add terms and definition to queue
            termAndDefMap.add(term, def);
            listOfTerms.enqueue(term);
        }
        return listOfTerms;
    }

    /**
     * Creates a list of our terms.
     *
     * @param index
     *            file we're outputting to
     * @param terms
     *            terms going in list
     */
    public static void createLists(SimpleWriter index, Queue<String> terms) {
        /*
         * Initializes the counter and starts the list.
         */

        index.println("<ul>");

        /*
         * For each term add it to the list
         */
        for (int i = 0; i < terms.length(); i++) {
            String term = terms.front();

            index.println(
                    "<li><a href=" + term + ".html>" + term + "</a></li>");
            terms.rotate(1);
        }
    }

    /**
     * Gets a filename from the user that will be read. Output files into folder
     * given by the user Main file is an idex file containing a list of multiple
     * htmls.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader userIN = new SimpleReader1L();
        SimpleWriter userOut = new SimpleWriter1L();

        userOut.print("Enter input file: ");
        String input = userIN.nextLine();
        userOut.print("Enter folder where output files will be saved: ");
        String output = userIN.nextLine();

        //crate blank map and queue for our
        //terms and Defininitions and list of terms respectively
        SimpleReader in = new SimpleReader1L(input);
        SimpleWriter out = new SimpleWriter1L(output + "/index.html");

        Map<String, String> termsAndDefsMap = new Map1L<>();
        Queue<String> listOfTerms = new Queue1L<>();

        //creates list of terms and appends the
        //terms and the definitions to termsAndDefsMap
        listOfTerms.append(getTermsAndDefs(termsAndDefsMap, in));

        //organize terms in alphabetical order
        Comparator<String> order = new StringLT();
        listOfTerms.sort(order);
        htmlLinks(output, termsAndDefsMap, listOfTerms);

        //print index header
        outputHeader(out);
        //creates list of terms
        createLists(out, listOfTerms);
        //print index end
        outputClose(out);

        //Close streams
        userIN.close();
        userOut.close();
        in.close();
        out.close();
    }
}
