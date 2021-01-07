package de.chrestin.analysis.pojos;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
public class MultiBenchmarkJson {
	
	@JsonProperty("benchmarks")
	private List<Benchmark> benchmarkList;

	public List<Benchmark> getBenchmarkList() {
		return benchmarkList;
	}

	public void setBenchmark(List<Benchmark> benchmarkList) {
		this.benchmarkList = benchmarkList;
	}
}
