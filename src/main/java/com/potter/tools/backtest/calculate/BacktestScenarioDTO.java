package com.potter.tools.backtest.calculate;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.potter.tools.backtest.data.JsonDateDeserializer;

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
