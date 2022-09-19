

import adress.persons.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Class fot testing <b>PersonArray</b> class
 * @author Kirill Artamonov
 * @see PersonsArray
 */
public class PersonsArrayTest {
    public static void main(String[] args) {
        PersonsArray pA = new PersonsArray(4);
        Person person1 = pA.addPerson(new Address("Russia",
                "Moscow", "Street1", 1),
                "Ivanov", LocalDate.of(2002, Calendar.OCTOBER, 13));

        Person person2 = pA.addPerson(new Address("US",
                "NY", "Street3", 2),
                "Bieber", LocalDate.of(2003, Calendar.JULY, 18));

        Person person3 = pA.addPerson(new Address("Germany",
                "Leipzig", "Street3", 3), "Muller",
                LocalDate.of(2006, Calendar.MAY, 5));

        Person person4 = pA.addPerson(new Address("Germany",
                "Leipzig", "Street3", 8), "Diesel",
                LocalDate.of(1998, Calendar.DECEMBER, 17));

//        PersonsArray.print(pA.findYoungest());
//        PersonsArray.print(pA.findOldest());
//        PersonsArray.print(pA.searchBornInThePeriod(LocalDate.of(1997, Calendar.MAY, 5), LocalDate.of(2000, Calendar.MAY, 5)));
//        PersonsArray.print(pA.findNeighbours(new Address("Germany", "Leipzig", "Street3")));
//        PersonsArray.print(pA.searchByAddressAttribute(new String[]{"US", "NY"}));
    }
}
