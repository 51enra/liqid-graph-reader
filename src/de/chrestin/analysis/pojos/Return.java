package de.chrestin.analysis.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Return {

	@JsonProperty("d")
	private String dateString; // dateString

	@JsonProperty("q")
	private double portfolioValue; // current portfolio value at date; ignored by Jackson if not present

	@JsonProperty("r")
	private double m2mValueChange; // monthly change of value

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public double getPortfolioValue() {
		return portfolioValue;
	}

	public void setPortfolioValue(double portfolioValue) {
		this.portfolioValue = portfolioValue;
	}

	public double getM2mValueChange() {
		return m2mValueChange;
	}

	public void setM2mValueChange(double m2mValueChange) {
		this.m2mValueChange = m2mValueChange;
	}

}
