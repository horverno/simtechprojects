package hu.puff.diffsolver.gui;

import hu.puff.diffsolver.input.InputData;
import hu.puff.diffsolver.input.SolvMethod;
import hu.puff.diffsolver.math.Function;
import hu.puff.diffsolver.math.ResultStore;
import hu.puff.diffsolver.math.Solvers;
import hu.puff.diffsolver.output.MeasurementItem;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import org.jfree.chart.ChartPanel;

public class MainPanel extends JPanel implements ActionListener {

    private LineChart demo;
    private JPanel bottomPanel;
    java.util.List<MeasurementItem> list;
    private InputData data;

    public final SolvMethod[] mathMethods = {SolvMethod.EULER, SolvMethod.IMPLICITEULER, SolvMethod.RUNGEKUTTA, SolvMethod.RUNGEKUTTA4};

    private double xMin = 0.0, xMax = 30.0, yMin = -3.0, yMax = 3.0, incr = 0.1;
    Double card = 10d;
    private String function = "2*y-0.5,0.5";

    private String ftemp;

    private JLabel xminLabel = new JLabel("Minimum X érték ", JLabel.RIGHT);
    private DecimalField xminTextF = new DecimalField(xMin, 5);
    private JLabel xmaxLabel = new JLabel("Maximum X érték ", JLabel.RIGHT);
    private DecimalField xmaxTextF = new DecimalField(xMax, 5);
    private JLabel yminLabel = new JLabel("Minimum Y érték ", JLabel.RIGHT);
    private DecimalField yminTextF = new DecimalField(yMin, 5);
    private JLabel ymaxLabel = new JLabel("Maximum Y érték ", JLabel.RIGHT);
    private DecimalField ymaxTextF = new DecimalField(yMax, 5);
    private JLabel incrLabel = new JLabel("Növekmény érték ", JLabel.RIGHT);
    private DecimalField incrTextF = new DecimalField(incr, 5);
    private JLabel cardLabel = new JLabel("Számosság ", JLabel.RIGHT);
    private DecimalField cardTextF = new DecimalField(card, 5);
    private JTextArea funcTextA = new JTextArea(function, 4, 10);
    private JLabel methodLabel = new JLabel("Függvények ", JLabel.RIGHT);
    private JComboBox methodComboBox = new JComboBox(mathMethods);

    private JButton refreshButton = new JButton("Újra számol");

    public MainPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        funcTextA.setLineWrap(false);
        funcTextA.setWrapStyleWord(false);
        JScrollPane areaScrollPane = new JScrollPane(funcTextA);
        areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        areaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel topPanel = new JPanel();
        bottomPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        topPanel.add(areaScrollPane);

        JPanel varPanel = new JPanel();
        varPanel.setLayout(new BoxLayout(varPanel, BoxLayout.Y_AXIS));
        varPanel.setBorder(BorderFactory.createTitledBorder("Változók"));

        JPanel textPanel = new JPanel(new GridLayout(0, 4));

        textPanel.add(xminLabel);
        xminLabel.setLabelFor(xminTextF);
        textPanel.add(xminTextF);
        xminTextF.addActionListener(this);

        textPanel.add(xmaxLabel);
        xmaxLabel.setLabelFor(xmaxTextF);
        textPanel.add(xmaxTextF);
        xmaxTextF.addActionListener(this);

        textPanel.add(yminLabel);
        yminLabel.setLabelFor(yminTextF);
        textPanel.add(yminTextF);
        yminTextF.addActionListener(this);

        textPanel.add(ymaxLabel);
        ymaxLabel.setLabelFor(ymaxTextF);
        textPanel.add(ymaxTextF);
        ymaxTextF.addActionListener(this);

        textPanel.add(incrLabel);
        incrLabel.setLabelFor(incrTextF);
        textPanel.add(incrTextF);
        incrTextF.addActionListener(this);

        textPanel.add(cardLabel);
        cardLabel.setLabelFor(cardTextF);
        textPanel.add(cardTextF);
        cardTextF.addActionListener(this);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(methodLabel);
        buttonPanel.add(methodComboBox);
        methodComboBox.addActionListener(this);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(refreshButton);
        refreshButton.addActionListener(this);

        varPanel.add(textPanel);
        varPanel.add(buttonPanel);

        topPanel.add(varPanel);

        ftemp = new String(function);
        Vector tempstrings = SplitString(function);
        tempstrings = SplitFunction(tempstrings);

        int maxTopWidth = (int) topPanel.getMaximumSize().getWidth();
        int minTopHeight = (int) topPanel.getMinimumSize().getHeight();
        topPanel.setMaximumSize(new Dimension(maxTopWidth, minTopHeight));

        drawChart(null);

        int maxBottomWidth = (int) bottomPanel.getMaximumSize().getWidth();
        int minBottomHeight = (int) bottomPanel.getMinimumSize().getHeight();
        bottomPanel.setMaximumSize(new Dimension(maxBottomWidth, minBottomHeight));

