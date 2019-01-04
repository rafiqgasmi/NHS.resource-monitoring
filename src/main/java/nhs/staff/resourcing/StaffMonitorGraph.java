package nhs.staff.resourcing;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Creates a real-time chart using SwingWorker
 */
public class StaffMonitorGraph implements GetChart<XYChart>, RealtimeChart {


    private XYChart chart;
    private List<Double> yData;
    public static final String SERIES_NAME = "Patient to Staff";


    public static void main(String[] args) throws Exception {

        final StaffMonitorGraph staffMonitorGraph = new StaffMonitorGraph();
        staffMonitorGraph.go();
    }

    private void go() {

        final SwingWrapper<XYChart> swingWrapper = new SwingWrapper<XYChart>(getChart());
        swingWrapper.displayChart();


        //simulates a data feed
        TimerTask chartUpdaterTask =
            new TimerTask() {

                @Override
                public void run() {

                    updateData();

                    javax.swing.SwingUtilities.invokeLater(
                        new Runnable() {

                            @Override
                            public void run() {

                                swingWrapper.repaintChart();
                            }
                        });
                }
            };

        java.util.Timer timer = new Timer();
        timer.scheduleAtFixedRate(chartUpdaterTask, 0, 500);

    }

    @Override
    public XYChart getChart() {
        yData = getRandomData(5);

        // Create Chart
        chart =
            new XYChartBuilder()
                .width(500)
                .height(400)
                .theme(Styler.ChartTheme.GGPlot2)
                .title("Staff Monitoring")
                .build();
        chart.addSeries(SERIES_NAME, null, yData);

        return chart;
    }

    @Override
    public void updateData() {
        // Get some new data
        List<Double> newData = getRandomData(1);

        yData.addAll(newData);

        // Limit the total number of points
        while (yData.size() > 20) {
            yData.remove(0);
        }

        chart.updateXYSeries(SERIES_NAME, null, yData, null);
    }

    private List<Double> getRandomData(int numPoints) {

        List<Double> data = new CopyOnWriteArrayList<Double>();
        for (int i = 0; i < numPoints; i++) {
            data.add(Math.random() * 100);
        }
        return data;
    }
}

