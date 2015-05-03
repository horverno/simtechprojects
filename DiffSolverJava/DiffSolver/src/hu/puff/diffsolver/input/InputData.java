package hu.puff.diffsolver.input;

/**
 *
 * @author JombY
 *
 * Egyenleteket, kezdő értékeket, koordináta rendszer beosztás értékeit, és
 * léptékszámot tároló entity
 */
public class InputData {

    private String equations;
    private Double x0, xmax, incr, ymin, ymax, card;

    public InputData() {

    }

    public InputData(String equations, Double x0, Double xmax, Double incr, Double ymin, Double ymax, Double card) {
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
        // számosság
        this.card = card;
    }

    public String getEquations() {
        return equations;
    }

    public void setEquations(String equations) {
        this.equations = equations;
    }

    public Double getX0() {
        return x0;
    }

    public void setX0(Double x0) {
        this.x0 = x0;
    }

    public Double getXmax() {
        return xmax;
    }

    public void setXmax(Double xmax) {
        this.xmax = xmax;
    }

    public Double getIncr() {
        return incr;
    }

    public void setIncr(Double incr) {
        this.incr = incr;
    }

    public Double getYmin() {
        return ymin;
    }

    public void setYmin(Double ymin) {
        this.ymin = ymin;
    }

    public Double getYmax() {
        return ymax;
    }

    public void setYmax(Double ymax) {
        this.ymax = ymax;
    }

    public Double getCard() {
        return card;
    }

    public void setCard(Double card) {
        this.card = card;
    }

}
