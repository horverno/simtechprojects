package szimtech.puff.input;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import szimtech.puff.input.gui.MainPanel;

public class DiffSolver extends JPanel {

    private static JFrame frame = null;

    private MainPanel mPanel;

    private int PREFERRED_WIDTH = 640;
    private int PREFERRED_HEIGHT = 480;

    private DiffSolverAction funcPainterAction = new DiffSolverAction();

    private JMenuBar menuBar = null;
    private JMenuItem exitMItem;

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
            getFrame().show();
        }
    }

    public void initialize() {
        mPanel = new MainPanel();
        add(mPanel);
    }

    public void exitApp() {
//      frame.hide();
        frame.dispose();
    }

    private JMenuBar createMenus() {
        JMenuItem mi;

        JMenuBar menuBar = new JMenuBar();
        menuBar.getAccessibleContext().setAccessibleName("Főmenü");

        JMenu fileMenu = (JMenu) menuBar.add(new JMenu("File"));
        fileMenu.addSeparator();
        exitMItem = createMenuItem(fileMenu, "Kilépés", "Kilépés a programból");

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
        }
    }

}
