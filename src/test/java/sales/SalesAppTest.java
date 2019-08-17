package sales;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.*;

public class SalesAppTest {

    @Test
    public void should_return_when_today_after_effective_to() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, Calendar.APRIL, 19);
        Date effectiveTo = calendar.getTime();
        calendar.set(2019, Calendar.AUGUST, 15);
        Date today = calendar.getTime();

        SalesApp salesApp = mock(SalesApp.class);
        Sales sales = mock(Sales.class);
        SalesDao salesDao = mock(SalesDao.class);
        SalesReportDao salesReportDao = mock(SalesReportDao.class);
        when(salesApp.getSalesDao()).thenReturn(salesDao);
        when(salesApp.getSalesReportDao()).thenReturn(salesReportDao);
        when(salesApp.getToday()).thenReturn(today);
        when(salesDao.getSalesBySalesId("DUMMY")).thenReturn(sales);
        when(sales.getEffectiveTo()).thenReturn(effectiveTo);

        salesApp.generateSalesActivityReport("DUMMY", 1000, false, false);

        verify(salesReportDao, times(0)).getReportData(sales);

    }
}
