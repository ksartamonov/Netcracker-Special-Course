import parse.address.attributes.Address;

public class AddressTest {
    public static void main(String[] args) {
        Address adr1 = new Address();
        adr1.parseAddressAttributesSplit("Country1, Region1, City1, Street1, House1, Building1, Flat1");
        System.out.println("Address1: " + adr1);
        Address adr2 = new Address();
        adr2.parseAddressAttributesStringTokenizer("Country2; Region2; City2; Street2; House2; Building2; Flat2");
        System.out.println("Address2: " + adr2);
    }
}
