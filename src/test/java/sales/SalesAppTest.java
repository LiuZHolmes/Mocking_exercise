package sales;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.*;

public class SalesAppTest {
	private Calendar calendar;
	private Date effectiveFrom;
	private Date effectiveTo;
	private Date today;
	private SalesApp salesApp;
	private Sales sales;
	private SalesDao salesDao;
	private SalesReportDao salesReportDao;

	@Before
	public void setUp() throws Exception {
		calendar = Calendar.getInstance();
		salesApp = spy(new SalesApp());
		sales = mock(Sales.class);
		salesDao = mock(SalesDao.class);
		salesReportDao = mock(SalesReportDao.class);
		when(salesApp.getSalesDao()).thenReturn(salesDao);
		when(salesApp.getSalesReportDao()).thenReturn(salesReportDao);
	}

	@Test
	public void should_return_when_saleId_is_null() {
		SalesApp salesApp = mock(SalesApp.class);
		salesApp.generateSalesActivityReport(null, 1000, false, false);

		verify(salesDao, times(0)).getSalesBySalesId(anyString());

	}

    @Test
    public void should_return_when_today_after_effective_to() {
		calendar.set(2018, Calendar.APRIL, 19);
		effectiveTo = calendar.getTime();
		calendar.set(2019, Calendar.AUGUST, 15);
		today = calendar.getTime();
		when(salesApp.getToday()).thenReturn(today);
		when(salesDao.getSalesBySalesId("DUMMY")).thenReturn(sales);
		when(sales.getEffectiveTo()).thenReturn(effectiveTo);

        salesApp.generateSalesActivityReport("DUMMY", 1000, false, false);

        verify(salesReportDao, times(0)).getReportData(sales);

    }
}
