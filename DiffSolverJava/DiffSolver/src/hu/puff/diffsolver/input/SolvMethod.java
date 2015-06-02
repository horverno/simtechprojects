package hu.puff.diffsolver.input;

/**
 *
 * @author JombY
 * 
 * A megoldási metódusokat tartalmazó enum
 */
public enum SolvMethod {

    MIDPOINT("MidPoint"), EULER("Euler"), RUNGEKUTTA3("RungeKutta3"),RUNGEKUTTA4("RungeKutta4");

    private final String method;

    SolvMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

}
