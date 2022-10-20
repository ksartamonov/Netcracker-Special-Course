package adress.persons;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A class that implements the <b>Person[]</b> array with necessary methods.
 * @author Kirill Artamonov
 */
public class PersonsArray{
    private Person[] persons;

    /**
     * @param arraySize Amount of persons
     */
    public PersonsArray(int arraySize) {
        persons = new Person[arraySize];
    }

    public PersonsArray() {
    }

    /**
     * Searches a person by surname
     * @param surname to search for
     * @return a <b>Person[]</b> array of suitable people
     */
    public Person[] searchBySurname(String surname) {
        return castToPersonsArray(Arrays.stream(persons)
                        .filter(person -> person.getSurname().equals(surname)) //filtering array by surname
                .collect(Collectors.toList()));
    }

    /**
     * Searches a person by adress attribute
     * @param addressAttributes String[] array, each String - adress attribute
     * @return a <b>Person[]</b> array of suitable people
     */
    public Person[] searchByAddressAttribute(String[] addressAttributes) {
        List<String> addressAttributesList = List.of(addressAttributes);

        return castToPersonsArray(Arrays.stream(persons)
        .filter(person ->
                        addressAttributesList.contains(person.getAdress().getCountry()) || // checking all possible matches
                            addressAttributesList.contains(person.getAdress().getCity()) ||
                                addressAttributesList.contains(person.getAdress().getStreet()) ||
                                    addressAttributesList.contains(String.valueOf(person.getAdress().getHouseNumber()))
                )
                .collect(Collectors.toList())); // putting all matches to list
    }



    /**
     * Method searches persons born in the period. If the values are entered in the wrong order,
     * swaps them, displaying a warning
     * @param periodBegin begin of the period
     * @param periodEnd end of the period
     * @return a <b>Person[]</b> array of suitable people
     */
    public Person[] searchBornInThePeriod(LocalDate periodBegin, LocalDate periodEnd) {

        if (periodBegin.isAfter(periodEnd)) {   //incorrect order of arguments
            System.err.println("[warning] periodBegin is later than periodEnd, swapping the values.");
            LocalDate tmp = periodBegin;
            periodBegin = periodEnd;
            periodEnd = tmp;
        }
        LocalDate finalPeriodBegin = periodBegin; // to use in lambda expression
        LocalDate finalPeriodEnd = periodEnd;   // to use in lambda expression



        return castToPersonsArray(Arrays.stream(persons)
                .filter(person ->
                        person.getBirthDate().isAfter(finalPeriodBegin) && // Search for fitting persons
                            person.getBirthDate().isBefore(finalPeriodEnd))
                .collect(Collectors.toList())); // put the right people in the list
    }

    /**
     * Method finds the oldest people, if there are several such people, the method will return all of them
     * @return a <b>Person[]</b> array of the oldest people
     */
    public Person[] findOldest() {
        LocalDate minDate = Arrays.stream(persons)
                .map(Person::getBirthDate)
                .min(LocalDate::compareTo).get(); // finding minimal date of birth

        return castToPersonsArray(Arrays.stream(persons) // checking if there are more than one born in that day
                .filter(person ->
                        person.getBirthDate().equals(minDate))
                .collect(Collectors.toList()));
    }


    /**
     * Method finds the youngest people, if there are several such people, the method will return all of them
     * @return a <b>Person[]</b> array of the youngest people
     */
    public Person[] findYoungest() {
        LocalDate maxDate = Arrays.stream(persons)
                .map(Person::getBirthDate)
                .max(LocalDate::compareTo).get(); // finding max date of birth

        return castToPersonsArray(Arrays.stream(persons) // checking if there are more than one born in that day
                .filter(person ->
                        person.getBirthDate().equals(maxDate))
                .collect(Collectors.toList()));
    }

    /** Finds people living on the same street
     * @param address The address
     * @return a <b>Person[]</b> array of people living in the <b>streetName</b>
     */
    public Person[] findNeighbours(Address address) {
        return castToPersonsArray(Arrays.stream(persons)
                .filter(person ->
                        address.equalsTillStreet(person.getAdress()))
                .collect(Collectors.toList()));
    }

    /**
     * Method prints <b>Person[]</b arrays
     * @param printedArray Array  <b>Person[]</b> to print
     */
    public static void print(Person[] printedArray) {
        Arrays.stream(printedArray).forEach(System.out::println);
    }

    /**
     * Method sets printedArray[index] provided <b>Person</b> value
     * @param person Value
     * @param index position to set value
     */
    public void setPerson(Person person, int index) {
        persons[index] = person;
    }

    /**
     * Method gets element of printedArray on <b>index</b> position
     * @param index position to get value
     * @return element on <b>index</b> position
     */
    public Person getPerson(int index) {
        return persons[index];
    }

    /**
     * Service method casting <b>List<Person></b> to array <b>Person[]</b>
     * @param personList List to cast
     * @return Converted Array
     * @see PersonsArray#findOldest()
     * @see PersonsArray#findYoungest()
     */
    private Person[] castToPersonsArray(List<Person> personList) {

        return personList.toArray(new Person[personList.size()]);
    }

    /**
     * Method adds a person by attributes to array <b>persons</b>
     * @param address
     * @param surname
     * @param birthDate
     * @return Person which was inserted
     */
    public Person addPerson(Address address, String surname, LocalDate birthDate) {
        for (int i = 0; i < persons.length; i++) {
            if (persons[i] == null) {
                persons[i] = new Person(address, surname, birthDate);
                return persons[i];
            }
        }

        throw new RuntimeException("Persons array is full!!!");
    }

}
