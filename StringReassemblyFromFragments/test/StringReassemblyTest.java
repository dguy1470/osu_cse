import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;
import components.set.Set2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class StringReassemblyTest {

    /*
     * Tests for overlap
     */

    @Test
    public void overlap_gataca() {
        String str1 = ("gataca");
        String str2 = ("tacagotit");
        int overlap = StringReassembly.overlap(str1, str2);
        assertEquals(4, overlap);
    }

    @Test
    public void overlap_none() {
        String str1 = ("bruhmoment1");
        String str2 = ("zzzzzzzzzzz2");
        int overlap = StringReassembly.overlap(str1, str2);
        assertEquals(0, overlap);
    }

    @Test
    public void overlap_nums() {
        String str1 = ("123456");
        String str2 = ("567890");
        int overlap = StringReassembly.overlap(str1, str2);
        assertEquals(2, overlap);
    }

    /*
     * Tests for combination
     */
    @Test
    public void combination_Ohio() {
        String str1 = ("Ohi");
        String str2 = ("io!");
        int overlap = 1;
        String combo = StringReassembly.combination(str1, str2, overlap);
        assertEquals("Ohio!", combo);
    }

    @Test
    public void combination_Cool_Beans() {
        String str1 = ("Cool ");
        String str2 = ("Beans");
        int overlap = 0;
        String combo = StringReassembly.combination(str1, str2, overlap);
        assertEquals("Cool Beans", combo);
    }

    @Test
    public void combination_nums() {
        String str1 = ("123456");
        String str2 = ("456789");
        int overlap = 3;
        String combo = StringReassembly.combination(str1, str2, overlap);
        assertEquals("123456789", combo);
    }

    /*
     * Tests for addToSetAvoidingSubstrings
     */
    @Test
    public void addToSetAvoidingSubstringst1() {
        Set<String> actual = new Set2<>();
        actual.add("fire");
        actual.add("water");
        actual.add("tric");
        String str = ("electric");

        Set<String> expected = new Set2<>();
        expected.add("fire");
        expected.add("water");
        expected.add("electric");
        StringReassembly.addToSetAvoidingSubstrings(actual, str);
        assertEquals(expected, actual);
    }

    @Test
    public void addToSetAvoidingSubstringst2() {
        Set<String> actual = new Set2<>();
        actual.add("red");
        actual.add("gold");
        actual.add("purp");
        String str = ("purple");
        Set<String> expected = new Set2<>();
        expected.add("red");
        expected.add("gold");
        expected.add("purple");
        StringReassembly.addToSetAvoidingSubstrings(actual, str);
        assertEquals(expected, actual);
    }

    /*
     * Tests for linesFromInput
     */

    public void linesFromInputt1() {
        SimpleReader in = new SimpleReader1L("test12.txt");
        SimpleWriter out = new SimpleWriter1L();

        Set<String> actual = new Set2<String>();
        Set<String> expected = new Set2<String>();

        actual = StringReassembly.linesFromInput(in);

        expected.add("Bucks -- Beat");
        expected.add("Go Bucks");
        expected.add("o Bucks -- B");
        expected.add("Beat Mich");
        expected.add("Michigan~");

        out.println(actual.toString());
        out.println(expected.toString());

        out.close();

        assertEquals(expected, actual);
    }

    /*
     * Tests for printWithLineSeparators
     */
    @Test
    public void printWithLineSeparatorst1() {
        String original = ("There's~a~lot~of~tests");
        SimpleWriter out = new SimpleWriter1L("printWithLineSeparatorst1.txt");
        StringReassembly.printWithLineSeparators(original, out);
        SimpleReader in = new SimpleReader1L("printWithLineSeparatorst1.txt");

        assertEquals("There's", in.nextLine());
        assertEquals("a", in.nextLine());
        assertEquals("lot", in.nextLine());
        assertEquals("of", in.nextLine());
        assertEquals("tests", in.nextLine());
        out.close();
        in.close();
    }

    @Test
    public void printWithLineSeparatorst2() {
        String original = ("chicka-chicka~boom-boom~123");
        SimpleWriter out = new SimpleWriter1L("printWithLineSeparatorst2.txt");
        StringReassembly.printWithLineSeparators(original, out);
        SimpleReader in = new SimpleReader1L("printWithLineSeparatorst2.txt");

        assertEquals("chicka-chicka", in.nextLine());
        assertEquals("boom-boom", in.nextLine());
        assertEquals("123", in.nextLine());

        out.close();
        in.close();
    }
}
