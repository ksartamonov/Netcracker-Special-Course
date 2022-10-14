import org.jetbrains.annotations.NotNull;
import task3.StringsAnalyzer;

import java.util.*;

public class StringAnalyzerTest {
    public static void main(String[] args) {
        StringsAnalyzer sa = new StringsAnalyzer();
//        String str1 = "That's one small step for a man, a giant leap for mankind.";
//        String str2 = "Genius is one percent inspiration and ninety-nine percent perspiration.";

        String str1 = "A1B";
        String str2 = "BC2";
        sa.setCharsInBoth(str1, str2);
        sa.setCharsOnlyInFirst(str1, str2);
        sa.setCharsAtLeastInOne(str1, str2);


        System.out.println("Printing in default order:");
        printInDefaultOrder(List.of(sa.getCharsInBoth(), sa.getCharsOnlyInFirst(), sa.getCharsAtLeastInOne()));
        System.out.println("Printing in reverse order:");
        printInReversedOrder(List.of(sa.getCharsInBoth(), sa.getCharsOnlyInFirst(), sa.getCharsAtLeastInOne()));
        System.out.println("Printing in ascending order of the cyclic" +
                "shift to the left by n digits of the hash function of the symbol.");
        printInCyclicLeftShiftOrder(List.of(sa.getCharsInBoth(), sa.getCharsOnlyInFirst(), sa.getCharsAtLeastInOne()), 13);
    }

    /**
     * Method printing symbols in default order
     * @param sets sets to print
     */
    static void printInDefaultOrder(@NotNull List<Set<Character>> sets) {
        sets.forEach(System.out::println);
    }

    /**
     * Method printing symbols in reversed order
     * @param sets sets to print
     */
    static void printInReversedOrder(@NotNull List<Set<Character>> sets) {
        sets.forEach(characters -> {
            List<Character> setList = new ArrayList<Character>(characters);
            Collections.reverse(setList);
            System.out.println(setList);
        });
    }

    /**
     * Method prints symbols in ascending order circular shift left by <b>n</b> bits of the character hash-function
     * @param sets sets to print
     * @param n bits amount to shift
     */
    static void printInCyclicLeftShiftOrder(@NotNull List<Set<Character>> sets, int n) {
        sets.forEach(characters -> {
            List<Character> setList = new ArrayList<Character>(characters);
            Collections.sort(setList, Comparator.comparingInt(o -> leftCyclicShift(o.hashCode(), n)));
            System.out.println(setList);
        });
    }

    /**
     * Function makes circular shift left by <b>n</b> bits
     * @param value value to shift
     * @param n bits amount to shift left
     * @return circular shift left by <b>n</b> bits of the number
     */
    private static int leftCyclicShift(int value, int n) {
        return (value << (n % 32)) | (value >>> ((n % 32) - 32));
    }
}