        add(topPanel);
        add(bottomPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        xMin = xminTextF.getValue();
        xMax = xmaxTextF.getValue();
        yMin = yminTextF.getValue();
        yMax = ymaxTextF.getValue();
        incr = incrTextF.getValue();
        card = cardTextF.getValue();

        if (incr <= 0) {
            JOptionPane.showMessageDialog(null, "A növekménynek nullánál nagyobb értéket kell megadni! Alapérték lesz beállítva(0.1)!", "Hiba!", JOptionPane.ERROR_MESSAGE);

            incr = 0.1;
            incrTextF.setValue(incr);
        }

        if (card <= 0) {
            JOptionPane.showMessageDialog(null, "A számosságnak nullánál nagyobb egész értéket kell megadni! Alapérték lesz beállítva(10)!", "Hiba!", JOptionPane.ERROR_MESSAGE);

            card = 10d;
            cardTextF.setValue(card);
        }

        if ((xMax - xMin) * incr < 0 || yMin > yMax) {
            JOptionPane.showMessageDialog(null, "A maximumnak nagyobbnak kell lenni mint a minimum!", "Hiba!", JOptionPane.ERROR_MESSAGE);
        } else {
            InputData data = getEnvironmentData();
            StringTokenizer str = new StringTokenizer(data.getEquations(), ",");

            Function f = new Function(str.nextToken());
            ResultStore coolingResult;
            switch ((SolvMethod) methodComboBox.getSelectedItem()) {
                case EULER: {
                    System.out.println("Euler m�dszer");
                    coolingResult = Solvers.eulerMethod(f, Double.parseDouble(str.nextToken(",")), data.getIncr(), data.getCard().intValue());
                    break;
                }
                case IMPLICITEULER: {
                    System.out.println("impEuler m�dszer");
                    coolingResult = Solvers.implicitEulerMethod(f, Double.parseDouble(str.nextToken(",")), data.getIncr(), data.getCard().intValue());
                    break;
                }
                case RUNGEKUTTA: {
                    System.out.println("RUNGEKUTTA m�dszer");
                    coolingResult = Solvers.rungeKuttaSecondOrderMethod(f, Double.parseDouble(str.nextToken(",")), data.getIncr(), data.getCard().intValue());
                    break;
                }
                case RUNGEKUTTA4: {
                    System.out.println("RUNGEKUTTA4 m�dszer");
                    coolingResult = Solvers.rungeKuttaFourthOrderMethod(f, Double.parseDouble(str.nextToken(",")), data.getIncr(), data.getCard().intValue());
                    break;
                }
                default: {
                    coolingResult = new ResultStore();
                }
            }
            java.util.List<MeasurementItem> list = new ArrayList<>();
            System.out.println("H�l�s:");
            for (Map.Entry<Double, Double> entry : coolingResult.getResult()
                    .entrySet()) {
                MeasurementItem item = new MeasurementItem(entry.getKey(), entry.getValue());
                list.add(item);
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
            drawChart(list);
        }
    }

    private Vector SplitString(String split) {
        StringTokenizer str = new StringTokenizer(split, "\n");
        Vector v = new Vector(1, 1);

        while (str.hasMoreTokens()) {
            v.addElement(str.nextToken());
        }

        return v;
    }

    private Vector SplitFunction(Vector functions) {
        Vector ret = new Vector(1, 1);
        Vector f = new Vector(1, 1);
        Vector a = new Vector(1, 1);
        Enumeration e = functions.elements();

        while (e.hasMoreElements()) {
            String split = (String) e.nextElement();
            StringTokenizer str = new StringTokenizer(split, ",");
            if (str.countTokens() != 2) {
                return null;
            }
            f.addElement(str.nextToken());
            Double dummy = new Double(str.nextToken());
            a.addElement(dummy);
        }

        ret.addElement(f);
        ret.addElement(a);

        return ret;
    }

    public void setEnvironment(double xMin, double xMax, double yMin, double yMax, double incr, double card, String function) {
        xminTextF.setValue(xMin);
        xmaxTextF.setValue(xMax);
        yminTextF.setValue(yMin);
        ymaxTextF.setValue(yMax);
        incrTextF.setValue(incr);
        cardTextF.setValue(card);
        funcTextA.setText(function);

        ftemp = new String(function);
        Vector tempstrings = SplitString(function);

        tempstrings = SplitFunction(tempstrings);
    }

    public InputData getEnvironmentData() {
        return data = new InputData(funcTextA.getText(), xminTextF.getValue(),
                xmaxTextF.getValue(), incrTextF.getValue(),
                yminTextF.getValue(), ymaxTextF.getValue(), cardTextF.getValue());
    }

    public void drawChart(java.util.List<MeasurementItem> list) {
        this.list = list;
        demo = new LineChart("Teszt", list);
        initChart();
    }

    public void colorChart(Color color) {
        demo.setColor(color);
        initChart();
    }

    private void initChart() {
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(new ChartPanel(demo.getChart()));
        bottomPanel.validate();
    }
;

}
