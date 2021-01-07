package de.chrestin.analysis.pojos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Benchmark {
	
	@JsonProperty("benchmark_id")
	private Long benchmarkId;
	
	private String name;
	
	@JsonProperty("localized_names")
	private String localizedNames;
	
	private String slug; //Datenquelle?
	
	@JsonProperty("historical_returns")
	private List<Return> historicalReturns;

	public Long getBenchmarkId() {
		return benchmarkId;
	}

	public void setBenchmarkId(Long benchmarkId) {
		this.benchmarkId = benchmarkId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocalizedNames() {
		return localizedNames;
	}

	public void setLocalizedNames(String localizedNames) {
		this.localizedNames = localizedNames;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public List<Return> getHistoricalReturns() {
		return historicalReturns;
	}

	public void setHistoricalReturns(List<Return> historicalReturns) {
		this.historicalReturns = historicalReturns;
	}

}
