import telephone.numbers.TelephoneNumber;

public class TelephoneNumberTest {
    public static void main(String[] args) {
        TelephoneNumber ph1= new TelephoneNumber("+79175655655");
        TelephoneNumber ph2= new TelephoneNumber("+104289652211");
        TelephoneNumber ph3= new TelephoneNumber("89175655655");
        System.out.println(ph1);
        System.out.println(ph2);
        System.out.println(ph3);
    }
}
