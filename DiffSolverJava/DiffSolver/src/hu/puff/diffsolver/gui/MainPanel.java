package hu.puff.diffsolver.gui;

import hu.puff.diffsolver.input.InputData;
import hu.puff.diffsolver.input.SolvMethod;
import hu.puff.diffsolver.math.DiffMathException;
import hu.puff.diffsolver.math.DifferentailEquation;
import hu.puff.diffsolver.math.ResultStore;
import hu.puff.diffsolver.output.MeasurementItem;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartPanel;
import org.jfree.data.Range;

public class MainPanel extends JPanel implements ActionListener {

    private LineChart demo;
    ChartPanel cp;
    private JPanel bottomPanel;
    java.util.List<MeasurementItem> list;
    private InputData data;

    public final SolvMethod[] mathMethods = {SolvMethod.EULER, SolvMethod.MIDPOINT, SolvMethod.RUNGEKUTTA3, SolvMethod.RUNGEKUTTA4};

    private Double xMin = 0.0, xMax = 30.0, yMin = -3.0, yMax = 3.0, incr = 0.1, card = 10d, startT = 0d;
    private String function = "x''=1/100*(1-x'-2*x),0.5,1";

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
    private JLabel startTLabel = new JLabel("Kezdő idő ", JLabel.RIGHT);
    private DecimalField startTTextF = new DecimalField(startT, 5);
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

        textPanel.add(startTLabel);
        startTLabel.setLabelFor(startTTextF);
        textPanel.add(startTTextF);
        startTTextF.addActionListener(this);

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
        startT = startTTextF.getValue();

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
            try {
                data = getEnvironmentData();
                StringTokenizer str = new StringTokenizer(data.getEquations(), ",");
                String eq = str.nextToken();
                String start = str.nextToken(",");
                double secOrdStart = 0;
                boolean has = str.hasMoreTokens();
                if (eq.contains("''") && !has) {
                    JOptionPane.showMessageDialog(null, "Másodrendű kezdőérték hiányzik!", "Hiba!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (has) {
                    secOrdStart = Double.parseDouble(str.nextToken(","));
                }
                DifferentailEquation difeq = new DifferentailEquation();

                ResultStore coolingResult = null;
                switch ((SolvMethod) methodComboBox.getSelectedItem()) {
                    case EULER: {
                        System.out.println("Euler módszer");
                        try {
                            coolingResult = difeq.calculateResulte(eq, SolvMethod.EULER,
                                    Double.parseDouble(start), secOrdStart, data.getIncr(),
                                    data.getCard().intValue(), startT.intValue());
                        } catch (DiffMathException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Hiba!", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    }
                    case MIDPOINT: {
                        System.out.println("MIDPOINT módszer");
                        try {
                            coolingResult = difeq.calculateResulte(eq, SolvMethod.MIDPOINT,
                                    Double.parseDouble(start), secOrdStart,
                                    data.getIncr(), data.getCard().intValue(), startT.intValue());
                        } catch (DiffMathException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Hiba!", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    }
                    case RUNGEKUTTA3: {
                        System.out.println("RUNGEKUTTA3 módszer");
                        try {
                            coolingResult = difeq.calculateResulte(eq, SolvMethod.RUNGEKUTTA3,
                                    Double.parseDouble(start), secOrdStart,
                                    data.getIncr(), data.getCard().intValue(), startT.intValue());
                        } catch (DiffMathException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Hiba!", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    }
                    case RUNGEKUTTA4: {
                        System.out.println("RUNGEKUTTA4 módszer");
                        try {
                            coolingResult = difeq.calculateResulte(eq, SolvMethod.RUNGEKUTTA4,
                                    Double.parseDouble(start), secOrdStart,
                                    data.getIncr(), data.getCard().intValue(), startT.intValue());
                        } catch (DiffMathException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Hiba!", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    }
                    default: {
                        coolingResult = new ResultStore();
                    }
                }
                list = new ArrayList<>();
                System.out.println("Hűlés:");
                if (coolingResult != null) {
                    for (Map.Entry<Double, Double> entry : coolingResult.getResult()
                            .entrySet()) {
                        MeasurementItem item = new MeasurementItem(entry.getKey(), entry.getValue());
                        list.add(item);
                        System.out.println(entry.getKey() + " - " + entry.getValue());
                    }
                    drawChart(list);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Nem jó kezdő értéket, értékeket adott meg!", "Hiba!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void setEnvironment(InputData data) {
        xminTextF.setValue(data.getX0());
        xMin = data.getX0();
        xmaxTextF.setValue(data.getXmax());
        xMax = data.getXmax();
        yminTextF.setValue(data.getYmin());
        yMin = data.getYmin();
        ymaxTextF.setValue(data.getYmax());
        yMax = data.getYmax();
        incrTextF.setValue(data.getIncr());
        incr = data.getIncr();
        cardTextF.setValue(data.getCard());
        card = data.getCard();
        funcTextA.setText(data.getEquations());
        function = data.getEquations();
        startTTextF.setValue(data.getStartT());
        startT = data.getStartT();
    }

    public InputData getEnvironmentData() {
        return data = new InputData(funcTextA.getText(), xminTextF.getValue(),
                xmaxTextF.getValue(), incrTextF.getValue(),
                yminTextF.getValue(), ymaxTextF.getValue(), cardTextF.getValue(), startTTextF.getValue());
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
        if (cp == null) {
            cp = new ChartPanel(demo.getChart());
        } else {
            cp.removeAll();
            cp.setChart(demo.getChart());
        }
        cp.getChart().getXYPlot().getDomainAxis().setRange(new Range(xMin, xMax));
        cp.getChart().getXYPlot().getRangeAxis().setRange(new Range(yMin, yMax));
        bottomPanel.add(cp);
        bottomPanel.validate();
    }

    public InputData getData() {
        return data;
    }

}
