package parse.address.attributes;

import java.util.StringTokenizer;

public class Address {
    private String country;
    private String region;
    private String city;
    private String street;
    private String house;
    private String building;
    private String flat;

    // GETTERS
    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }

    public String getBuilding() {
        return building;
    }

    public String getFlat() {
        return flat;
    }

    public void parseAddressAttributesSplit(String address) {
        String[] addressAttributes = address.split(",");

        if (addressAttributes.length != 7) {
            throw new IllegalArgumentException("[exception] String must contain all 7 address attributes.");
        }

        country = addressAttributes[0].trim();
        region  = addressAttributes[1].trim();
        city = addressAttributes[2].trim();
        street = addressAttributes[3].trim();
        house = addressAttributes[4].trim();
        building = addressAttributes[5].trim();
        flat = addressAttributes[6].trim();
    }

    public void parseAddressAttributesStringTokenizer(String address) {
        StringTokenizer st = new StringTokenizer(address, ",.;-");

        if (st.countTokens() != 7) {
            throw new IllegalArgumentException("[exception] String must contain all 7 address attributes.");
        }

        country = st.nextToken().trim();
        region = st.nextToken().trim();
        city = st.nextToken().trim();
        street = st.nextToken().trim();
        house = st.nextToken().trim();
        building = st.nextToken().trim();
        flat = st.nextToken().trim();
    }

    @Override
    public String toString() {
        return "Address{" +
                "  country='" + country + '\'' +
                ", region='" + region + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", building='" + building + '\'' +
                ", flat='" + flat + '\'' +
                '}';
    }
}
