package szimtech.puff.input;

/**
 *
 * @author JombY
 *
 * Egyenleteket, kezdő értékeket, koordináta rendszer beosztás értékeit, és
 * léptékszámot tároló entity
 */
public class InputData {

    private String equations;
    private Long x0, xmax, incr, ymin, ymax;
    private SolvMethod method;

    public InputData(String equations, Long x0, Long xmax, Long incr, Long ymin, Long ymax, SolvMethod method) {
        // egyenlet(ek), kezdő érték(ek) - pl.: y1+2.5,0.5 - vessző után van az egyenlet kezdő értéke
        this.equations = equations;
        // x tengely kezdő értéke
        this.x0 = x0;
        // x tengely vég értéke
        this.xmax = xmax;
        // léptékszám
        this.incr = incr;
        // y tengely alsó értéke
        this.ymin = ymin;
        // y tengely felső értéke
        this.ymax = ymax;
        // megoldás metódusa
        this.method = method;
    }

    public String getEquations() {
        return equations;
    }

    public void setEquations(String equations) {
        this.equations = equations;
    }

    public Long getX0() {
        return x0;
    }

    public void setX0(Long x0) {
        this.x0 = x0;
    }

    public Long getXmax() {
        return xmax;
    }

    public void setXmax(Long xmax) {
        this.xmax = xmax;
    }

    public Long getIncr() {
        return incr;
    }

    public void setIncr(Long incr) {
        this.incr = incr;
    }

    public Long getYmin() {
        return ymin;
    }

    public void setYmin(Long ymin) {
        this.ymin = ymin;
    }

    public Long getYmax() {
        return ymax;
    }

    public void setYmax(Long ymax) {
        this.ymax = ymax;
    }

    public SolvMethod getMethod() {
        return method;
    }

    public void setMethod(SolvMethod method) {
        this.method = method;
    }
    
}
