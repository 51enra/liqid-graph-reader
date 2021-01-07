package de.chrestin.analysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Store a set of dates and a corresponding map of asset names plus list of
 * month-on-month change histories per asset name.
 */

public class MonthlyPerformanceTable {

	private Set<LocalDate> monthEndDates;

	private Map<String, Map<LocalDate, Double>> changeHistories;

//	public MonthlyPerformanceTable(Set<LocalDate> monthEndDates, Map<String, Map<LocalDate, Double>> changeHistories) {
//		super();
//		this.monthEndDates = monthEndDates;
//		this.changeHistories = changeHistories;
//	}

	public MonthlyPerformanceTable() {
		super();
		this.monthEndDates = new TreeSet<>();
		this.changeHistories = new TreeMap<>();
	}

	public Set<LocalDate> getMonthEndDates() {
		return monthEndDates;
	}

	public List<String> getMonthEndDatesAsStrings() {
		List<String> datesAsStrings = new ArrayList<>();
		for (LocalDate monthEndDate : this.monthEndDates) {
			datesAsStrings.add(localDateToString(monthEndDate));
		}
		return datesAsStrings;
	}

	public Map<String, Map<LocalDate, Double>> getChangeHistories() {
		return changeHistories;
	}

	public Map<LocalDate, Double> getColumn(String assetName) {
		return this.changeHistories.get(assetName);
	}

	public Map<String, Double> getRow(LocalDate date) {
		if (this.monthEndDates.contains(date)) {
			Map<String, Double> dateRow = new TreeMap<>();
			changeHistories.forEach((k, v) -> {
				dateRow.put(k, v.get(date));
			});
			return dateRow;
		} else {
			return null;
		}
	}

	public void appendToChangeHistories(MonthlyPerformance performance) {
		Map<LocalDate, Double> history = performance.getMonthlyReturns();

		// Find dates not in current monthEndDates and add to all changeHistories
		Set<LocalDate> newDates = new TreeSet<>(history.keySet());
		newDates.removeAll(this.monthEndDates);
		this.monthEndDates.addAll(newDates);
		final Map<LocalDate, Double> addHistory = newDates.stream().collect(Collectors.toMap(k -> k, k -> Double.NaN));
		this.changeHistories.forEach((k, v) -> {
			v.putAll(addHistory);
		});
		// Find any missing dates from previous changeHistories and add to history
		Set<LocalDate> missingDates = new TreeSet<>(this.monthEndDates);
		missingDates.removeAll(history.keySet());
		Map<LocalDate, Double> knownHistory = missingDates.stream().collect(Collectors.toMap(k -> k, k -> Double.NaN));
		history.putAll(knownHistory);
		this.changeHistories.put(performance.getAssetName(), history);
	}

	public void writeToCsvFile(String qualifiedFileName) {
		File file = new File(qualifiedFileName);
		FileWriter fw = null;
		BufferedWriter bw = null;
		String lineSeparator = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();
		sb.append("Date");
		for (String assetName : this.changeHistories.keySet()) {
			sb.append(", " + assetName);
		}
		try {
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			bw.write(sb.toString() + lineSeparator);
			for (LocalDate date : this.monthEndDates) {
				StringBuilder dateLine = new StringBuilder();
				dateLine.append(localDateToString(date));
				Map<String, Double> dateRow = this.getRow(date);
				for (String assetName : dateRow.keySet()) {
					dateLine.append(", " + dateRow.get(assetName));
				}
				bw.write(dateLine + lineSeparator);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String localDateToString(LocalDate date) {
		final String datePattern = "dd.MM.yyyy";
		return date.format(DateTimeFormatter.ofPattern(datePattern));
	}

}
