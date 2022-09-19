package adress.persons;

import java.time.LocalDate;
import java.util.Calendar;

public class Person {
    private Address address;
    private String surname;
    private LocalDate birthDate;

    /**
     * Constructor
     * @see Address
     * @param address Address, described in class <b>Address</b>
     * @param surname
     * @param birthDate
     */
    public Person(Address address, String surname, LocalDate birthDate) {

        if (address == null) throw new RuntimeException("[err] address is null");
        if (surname == null) throw new RuntimeException("[err] surname is null");
        if (birthDate == null) throw new RuntimeException("[err] birthDate is null");

        this.address = address;
        this.surname = surname;
        this.birthDate = birthDate;
    }

    /**
     * Constructor
     */
    public Person() {
    }

    // ************************************************************************
    // Getters
    // ************************************************************************

    public String getSurname() {
        return surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Address getAdress() {
        return address;
    }

    // ************************************************************************
    // Setters
    // ************************************************************************

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setAdress(Address address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "Person{" +
                "address=" + address +
                ", surname='" + surname + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
