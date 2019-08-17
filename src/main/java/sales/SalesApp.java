package sales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SalesApp {

	public void generateSalesActivityReport(String salesId, int maxRow, boolean isNatTrade, boolean isSupervisor) {
		
		SalesDao salesDao = getSalesDao();
		SalesReportDao salesReportDao = getSalesReportDao();
		List<String> headers = getHeaders();
		
		List<SalesReportData> filteredReportDataList = getList();

		if (salesId == null) {
			return;
		}
		
		Sales sales = salesDao.getSalesBySalesId(salesId);
		
		Date today = getToday();
		if (today.after(sales.getEffectiveTo())
				|| today.before(sales.getEffectiveFrom())){
			return;
		}
		
		List<SalesReportData> reportDataList = salesReportDao.getReportData(sales);
		
		for (SalesReportData data : reportDataList) {
			if ("SalesActivity".equalsIgnoreCase(data.getType())) {
				if (data.isConfidential()) {
					if (isSupervisor) {
						filteredReportDataList.add(data);
					}
				}else {
					filteredReportDataList.add(data);
				}
			}
		}
		
		if (isNatTrade) {
			headers.addAll(Arrays.asList("Sales ID", "Sales Name", "Activity", "Time"));
		} else {
			headers.addAll(Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time"));
		}
		
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
	SalesActivityReport generateReport(List<String> headers, List<SalesReportData> reportDataList) {
		// TODO Auto-generated method stub
		return null;
	}

}
