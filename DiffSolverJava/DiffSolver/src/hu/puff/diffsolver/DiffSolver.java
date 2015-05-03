package hu.puff.diffsolver;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import hu.puff.diffsolver.gui.MainPanel;
import hu.puff.diffsolver.output.JSONParser;
import hu.puff.diffsolver.output.MeasurementHandler;
import hu.puff.diffsolver.output.MeasurementItem;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DiffSolver extends JPanel {

    private static JFrame frame = null;

    private MainPanel mPanel;

    private int PREFERRED_WIDTH = 720;
    private int PREFERRED_HEIGHT = 480;

    private DiffSolverAction funcPainterAction = new DiffSolverAction();

    private JMenuBar menuBar = null;
    private JMenuItem exitMItem, saveMItem, loadMItem, colorMItem;
    private Color color;

    public DiffSolver() {
        frame = createFrame();

        setLayout(new BorderLayout());

        frame.getContentPane().add(createMenus(), BorderLayout.NORTH);

        setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));

        initialize();

        showDiffSolver();
    }

    public static void main(String[] args) {
        DiffSolver funcPainter = new DiffSolver();
    }

    public JFrame createFrame() {
        JFrame frame = new JFrame();

        frame.getContentPane().setLayout(new BorderLayout());

        WindowListener l = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitApp();
            }
        };

        frame.addWindowListener(l);

        return frame;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void showDiffSolver() {
        if (getFrame() != null) {
            JFrame f = getFrame();
            f.getContentPane().add(this, BorderLayout.CENTER);
            f.pack();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            getFrame().setLocation(screenSize.width / 2 - f.getSize().width / 2, screenSize.height / 2 - f.getSize().height / 2);
            getFrame().setVisible(true);
        }
    }

    public void initialize() {
        mPanel = new MainPanel();
        add(mPanel);
    }

    public void exitApp() {
        frame.dispose();
    }

    private JMenuBar createMenus() {

        JMenuBar menuBar = new JMenuBar();
        menuBar.getAccessibleContext().setAccessibleName("Főmenü");

        JMenu fileMenu = (JMenu) menuBar.add(new JMenu("File"));
        saveMItem = createMenuItem(fileMenu, "Mentés", "Adatok mentése JSON formátumba");
        loadMItem = createMenuItem(fileMenu, "Megnyitás", "JSON adatfájl megnyitása");
        fileMenu.addSeparator();
        exitMItem = createMenuItem(fileMenu, "Kilépés", "Kilépés a programból");

        JMenu grafMenu = (JMenu) menuBar.add(new JMenu("Grafikon"));
        colorMItem = createMenuItem(grafMenu, "Szín", "Grafikon színének módosítása");

        return menuBar;
    }

    private JMenuItem createMenuItem(JMenu menu, String label, String accessibleDescription) {
        JMenuItem mi = (JMenuItem) menu.add(new JMenuItem(label));

        mi.setMnemonic(label.charAt(0));
        mi.getAccessibleContext().setAccessibleDescription(accessibleDescription);
        mi.addActionListener(funcPainterAction);

        return mi;
    }

    class DiffSolverAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            if (source.equals(exitMItem)) {
                exitApp();
            }
            if (source.equals(saveMItem)) {
                // Saving generated JSON
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "JSON file", "json");

                JFileChooser saveFileChooser = new JFileChooser();
                saveFileChooser.addChoosableFileFilter(filter);
                saveFileChooser.setFileFilter(filter);
                saveFileChooser.setSelectedFile(new File(".json"));

                // Demonstrate "Save" dialog:
                int rVal = saveFileChooser.showSaveDialog(DiffSolver.this);
                if (rVal == JFileChooser.APPROVE_OPTION) {

                    JSONParser.getInstance().dataToJSON(
                            MeasurementHandler.getInstance().getMathResult(), //getMathResult
                            saveFileChooser.getSelectedFile());
                }
                if (rVal == JFileChooser.CANCEL_OPTION) {
                    System.out.println("Cancel");
                }
            }
            if (source.equals(loadMItem)) {
// Loading JSON
                java.util.List<MeasurementItem> list = null;

                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "JSON file", "json");

                JFileChooser loadFileChooser = new JFileChooser();
                loadFileChooser.addChoosableFileFilter(filter);
                loadFileChooser.setFileFilter(filter);
                loadFileChooser.setSelectedFile(new File(".json"));

                int rVal = loadFileChooser.showOpenDialog(DiffSolver.this);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    list = JSONParser.getInstance()
                            .getDataFromJSON(loadFileChooser.getSelectedFile());
                    System.out.println(list.toString());
                }
                if (rVal == JFileChooser.CANCEL_OPTION) {
                    System.out.println("Cancel");
                }

                mPanel.drawChart(list);
            }
            if (source.equals(colorMItem)) {
                color = JColorChooser.showDialog(null, "Színválasztó", color);

                mPanel.colorChart(color);
            }
        }
    }

}
