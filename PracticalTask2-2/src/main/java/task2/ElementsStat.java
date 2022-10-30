package task2;

import java.util.List;

/**
 * Class working with statistics of different number types required in the task.
 * @param <T> Type to work with.
 */
public class ElementsStat<T extends Number> {
    private final int quantity; // amount of numbers
    private final Object secondHalfAverage; // average element of second half
    private final Object thirdQuarterAverage; // average element of the third quarter

    /**
     * Constructor initializing all values.
     * @param elements list of elements.
     */
    public ElementsStat(List<T> elements) {
        this.quantity = elements.size();

        T sample = elements.get(0);
        if(sample instanceof Byte)
        {
            this.secondHalfAverage = getAverageByte((List<Byte>) elements.subList((this.quantity / 2), this.quantity - 1));
            this.thirdQuarterAverage = getAverageByte((List<Byte>)elements.subList((this.quantity / 2), this.quantity * 3 / 4));
        }
        else if (sample instanceof Integer)
        {
            this.secondHalfAverage = getAverageInteger((List<Integer>) elements.subList((this.quantity / 2), this.quantity - 1));
            this.thirdQuarterAverage = getAverageInteger((List<Integer>)elements.subList((this.quantity / 2), this.quantity * 3 / 4));
        }
        else if (sample instanceof Float)
        {
            this.secondHalfAverage = getAverageFloat((List<Float>) elements.subList((this.quantity / 2), this.quantity - 1));
            this.thirdQuarterAverage = getAverageFloat((List<Float>)elements.subList((this.quantity / 2), this.quantity * 3 / 4));
        }

        else
        {
            throw new IllegalStateException("Constructor must be initialized with either of Byte, Integer or Float list");
        }

    }

    /**
     * Method finding average element in <b>Byte</b> list.
     * @param list <b>Byte</b> list
     * @return average element.
     */
    private Integer getAverageByte(List<Byte> list) {
        Integer sum = 0;

        for(Byte e : list) {
            sum += e;
        }

        return sum / list.size();
    }

    /**
     * Method finding average element in <b>Byte</b> list.
     * @param list <b>Byte</b> list
     * @return average element.
     */
    private Integer getAverageInteger(List<Integer> list) {
        Integer sum = 0;

        for(Integer e : list) {
            sum += e;
        }

        return sum / list.size();
    }

    /**
     * Method finding average element in <b>Float</b> list.
     * @param list <b>Float</b> list
     * @return average element.
     */
    private Float getAverageFloat(List<Float> list) {
        Float sum = (float) 0;

        for(Float e : list) {
            sum += e;
        }

        return sum / list.size();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Numbers amount: ")
                .append(this.quantity)
                .append('\n');
        sb.append("Second Half average: ")
                .append(this.secondHalfAverage)
                .append('\n');
        sb.append("Third quarter average: ")
                .append(this.thirdQuarterAverage)
                .append('\n');
        return sb.toString();
    }
}
