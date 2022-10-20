import quadratic.equation.solver.QuadraticEquation;

import java.util.*;

/**
 * Class for testing <b>QuadraticEquation class</b>
 * @author Kirill Artamonov
 * @see QuadraticEquation
 */
public class QuadraticEquationTest {
    public static void main(String[] args) {
        try {
            rootsCalculatedCorrectly();
        } catch (TestsNotPassedException e) {
            throw new RuntimeException(e);
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the coefficients of the equation ax^2 + bx + c = 0:");
        System.out.print("a = ");
        double a = sc.nextDouble();
        System.out.print("b = ");
        double b = sc.nextDouble();
        System.out.print("c = ");
        double c = sc.nextDouble();

        System.out.println("Solving the equation: " + a + "x^2 + " + b + "x  + " + c + " = 0" );

        QuadraticEquation eq = new QuadraticEquation(a, b, c);
        eq.solveEquation();
        eq.printSolutions();
    }

    /**
     * Method used for testing solution of the equation.
     * @throws TestsNotPassedException
     */
    public static void rootsCalculatedCorrectly() throws TestsNotPassedException {
        System.out.println("----------------------------------");
        System.out.println("[test] Running tests:");

        int nTests = 3;
        double[][] testData = {{1, 2, 2}, {1, 2, 1}, {1, 3, -4}};
        String[][] testAnswers = {{"-1.0 + 1.0i", "-1.0 - 1.0i"}, {"-1.0", "-1.0"}, {"-4.0", "1.0"}};

        for (int i = 0; i < nTests; i++) {
            QuadraticEquation eq = new QuadraticEquation(testData[i][0], testData[i][1], testData[i][2]);
            eq.solveEquation();
            if (!eq.getX1().equals(testAnswers[i][0]) || !eq.getX2().equals(testAnswers[i][1])) {
                throw new TestsNotPassedException("[err] Test № " + i + ": " + testData[i][0] + "x^2 + "
                        + testData[1][i] + "x  + " + testData[2][i] + " = 0 - is not passed\n" +
                        "Waited roots: x1 = " + testAnswers[i][0] + ", x2 = " + testAnswers[i][1] + ";\n" +
                        "Calculated roots: x1 = " + eq.getX1() + ", x2 = " + eq.getX2() + ".");
            }
            System.out.println("[test] Test №" + (i+1) + " passed.");
        }
        System.out.println("[test] All tests passed correctly.");
        System.out.println("----------------------------------");
    }

    private static class TestsNotPassedException extends Throwable {
        public TestsNotPassedException(String errorMessage) {
            super(errorMessage);
        }
    }
}
