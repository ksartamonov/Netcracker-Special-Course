package telephone.numbers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TelephoneNumber {
    private final String code;
    private final String sub_number; // subscriber's number

    public TelephoneNumber(String number) {
        Pattern numberPattern = Pattern.compile("(\\+(\\d+)|8)((\\d){10})");
        Matcher numberMatcher = numberPattern.matcher(number);

        if (numberMatcher.matches()) { // if pattern is found
            if (numberMatcher.group(2) != null) { // if number contains regioncode
                code = numberMatcher.group(2);
            } else {
                code = "7"; // if Russian number
            }
            sub_number = numberMatcher.group(3);
        } else {
            throw new IllegalArgumentException("Incorrect Phone Number");
        }
    }

    @Override
    public String toString() {
        StringBuilder strB =  new StringBuilder("+");
        return new String(strB.append(code)
                .append(sub_number.substring(0, 3))
                .append("-")
                .append(sub_number.substring(3, 6))
                .append("-")
                .append(sub_number.substring(6)));
    }
}
