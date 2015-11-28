package com.potter.tools.backtest.calculate;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.potter.tools.backtest.data.JsonDateDeserializer;

/**
 * Backtest scenario data transfer object (follows the data transfer object pattern)
 * The data transfer object is necessary because the JSON data representing the backtest
 * scenario that is sent from the client cannot be automatically be converted by Jackson
 * into a BacktestScenario object because a factory design pattern is used to create
 * the individual IndicatorStrategy objects.
 * This may not be the most graceful way to solve this issue because additional classes
 * are required.  This code will be refactored out of a better approach is identified
 * @author Scott Potter
 *
 */
public class BacktestScenarioDTO {
    private String tickerSymbol;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private LocalDate startDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private LocalDate endDate;
    private BigDecimal startingInvestment;
    private BigDecimal transactionCost;  
    private TransactionTriggerDTO buyTrigger;
    private TransactionTriggerDTO sellTrigger;

    public BacktestScenarioDTO(){}
    
    public String getTickerSymbol() {
		return tickerSymbol;
	}
	public void setTickerSymbol(String tickerSymbol) {
		this.tickerSymbol = tickerSymbol;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public BigDecimal getStartingInvestment() {
		return startingInvestment;
	}
	public void setStartingInvestment(BigDecimal startingInvestment) {
		this.startingInvestment = startingInvestment;
	}
	public BigDecimal getTransactionCost() {
		return transactionCost;
	}
	public void setTransactionCost(BigDecimal transactionCost) {
		this.transactionCost = transactionCost;
	}
	public TransactionTriggerDTO getBuyTrigger() {
		return buyTrigger;
	}
	public void setBuyTrigger(TransactionTriggerDTO buyTrigger) {
		this.buyTrigger = buyTrigger;
	}
	public TransactionTriggerDTO getSellTrigger() {
		return sellTrigger;
	}
	public void setSellTrigger(TransactionTriggerDTO sellTrigger) {
		this.sellTrigger = sellTrigger;
	}
    
}
