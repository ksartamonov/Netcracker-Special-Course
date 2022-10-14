package task3;

import java.util.*;

public class StringsAnalyzer {

    private final Set<Character> charsInBoth;
    private final Set<Character> charsOnlyInFirst;
    private final Set<Character> charsAtLeastInOne;

    public StringsAnalyzer() {
        charsInBoth = new TreeSet<>();
        charsOnlyInFirst = new TreeSet<>();
        charsAtLeastInOne = new TreeSet<>();
    }



    /** GETTERS */
    public Set<Character> getCharsInBoth() {
        return charsInBoth;
    }

    public Set<Character> getCharsOnlyInFirst() {
        return charsOnlyInFirst;
    }

    public Set<Character> getCharsAtLeastInOne() {
        return charsAtLeastInOne;
    }

    /**
     * Functions sets symbols presented in both strings and adds them to set<\b>charsInBoth</\b>
     * @param str1
     * @param str2
     */
    public void setCharsInBoth(String str1, String str2) {
        for (char c : str1.toCharArray()) {
            if (str2.contains(String.valueOf(c))) {
                charsInBoth.add(c);
            }
        }
    }


    /**
     * Functions sets symbols presented only in first string and adds them to set<\b>harsOnlyInFirst</\b>
     * @param str1
     * @param str2
     */
    public void setCharsOnlyInFirst(String str1, String str2) {
        for (char c : str1.toCharArray()) {
            if (!str2.contains(String.valueOf(c))) {
                charsOnlyInFirst.add(c);
            }
        }
    }

    /**
     * Functions sets symbols presented at least in one of the strings and adds them to set <b>charsAtLeastInOne</b>
     * @param str1
     * @param str2
     */
    public void setCharsAtLeastInOne(String str1, String str2) {
        for (char c : str1.toCharArray()) {
            charsAtLeastInOne.add(c);
        }
        for (char c : str2.toCharArray()) {
            charsAtLeastInOne.add(c);
        }
    }

}
