package sales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SalesApp {

    public void generateSalesActivityReport(String salesId, int maxRow, boolean isNatTrade, boolean isSupervisor) {

        SalesDao salesDao = getSalesDao();
        SalesReportDao salesReportDao = getSalesReportDao();

        List<SalesReportData> filteredReportDataList = getList();

        if (salesId == null) return;

        Sales sales = salesDao.getSalesBySalesId(salesId);

        if (isTimeOutOfRange(sales.getEffectiveFrom(), sales.getEffectiveTo())) return;

        List<SalesReportData> reportDataList = salesReportDao.getReportData(sales);

        for (SalesReportData data : reportDataList) {
            if ("SalesActivity".equalsIgnoreCase(data.getType())) {
                if (data.isConfidential()) {
                    if (isSupervisor) {
                        filteredReportDataList.add(data);
                    }
                } else {
                    filteredReportDataList.add(data);
                }
            }
        }

        List<String> headers = generateHeaders(isNatTrade);

        SalesActivityReport report = this.generateReport(headers, reportDataList);

        EcmService ecmService = new EcmService();
        ecmService.uploadDocument(report.toXml());

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
        if (isNatTrade) {
            headers.addAll(Arrays.asList("Sales ID", "Sales Name", "Activity", "Time"));
        } else {
            headers.addAll(Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time"));
        }
        return headers;

    }
    SalesActivityReport generateReport(List<String> headers, List<SalesReportData> reportDataList) {
        // TODO Auto-generated method stub
        return null;
    }

}
