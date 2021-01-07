package de.chrestin.analysis;

public class JsonExtractor {

	private final static String ROOT_PATH = "/home/sea1/Dokumente/json/";

	public static void main(String[] args) {

		final String[][] benchmark = { { "benchmark/", "benchmark_", "38" }, { "benchmark/", "benchmark_", "39" },
				{ "benchmark/", "benchmark_", "40" }, { "benchmark/", "vermoegensverwalter_", "1" },
				{ "benchmark/", "vermoegensverwalter_", "2" }, { "benchmark/", "vermoegensverwalter_", "3" },
				{ "benchmark/", "vermoegensverwalter_", "4" } };

		// full filename path example:
		// "performance/liqid_global/liqid_global_5_performance.json"
		final String[][] performanceFile = { { "performance/", "liqid_global", "_5" },
				{ "performance/", "liqid_global", "_6" }, { "performance/", "liqid_global", "_7" },
				{ "performance/", "liqid_global", "_8" }, { "performance/", "liqid_global", "_9" }, //
				{ "performance/", "liqid_global_impact", "_5" }, { "performance/", "liqid_global_impact", "_6" },
				{ "performance/", "liqid_global_impact", "_7" }, { "performance/", "liqid_global_impact", "_8" },
				{ "performance/", "liqid_global_impact", "_9" }, //
				{ "performance/", "liqid_index", "_5" }, { "performance/", "liqid_index", "_6" },
				{ "performance/", "liqid_index", "_7" }, { "performance/", "liqid_index", "_8" },
				{ "performance/", "liqid_index", "_9" }, //
				{ "performance/", "liqid_select", "_5" }, { "performance/", "liqid_select", "_6" },
				{ "performance/", "liqid_select", "_7" }, { "performance/", "liqid_select", "_8" },
				{ "performance/", "liqid_select", "_9" } //
		};

		// **************************************************************************************************
		// Read multiple portfolio json files and write to one csv
//		int offset = 0;
//		MonthlyPerformanceTable performanceTable = new MonthlyPerformanceTable();
//		StringBuilder filePath = new StringBuilder();
//		filePath.append(ROOT_PATH)//
//				.append(performanceFile[offset][0])//
//				.append("/")//
//				.append(performanceFile[offset][1])//
//				.append("/");
//		for (int i = 0; i < 5; i++) {
//			String assetName = performanceFile[offset][1] + performanceFile[i + offset][2];
//			String filename = filePath.toString() + assetName + "_performance.json";
//			MonthlyPerformance performance = MonthlyPerformance.readFromPerformanceFile(assetName, filename);
//			performanceTable.appendToChangeHistories(performance);
//		}
//		performanceTable.writeToCsvFile(ROOT_PATH + "performance/test/liqid_global_performance.csv");

		// **************************************************************************************************
		// Write multiple benchmark json files and write to one csv
//		int offset = 0; // 3 for "vermoegensverwalter"
//		int members = 3; // 4 for "vermoegensverwalter"
//		StringBuilder filePath = new StringBuilder();
//		filePath.append(ROOT_PATH)//
//				.append(benchmark[offset][0]);
//		MonthlyPerformanceTable performanceTable = new MonthlyPerformanceTable();
//		for (int i = 0; i < members; i++) {
//			StringBuilder fileName = new StringBuilder(filePath);
//			fileName.append(benchmark[i + offset][1]).append(benchmark[i + offset][2]).append(".json");
//			MonthlyPerformance performance = MonthlyPerformance.readFromBenchmarkFile(fileName.toString());
//			performanceTable.appendToChangeHistories(performance);
//		}
//		performanceTable.writeToCsvFile(ROOT_PATH + "performance/test/" + benchmark[offset][1] + ".csv");

		// **************************************************************************************************
		// Read liqid portfolios from web. offset = 0, 5, 10, 15.
		int offset = 15;
		MonthlyPerformanceTable performanceTable = new MonthlyPerformanceTable();
		for (int i = 0; i < 5; i++) {
			String modelPortfolioName = performanceFile[i + offset][1] + performanceFile[i + offset][2];
			MonthlyPerformance performance = MonthlyPerformance.readModelPortfolioFromWeb(modelPortfolioName,
					modelPortfolioName);
			performanceTable.appendToChangeHistories(performance);
		}

		StringBuilder filename = new StringBuilder();
		filename.append(ROOT_PATH).append(performanceFile[offset][0]).append(performanceFile[offset][1]).append(".csv");

		performanceTable.writeToCsvFile(filename.toString());

		// **************************************************************************************************
		// Read benchmark from web
//		int offset = 0; // 3 for "vermoegensverwalter"
//		int members = 3; // 4 for "vermoegensverwalter"
//		MonthlyPerformanceTable performanceTable = new MonthlyPerformanceTable();
//		for (int i = 0; i < members; i++) {
//			int benchmarkId = Integer.valueOf(benchmark[i + offset][2]);
//			MonthlyPerformance performance = MonthlyPerformance.readBenchmarkFromWeb(benchmarkId);
//			performanceTable.appendToChangeHistories(performance);
//		}
//		StringBuilder filename = new StringBuilder();
//		filename.append(ROOT_PATH).append(benchmark[offset][0]).append(benchmark[offset][1]).append(".csv");
//
//		performanceTable.writeToCsvFile(filename.toString());

		// **************************************************************************************************
		// Print first 10 values
//		List<Return> returnHistory = performance.getReturnHistory();
//		for (int i = 0; i < 10; i++) {
//			System.out.print(returnHistory.get(i).getDateString() + "  ");
//			System.out.printf("%3.2f%n", (returnHistory.get(i).getM2mValueChange()));
//		}

	}
}
