package quadratic.equation.solver;

/**
 * A class that implements the quadratic equation
 * @author Kirill Artamonov
 */
public class QuadraticEquation {

    /** Accuracy of calculations */
    private static final double PRECISION = 1e-6;

    /** Coefficients of the equation Ax^2 + Bx + C = 0 */
    private final double coeffA;
    private final double coeffB;
    private final double coeffC;

    /** Solutions of the equation */
    private String x1, x2;

    // ************************************************************************
    // Getters
    // ************************************************************************


    public double getCoeffA() {
        return coeffA;
    }

    public double getCoeffB() {
        return coeffB;
    }

    public double getCoeffC() {
        return coeffC;
    }

    public String getX1() {
        return x1;
    }

    public String getX2() {
        return x2;
    }

    // ************************************************************************

    /**
     * Constructor, All coefficients pf equation are obligatory
     * @param coeffA coefficient A
     * @param coeffB coefficient B
     * @param coeffC coefficient C
     */
    public QuadraticEquation(double coeffA, double coeffB, double coeffC) {
        this.coeffA = coeffA;
        this.coeffB = coeffB;
        this.coeffC = coeffC;
    }

    /**
     * The function of calculating the roots of a quadratic equation (real or imaginary).
     * Writes roots to fields <b>x1</b>, <b>x2</b>.
     */
    public void solveEquation() {
        /** Inner class calculating Discriminant */
        class Discriminant {
            private double value;
            public Discriminant() {
                value = calculateValue();
            }
            private double calculateValue() {
                return coeffB * coeffB - 4 * coeffA * coeffC;
            }
        }

        Discriminant d = new Discriminant(); // value is calculated when constructing
        if (d.value > PRECISION) { // real roots
            x1 = String.valueOf((-coeffB - Math.sqrt(d.value)) / (2 * coeffA));
            x2 = String.valueOf((-coeffB + Math.sqrt(d.value)) / (2 * coeffA));
        }
        else if (d.value >= -PRECISION && d.value <= PRECISION) { // one root, second condition is not necessary
            x1 = String.valueOf((-coeffB) / (2 * coeffA));
            x2 = x1;
        }
        else if (d.value < PRECISION) { // complex roots
            double rePart = ((-coeffB) / (2 * coeffA)); // real part of both roots
            double imPart = Math.sqrt((-1) * d.value) / (2 * coeffA); // imaginary multiplier
            x1 = rePart + " + " + imPart + "i";
            x2 = rePart + " - " + imPart + "i";
        }
    }

    /**
     * The function that prints the solution of the equation.
     */
    public void printSolutions() {
        System.out.println("x1 = " + x1);
        if (!x1.equals(x2)) {
            System.out.println("x2 = " + x2);
        }
    }
}



