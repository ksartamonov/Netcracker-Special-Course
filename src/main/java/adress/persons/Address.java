package adress.persons;

public class Address {
    private String country;
    private String city;
    private String street;
    private int houseNumber;
    /**
     * Constructor, needs all arguments
     * @param country
     * @param city
     * @param street
     */
    public Address(String country, String city, String street, int houseNumber) {

        if (country == null) throw new RuntimeException("[err] country is null");
        if (city == null) throw new RuntimeException("[err] city is null");
        if (street == null) throw new RuntimeException("[err] street is null");

        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public Address(String country, String city, String street) {

        if (country == null) throw new RuntimeException("[err] country is null");
        if (city == null) throw new RuntimeException("[err] city is null");
        if (street == null) throw new RuntimeException("[err] street is null");

        this.country = country;
        this.city = city;
        this.street = street;
    }

    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", house=" + houseNumber +
                '}';
    }

    // ************************************************************************
    // Getters
    // ************************************************************************
    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    // ************************************************************************
    // Setters
    // ************************************************************************
    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    /**
     * Method checks if 2 addresses match exactly to the street
     * @param obj another address
     * @return true - if 2 addresses match exactly to the street, false - in other cases
     */
    public boolean equalsTillStreet(Object obj) {
        if (!(obj instanceof Address))
            return false;

        Address address = (Address) obj;
        return address.getCountry().equals(this.country) &&
                address.getCity().equals(this.city) &&
                address.getStreet().equals(this.street);
    }
}
