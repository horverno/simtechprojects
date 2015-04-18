package szimtech.puff.input;

/**
 *
 * @author JombY
 * 
 * A megoldási metódusokat tartalmazó enum
 */
public enum SolvMethod {

    RUNGEKUTTA("RungeKutta"), EULER("Euler");

    private final String method;

    SolvMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

}
