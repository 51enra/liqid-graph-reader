package de.chrestin.analysis.pojos;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
public class PerformanceHistoryJson {
	
	private boolean success;
	
	@JsonProperty("model_portfolio_performance")
	private List<Return> modelPortfolioPerformance; //modelPerformance

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<Return> getModelPortfolioPerformance() {
		return modelPortfolioPerformance;
	}

	public void setModelPortfolioPerformance(List<Return> modelPortfolioPerformance) {
		this.modelPortfolioPerformance = modelPortfolioPerformance;
	}

}
