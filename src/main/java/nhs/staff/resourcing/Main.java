package nhs.staff.resourcing;

import org.knowm.xchart.XChartPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import javax.swing.event.TreeSelectionListener;

public class Main extends JPanel   {

    /** The main split frame */
    private final JSplitPane splitPane;

    /** The panel for chart */
    protected XChartPanel chartPanel;

    /** The Staff Monitor */
    final StaffMonitor staffMonitor = new StaffMonitor();
    Timer timer = new Timer();


    /** Constructor */
    public Main() {

        super(new GridLayout(1, 0));


        // Create Chart Panel
        chartPanel = new XChartPanel(new StaffMonitor().getChart());

        // Add the scroll panes to a split pane.
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setBottomComponent(chartPanel);

        splitPane.setPreferredSize(new Dimension(700, 700));

        // Add the split pane to this panel.
        JButton showDialogButton = new JButton("Text Button");
        showDialogButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                p();
            }
        });
        add(showDialogButton);
        add(splitPane);


    }

    private void p (){
        TimerTask chartUpdaterTask =
            new TimerTask() {

                @Override
                public void run() {

                    staffMonitor.updateData();
                    chartPanel.revalidate();
                    chartPanel.repaint();
                }
            };
        timer = new Timer();
        timer.scheduleAtFixedRate(chartUpdaterTask, 0, 500);
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be invoked from the event
     * dispatch thread.
     */
    private static void createAndShowGUI() {

        // Create and set up the window.
        JFrame frame = new JFrame("Staff Monitoring");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Add content to the window.
        frame.add(new Main());

        // Display the window.
        frame.pack();
        frame.setVisible(true);


    }

    public static void main(String[] args) {


        // Schedule a job for the event dispatch thread:
        // creating and showing this application's GUI.
        SwingUtilities.invokeLater(
            new Runnable() {

                @Override
                public void run() {
                    createAndShowGUI();
                }
            });
    }

}
