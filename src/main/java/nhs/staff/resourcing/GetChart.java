package nhs.staff.resourcing;

import org.knowm.xchart.internal.chartpart.Chart;

public interface GetChart<C extends Chart<?, ?>> {

    C getChart();
}
