package sales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SalesApp {

    public void generateSalesActivityReport(String salesId, int maxRow, boolean isNatTrade, boolean isSupervisor) {
        SalesDao salesDao = getSalesDao();
        SalesReportDao salesReportDao = getSalesReportDao();
        if (salesId == null) return;
        Sales sales = salesDao.getSalesBySalesId(salesId);
        if (isTimeOutOfRange(sales.getEffectiveFrom(), sales.getEffectiveTo())) return;
        UploadReport(generateHeaders(isNatTrade),
                filterReportData(salesReportDao.getReportData(sales), isSupervisor));
    }

    SalesDao getSalesDao() {
        return new SalesDao();
    }

    SalesReportDao getSalesReportDao() {
        return new SalesReportDao();
    }

    Date getToday() {
        return new Date();
    }

    List<SalesReportData> getList() {
        return new ArrayList<>();
    }

    List<String> getHeaders() {
        return new ArrayList<>();
    }

    private boolean isTimeOutOfRange(Date begin, Date end) {
        Date today = getToday();
        return today.after(end) || today.before(begin);
    }

    private List<String> generateHeaders(boolean isNatTrade) {
        List<String> headers = getHeaders();
        if (isNatTrade) headers.addAll(Arrays.asList("Sales ID", "Sales Name", "Activity", "Time"));
        else headers.addAll(Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time"));
        return headers;

    }

    private List<SalesReportData> filterReportData(List<SalesReportData> reportDataList, boolean isSupervisor) {
        List<SalesReportData> filteredReportDataList = getList();
        filteredReportDataList.addAll(reportDataList.stream().filter(x -> !x.isConfidential() || isSupervisor)
                .collect(Collectors.toList()));
        return filteredReportDataList;
    }

    private void UploadReport(List<String> headers, List<SalesReportData> filteredReportDataList) {
        SalesActivityReport report = this.generateReport(headers, filteredReportDataList);
        EcmService ecmService = new EcmService();
        ecmService.uploadDocument(report.toXml());
    }

    SalesActivityReport generateReport(List<String> headers, List<SalesReportData> reportDataList) {
        // TODO Auto-generated method stub
        return null;
    }

}
