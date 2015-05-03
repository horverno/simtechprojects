package hu.puff.diffsolver.input;

/**
 *
 * @author JombY
 * 
 * A megoldási metódusokat tartalmazó enum
 */
public enum SolvMethod {

    RUNGEKUTTA("RungeKutta"), RUNGEKUTTA4("RungeKutta4"), EULER("Euler"), IMPLICITEULER("Euler implicit");

    private final String method;

    SolvMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

}
