import task1.Person;

import java.time.LocalDate;

public class PersonTest {
    public static void main(String[] args) {
        Person p1 = new Person(LocalDate.of(2002,11, 30));
        System.out.println(p1.toString());
        System.out.println(p1.getDateInFormat("dd LLLL yyyy"));
        System.out.println(p1.getDateInFormat("d-MM-yy"));

        String str1 = "sdas";
        String str2 = "ewfe";

        str1+=str2;

    }
}
