package task1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Person {
    private LocalDate birthDate;

    /**
     * Constructor receiving bith date in <b>LocalDate</b> format.
     * @param birthDate
     */
    public Person(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Constructors with no arguments
     */
    public Person() {
    }

    /** GETTER */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Setter of a person's Birth Date
     * @param birthDate date in <b>LocalDate</b> format
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Method to format date according to the specified template (Patterns for Formatting and Parsing)
     * @param format Template to format by
     * @return string representation of birthDate
     * @see DateTimeFormatter
     */
    public String getDateInFormat(String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return birthDate.format(formatter);
    }

    @Override
    public String toString() {
        return birthDate.toString();
    }
}
