package de.chrestin.analysis;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.chrestin.analysis.pojos.Benchmark;
import de.chrestin.analysis.pojos.MultiBenchmarkJson;
import de.chrestin.analysis.pojos.PerformanceHistoryJson;
import de.chrestin.analysis.pojos.Return;

public class MonthlyPerformance {

	private String assetName;
	private Map<LocalDate, Double> monthlyReturns;

	public MonthlyPerformance(String assetName, List<Return> returnHistory) {
		this.assetName = assetName;
		this.monthlyReturns = returnHistoryToMap(returnHistory);
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public Map<LocalDate, Double> getMonthlyReturns() {
		return monthlyReturns;
	}

	public void setMonthlyReturns(Map<LocalDate, Double> monthlyReturns) {
		this.monthlyReturns = monthlyReturns;
	}
	

	public static MonthlyPerformance readFromPerformanceFile(String assetName, String qualifiedFileName) {

		ObjectMapper mapper = new ObjectMapper();
		File file = new File(qualifiedFileName);
		PerformanceHistoryJson history = new PerformanceHistoryJson();
		try {
			history = mapper.readValue(file, PerformanceHistoryJson.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new MonthlyPerformance(assetName, history.getModelPortfolioPerformance());
	}
	

	public static MonthlyPerformance readModelPortfolioFromWeb(String assetName, String portfolioName) {

		RestApi restApi = RestApi.getInstance();
		PerformanceHistoryJson history = restApi.getModelPortfolio(portfolioName);

		return new MonthlyPerformance(assetName, history.getModelPortfolioPerformance());
	}
	

	public static MonthlyPerformance readFromBenchmarkFile(String qualifiedFileName) {
		ObjectMapper mapper = new ObjectMapper();
		File file = new File(qualifiedFileName);
		MultiBenchmarkJson multiBenchmark = new MultiBenchmarkJson();

		try {
			multiBenchmark = mapper.readValue(file, MultiBenchmarkJson.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Benchmark benchmark = multiBenchmark.getBenchmarkList().get(0);

		return new MonthlyPerformance(benchmark.getName(), benchmark.getHistoricalReturns());
	}
	

	public static MonthlyPerformance readBenchmarkFromWeb(int benchmarkId) {

		RestApi restApi = RestApi.getInstance();
		Benchmark benchmark = restApi.getBenchmark(benchmarkId).getBenchmarkList().get(0);

		return new MonthlyPerformance(benchmark.getName(), benchmark.getHistoricalReturns());
	}

	private Map<LocalDate, Double> returnHistoryToMap(List<Return> returnHistory) {
		Map<LocalDate, Double> history = new TreeMap<>();
		for (Return returnValue : returnHistory) {
			history.put(stringToLocalDate(returnValue.getDateString()), returnValue.getM2mValueChange());
		}
		return history;
	}

	private LocalDate stringToLocalDate(String dateString) {
		// Accepts dateString in "standard format" yyyy-mm-dd.
		// Possible later extension to support other formatting

		return LocalDate.parse(dateString);
	}

}
