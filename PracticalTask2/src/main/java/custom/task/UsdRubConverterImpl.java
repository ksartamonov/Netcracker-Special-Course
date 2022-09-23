package custom.task;

/**
 * Class implementing UsdRubConverter interface.
 * @see  UsdRubConverter
 * @author Kirill Artamonov
 */
public class UsdRubConverterImpl implements UsdRubConverter {
    private static double rate = 60;

    public void setRate(double rate) {
        this.rate = rate;
    }
    public double convertRubToUsd(double rub) {
        return rub / rate;
    }
    public double convertUsdToRub(double usd) {
        return usd * rate;
    }
}
