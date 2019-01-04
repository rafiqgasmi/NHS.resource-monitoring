package nhs.staff.resourcing;

import org.knowm.xchart.XChartPanel;

import java.awt.*;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

public class MainGui extends JPanel implements TreeSelectionListener   {

    /** The main split frame */
    private final JSplitPane splitPane;

    /** The panel for chart */
    protected XChartPanel chartPanel;

    /** The Staff Monitor */
    final StaffMonitorGraph staffMonitorGraph = new StaffMonitorGraph();
    Timer timer;

    /** The tree */
    private final JTree tree;

    /** Constructor */
    public MainGui() {

        super(new GridLayout(1, 0));


        // Create Chart Panel
        chartPanel = new XChartPanel(staffMonitorGraph.getChart());

        // Creates the split Panel
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        // Add the scroll panes to a split pane.
        splitPane.setBottomComponent(chartPanel);
        // Sets size
        splitPane.setPreferredSize(new Dimension(700, 700));

        // Creates Jtree with nodes
        tree = new JTree(createNodes());
        // populate List of Staff
        ListOfAvailableStaff();

        // set up real-time chart simulated data feed
        TimerTask chartUpdaterTask =
            new TimerTask() {

                @Override
                public void run() {

                    staffMonitorGraph.updateData();
                    chartPanel.revalidate();
                    chartPanel.repaint();
                }
            };
        timer = new Timer();
        timer.scheduleAtFixedRate(chartUpdaterTask, 0, 500);

        add(splitPane);

    }

    private void ListOfAvailableStaff(){

        // Create the nodes.
        // Create a tree that allows one selection at a time.
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        // Listen for when the selection changes.
        tree.addTreeSelectionListener(this);

        // Create the scroll pane and add the tree to it.
        JScrollPane treeView = new JScrollPane(tree);

        splitPane.setTopComponent(treeView);
        splitPane.setTopComponent(treeView);

        // min size of panel
        Dimension minimumSize = new Dimension(130, 160);
        treeView.setMinimumSize(minimumSize);



    }

    private DefaultMutableTreeNode createNodes() {
        // categories
        DefaultMutableTreeNode category = new DefaultMutableTreeNode("Staff Available");

        // leaves
        DefaultMutableTreeNode defaultMutableTreeNode;

        category.add(new DefaultMutableTreeNode("staff 1"));
        category.add(new DefaultMutableTreeNode("staff 2"));


        return category;
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
        frame.add(new MainGui());

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

    @Override
    public void valueChanged(TreeSelectionEvent treeSelectionEvent) {

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();


        if (node == null) {
            return;
        }

        Object nodeInfo = node.getUserObject();
        // tree leaf
        if (node.isLeaf()) {
            splitPane.setBottomComponent(chartPanel);

            }

    }
}
