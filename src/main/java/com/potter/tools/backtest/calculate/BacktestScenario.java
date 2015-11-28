package com.potter.tools.backtest.calculate;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Component;
 
/**
 * Attributes of the scenario to be executed in the backtest analysis
 * @author Scott Potter
 *
 */
@Component
public class BacktestScenario {     
    String tickerSymbol;
    LocalDate startDate;
    LocalDate endDate;
    BigDecimal startingInvestment;
    BigDecimal transactionCost;
    TransactionTrigger buyTrigger;
    TransactionTrigger sellTrigger;
    
    public BacktestScenario() {}
    
    /**
     * Constructor for BacktestScenario
     * @param tickerSymbol : ticker symbol of stock
     * @param startDate : first date for backtest analysis
     * @param endDate : last date of backtest analysis
     * @param startingInvestment : amount of money initially invested
     * @param transactionCost : cost per but / sell transaction 
     * @param buyTrigger : situation that would initiate a buy
     * @param sellTrigger : situation that would initiate a sell
     */
    public BacktestScenario(String tickerSymbol, LocalDate startDate, LocalDate endDate, BigDecimal startingInvestment, BigDecimal transactionCost, TransactionTrigger buyTrigger, TransactionTrigger sellTrigger) {
        super();
        this.tickerSymbol = tickerSymbol;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startingInvestment = startingInvestment;
        this.transactionCost = transactionCost;
        this.buyTrigger = buyTrigger;
        this.sellTrigger = sellTrigger;
    }

    /**
     * Constructor for BacktestScenario
     * @param backtestScenarioDTO : backtest scenario data transfer object
     */
    public BacktestScenario(BacktestScenarioDTO backtestScenarioDTO) {
    	this.tickerSymbol = backtestScenarioDTO.getTickerSymbol();
    	this.startDate = backtestScenarioDTO.getStartDate();
    	this.endDate = backtestScenarioDTO.getEndDate();
    	this.startingInvestment = backtestScenarioDTO.getStartingInvestment();
    	this.transactionCost = backtestScenarioDTO.getTransactionCost();
    	this.buyTrigger = new TransactionTrigger(backtestScenarioDTO.getBuyTrigger());
    	this.sellTrigger = new TransactionTrigger(backtestScenarioDTO.getSellTrigger());
	}

	public String getTickerSymbol() {
        return tickerSymbol;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BigDecimal getStartingInvestment() {
        return startingInvestment;
    }

    public BigDecimal getTransactionCost() {
        return transactionCost;
    }

    public TransactionTrigger getBuyTrigger() {
        return buyTrigger;
    }

    public TransactionTrigger getSellTrigger() {
        return sellTrigger;
    }
    
}
