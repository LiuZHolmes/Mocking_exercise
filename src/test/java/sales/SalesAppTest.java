package sales;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
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
        salesApp.generateSalesActivityReport(null, 1000, false, false);

        verify(salesDao, times(0)).getSalesBySalesId(anyString());
    }

    @Test
    public void should_return_when_today_before_effective_from() {
        calendar.set(2017, Calendar.AUGUST, 15);
        effectiveFrom = calendar.getTime();
        calendar.set(2018, Calendar.APRIL, 19);
        effectiveTo = calendar.getTime();
        calendar.set(2016, Calendar.AUGUST, 15);
        today = calendar.getTime();
        when(salesApp.getToday()).thenReturn(today);
        when(salesDao.getSalesBySalesId("DUMMY")).thenReturn(sales);
        when(sales.getEffectiveTo()).thenReturn(effectiveTo);
        when(sales.getEffectiveFrom()).thenReturn(effectiveFrom);

        salesApp.generateSalesActivityReport("DUMMY", 1000, false, false);

        verify(salesReportDao, times(0)).getReportData(sales);
    }

    @Test
    public void should_return_when_today_after_effective_to() {
        calendar.set(2018, Calendar.APRIL, 19);
        effectiveFrom = calendar.getTime();
        calendar.set(2019, Calendar.AUGUST, 15);
        effectiveTo = calendar.getTime();
        calendar.set(2020, Calendar.AUGUST, 15);
        today = calendar.getTime();
        when(salesApp.getToday()).thenReturn(today);
        when(salesDao.getSalesBySalesId("DUMMY")).thenReturn(sales);
        when(sales.getEffectiveTo()).thenReturn(effectiveTo);

        salesApp.generateSalesActivityReport("DUMMY", 1000, false, false);

        verify(salesReportDao, times(0)).getReportData(sales);
    }

    @Test
    public void should_add_data_when_data_type_is_sales_activity_and_sales_not_confidential() {
        calendar.set(2018, Calendar.APRIL, 19);
        effectiveFrom = calendar.getTime();
        calendar.set(2019, Calendar.AUGUST, 15);
        effectiveTo = calendar.getTime();
        calendar.set(2019, Calendar.APRIL, 15);
        today = calendar.getTime();
        when(salesApp.getToday()).thenReturn(today);
        when(salesDao.getSalesBySalesId("DUMMY")).thenReturn(sales);
        when(sales.getEffectiveTo()).thenReturn(effectiveTo);
        when(sales.getEffectiveFrom()).thenReturn(effectiveFrom);

        SalesReportData salesReportData = mock(SalesReportData.class);
        when(salesReportData.isConfidential()).thenReturn(false);
        when(salesReportData.getType()).thenReturn("SalesActivity");
        List<SalesReportData> salesReportDataList = new ArrayList<>();
        salesReportDataList.add(salesReportData);
        when(salesReportDao.getReportData(sales)).thenReturn(salesReportDataList);
        List<SalesReportData> filteredSalesReportDataList = new ArrayList<>();
        when(salesApp.getList()).thenReturn(filteredSalesReportDataList);

        SalesActivityReport report = mock(SalesActivityReport.class);
        when(report.toXml()).thenReturn("");
        when(salesApp.generateReport(any(), any())).thenReturn(report);

        salesApp.generateSalesActivityReport("DUMMY", 1, false, false);

        assertEquals(1, filteredSalesReportDataList.size());
    }

    @Test
    public void should_empty_when_data_type_is_sales_activity_and_sales_is_confidential_and_not_supervisor() {
        calendar.set(2018, Calendar.APRIL, 19);
        effectiveFrom = calendar.getTime();
        calendar.set(2019, Calendar.AUGUST, 15);
        effectiveTo = calendar.getTime();
        calendar.set(2019, Calendar.APRIL, 15);
        today = calendar.getTime();
        when(salesApp.getToday()).thenReturn(today);
        when(salesDao.getSalesBySalesId("DUMMY")).thenReturn(sales);
        when(sales.getEffectiveTo()).thenReturn(effectiveTo);
        when(sales.getEffectiveFrom()).thenReturn(effectiveFrom);

        SalesReportData salesReportData = mock(SalesReportData.class);
        when(salesReportData.isConfidential()).thenReturn(true);
        when(salesReportData.getType()).thenReturn("SalesActivity");
        List<SalesReportData> salesReportDataList = new ArrayList<>();
        salesReportDataList.add(salesReportData);
        when(salesReportDao.getReportData(sales)).thenReturn(salesReportDataList);
        List<SalesReportData> filteredSalesReportDataList = new ArrayList<>();
        when(salesApp.getList()).thenReturn(filteredSalesReportDataList);

        SalesActivityReport report = mock(SalesActivityReport.class);
        when(report.toXml()).thenReturn("");
        when(salesApp.generateReport(any(), any())).thenReturn(report);

        salesApp.generateSalesActivityReport("DUMMY", 1, false, false);

        assertEquals(0, filteredSalesReportDataList.size());
    }

    @Test
    public void should_add_data_when_data_type_is_sales_activity_and_sales_is_confidential_and_is_supervisor() {
        calendar.set(2018, Calendar.APRIL, 19);
        effectiveFrom = calendar.getTime();
        calendar.set(2019, Calendar.AUGUST, 15);
        effectiveTo = calendar.getTime();
        calendar.set(2019, Calendar.APRIL, 15);
        today = calendar.getTime();
        when(salesApp.getToday()).thenReturn(today);
        when(salesDao.getSalesBySalesId("DUMMY")).thenReturn(sales);
        when(sales.getEffectiveTo()).thenReturn(effectiveTo);
        when(sales.getEffectiveFrom()).thenReturn(effectiveFrom);

        SalesReportData salesReportData = mock(SalesReportData.class);
        when(salesReportData.isConfidential()).thenReturn(true);
        when(salesReportData.getType()).thenReturn("SalesActivity");
        List<SalesReportData> salesReportDataList = new ArrayList<>();
        salesReportDataList.add(salesReportData);
        when(salesReportDao.getReportData(sales)).thenReturn(salesReportDataList);
        List<SalesReportData> filteredSalesReportDataList = new ArrayList<>();
        when(salesApp.getList()).thenReturn(filteredSalesReportDataList);

        SalesActivityReport report = mock(SalesActivityReport.class);
        when(report.toXml()).thenReturn("");
        when(salesApp.generateReport(any(), any())).thenReturn(report);

        salesApp.generateSalesActivityReport("DUMMY", 1, false, true);

        assertEquals(1, filteredSalesReportDataList.size());
    }

    @Test
    public void should_return_local_time_headers_when_not_nat_trade() {
        calendar.set(2018, Calendar.APRIL, 19);
        effectiveFrom = calendar.getTime();
        calendar.set(2019, Calendar.AUGUST, 15);
        effectiveTo = calendar.getTime();
        calendar.set(2019, Calendar.APRIL, 15);
        today = calendar.getTime();
        when(salesApp.getToday()).thenReturn(today);
        when(salesDao.getSalesBySalesId("DUMMY")).thenReturn(sales);
        when(sales.getEffectiveTo()).thenReturn(effectiveTo);
        when(sales.getEffectiveFrom()).thenReturn(effectiveFrom);

        List<String> headers = new ArrayList<>();
        when(salesApp.getHeaders()).thenReturn(headers);

        SalesActivityReport report = mock(SalesActivityReport.class);
        when(report.toXml()).thenReturn("");
        when(salesApp.generateReport(any(), any())).thenReturn(report);

        salesApp.generateSalesActivityReport("DUMMY", 1, false, true);

        assertEquals("Local Time", headers.get(3));
    }
}
