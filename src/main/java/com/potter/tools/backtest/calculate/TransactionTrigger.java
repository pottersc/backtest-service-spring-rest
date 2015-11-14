package com.potter.tools.backtest.calculate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.potter.tools.backtest.calculate.TransactionTriggerDTO;
import com.potter.tools.backtest.calculate.strategy.IndicatorStrategy;
import com.potter.tools.backtest.calculate.strategy.IndicatorStrategyFactory;
import com.potter.tools.backtest.data.HistoricalQuote;
import com.potter.tools.backtest.results.Indicator;

public class TransactionTrigger {
    IndicatorStrategy firstOperandIndicatorStrategy;
    RelationalOperator relationalOperator;
    IndicatorStrategy secondOperandIndicatorStrategy;
    
    public TransactionTrigger(IndicatorStrategy firstOperandIndicatorStrategy, RelationalOperator relationalOperator, IndicatorStrategy secondOperandIndicatorStrategy) {
        super();
        this.firstOperandIndicatorStrategy = firstOperandIndicatorStrategy;
        this.relationalOperator = relationalOperator;
        this.secondOperandIndicatorStrategy = secondOperandIndicatorStrategy;
    }

    public TransactionTrigger(TransactionTriggerDTO transactionTriggerDTO) {
    	this.firstOperandIndicatorStrategy = IndicatorStrategyFactory.getIndicatorStrategy(transactionTriggerDTO.getOperand1());
    	this.relationalOperator = RelationalOperator.findByName(transactionTriggerDTO.getOperatorName());
    	this.secondOperandIndicatorStrategy = IndicatorStrategyFactory.getIndicatorStrategy(transactionTriggerDTO.getOperand2());
	}

	public RelationalOperator getRelationalOperator() {
        return relationalOperator;
    }

    public boolean isTransactionTriggerActivated(HistoricalQuote quoteInProcess, List<HistoricalQuote> historicalQuotes) {
        BigDecimal firstOperandValue = firstOperandIndicatorStrategy.calculate(quoteInProcess, historicalQuotes);
        BigDecimal secondOperandValue = secondOperandIndicatorStrategy.calculate(quoteInProcess, historicalQuotes);
        if(relationalOperator == RelationalOperator.GREATER_THAN && firstOperandValue.compareTo(secondOperandValue)==1) {
            return true;
        }
        if(relationalOperator == RelationalOperator.LESS_THAN && firstOperandValue.compareTo(secondOperandValue)==-1) {
            return true;
        }        
        if(relationalOperator == RelationalOperator.EQUALS && firstOperandValue.compareTo(secondOperandValue)==0) {
            return true;
        }        
        return false;
    }
    
    public List<Indicator> getIndicators(List<HistoricalQuote> historicalQuotes, LocalDate startDate, LocalDate endDate){
        List<Indicator> indicators = new ArrayList<Indicator>();
        indicators.add(new Indicator(firstOperandIndicatorStrategy, historicalQuotes, startDate, endDate));
        indicators.add(new Indicator(secondOperandIndicatorStrategy,historicalQuotes, startDate, endDate));
        return indicators;
    }
    
}
