import custom.task.*;
public class UsbRubConverterTest {
    public static void main(String[] args) {
        UsdRubConverter conv = new UsdRubConverterImpl();
        System.out.println(conv.convertRubToUsd(600));
    }
}
