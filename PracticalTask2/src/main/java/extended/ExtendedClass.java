package extended;

import java.util.Objects;

public class ExtendedClass {
    private byte b;
    private int i;
    private double d;
    private String s;

    // ************************************************************************
    // Getters
    // ************************************************************************

    public byte getB() {
        return b;
    }

    public int getI() {
        return i;
    }

    public double getD() {
        return d;
    }

    public String getS() {
        return s;
    }

    // ************************************************************************
    // Setters
    // ************************************************************************

    public void setB(byte b) {
        this.b = b;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setD(double d) {
        this.d = d;
    }

    public void setS(String s) {
        this.s = s;
    }


    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject == null || getClass() != anObject.getClass()) {
            return false;
        }

        ExtendedClass that = (ExtendedClass) anObject;
        return getB() == that.getB()
                && getI() == that.getI()
                && Double.compare(that.getD(), getD()) == 0
                && Objects.equals(getS(), that.getS());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getB(), getI(), getD(), getS());
    }

    @Override
    public String toString() {
        return "ExtendedClass{" +
                "b = " + b +
                ", i = " + i +
                ", d = " + d +
                ", s = '" + s + '\'' +
                '}';
    }
}
