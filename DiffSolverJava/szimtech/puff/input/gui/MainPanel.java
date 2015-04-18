package szimtech.puff.input.gui;

import szimtech.puff.input.SolvMethod;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class MainPanel extends JPanel implements ActionListener {

    public final String[] mathMethods = {SolvMethod.EULER.getMethod(), SolvMethod.RUNGEKUTTA.getMethod()};

    private double xMin = 0.0, xMax = 30.0, yMin = -3.0, yMax = 3.0, incr = 0.1;
    private String function = "y1-sin(0.5),0.5";

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
    private JTextArea funcTextA = new JTextArea(function, 4, 10);
    private JLabel methodLabel = new JLabel("Függvények ", JLabel.RIGHT);
    private JComboBox methodComboBox = new JComboBox(mathMethods);

    private JButton refreshButton = new JButton("Újra számol");

    public MainPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // lets build the TextArea
        funcTextA.setLineWrap(false);
        funcTextA.setWrapStyleWord(false);
        JScrollPane areaScrollPane = new JScrollPane(funcTextA);
        areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        areaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(areaScrollPane);

        JPanel varPanel = new JPanel();
        varPanel.setLayout(new BoxLayout(varPanel, BoxLayout.Y_AXIS));
        varPanel.setBorder(BorderFactory.createTitledBorder("Változók"));

        JPanel textPanel = new JPanel(new GridLayout(0, 5));

        textPanel.add(xminLabel);
        xminLabel.setLabelFor(xminTextF);
        textPanel.add(xminTextF);
        xminTextF.addActionListener(this);

        textPanel.add(xmaxLabel);
        xmaxLabel.setLabelFor(xmaxTextF);
        textPanel.add(xmaxTextF);
        xmaxTextF.addActionListener(this);

        textPanel.add(incrLabel);
        incrLabel.setLabelFor(incrTextF);
        textPanel.add(incrTextF);
        incrTextF.addActionListener(this);

        textPanel.add(yminLabel);
        yminLabel.setLabelFor(yminTextF);
        textPanel.add(yminTextF);
        yminTextF.addActionListener(this);

        textPanel.add(ymaxLabel);
        ymaxLabel.setLabelFor(ymaxTextF);
        textPanel.add(ymaxTextF);
        ymaxTextF.addActionListener(this);

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

        int maxwidth = (int) topPanel.getMaximumSize().getWidth();
        int minheight = (int) topPanel.getMinimumSize().getHeight();
        topPanel.setMaximumSize(new Dimension(maxwidth, minheight));

        add(topPanel);
    }

    // lets define the ActionListener Method
    public void actionPerformed(ActionEvent e) {
        xMin = xminTextF.getValue();
        xMax = xmaxTextF.getValue();
        yMin = yminTextF.getValue();
        yMax = ymaxTextF.getValue();
        incr = incrTextF.getValue();

        if (incr <= 0) {
            JOptionPane.showMessageDialog(null, "A növekménynek nullánál nagyobb értéket kell megadni! Alapérték lesz beállítva(0.1)!", "Hiba!", JOptionPane.ERROR_MESSAGE);

            incr = 0.1;
            incrTextF.setValue(incr);
        }

        if ((xMax - xMin) * incr < 0 || yMin > yMax) {
            JOptionPane.showMessageDialog(null, "A maximumnak nagyobbnak kell lenni mint a minimum!", "Hiba!", JOptionPane.ERROR_MESSAGE);
            return;
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

    public void setEnvironment(double xMin, double xMax, double yMin, double yMax, double incr, String function) {
        xminTextF.setValue(xMin);
        xmaxTextF.setValue(xMax);
        yminTextF.setValue(yMin);
        ymaxTextF.setValue(yMax);
        incrTextF.setValue(incr);
        funcTextA.setText(function);

        ftemp = new String(function);
        Vector tempstrings = SplitString(function);

        tempstrings = SplitFunction(tempstrings);
    }

}
