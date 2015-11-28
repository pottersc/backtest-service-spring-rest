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

/**
 * Defines a situation in which a stock buy or sell transaction would be triggered.
 * An example of a transaction trigger could be buy when ClosingPrice > 50 day SimpleMovingAverage
 * An 'operand' is and indicatorStrategy on the left or right of a relations operator
 * In the above example 'Closing price' is the firstOperandIndicatorStrategy and 
 * '50 day SimpleMovingAverage' is the secondOperandIndicatorStrategy
 * while '>' is the relationalOperator
 * @author Scott Potter
 *
 */
public class TransactionTrigger {
    IndicatorStrategy firstOperandIndicatorStrategy;
    RelationalOperator relationalOperator;
    IndicatorStrategy secondOperandIndicatorStrategy;
    
    /**
     * Constructor for the transactionTrigger
     * @param firstOperandIndicatorStrategy : indicator strategy to the left of the relational operator
     * @param relationalOperator : >,  <, ==
     * @param secondOperandIndicatorStrategy : indicator strategy to the right of the relational operator
     */
    public TransactionTrigger(IndicatorStrategy firstOperandIndicatorStrategy, RelationalOperator relationalOperator, IndicatorStrategy secondOperandIndicatorStrategy) {
        super();
        this.firstOperandIndicatorStrategy = firstOperandIndicatorStrategy;
        this.relationalOperator = relationalOperator;
        this.secondOperandIndicatorStrategy = secondOperandIndicatorStrategy;
    }

    /**
     * Constructor for the transactionTrigger
     * @param transactionTriggerDTO : Data Transfer object representing the TransactionTrigger
     */
    public TransactionTrigger(TransactionTriggerDTO transactionTriggerDTO) {
    	this.firstOperandIndicatorStrategy = IndicatorStrategyFactory.getIndicatorStrategy(transactionTriggerDTO.getOperand1());
    	this.relationalOperator = RelationalOperator.findByName(transactionTriggerDTO.getOperatorName());
    	this.secondOperandIndicatorStrategy = IndicatorStrategyFactory.getIndicatorStrategy(transactionTriggerDTO.getOperand2());
	}

	public RelationalOperator getRelationalOperator() {
        return relationalOperator;
    }

	/**
	 * Determine if a transaction trigger has been activated for the specified quote
	 * @param quoteInProcess : quote date to be assessed
	 * @param historicalQuotes : list of all historical quotes
	 * @return
	 */
    public boolean isTransactionTriggerActivated(HistoricalQuote quoteInProcess, List<HistoricalQuote> historicalQuotes) {
    	// calculate the indicator strategy values
        BigDecimal firstOperandValue = firstOperandIndicatorStrategy.calculate(quoteInProcess, historicalQuotes);
        BigDecimal secondOperandValue = secondOperandIndicatorStrategy.calculate(quoteInProcess, historicalQuotes);
        // test triggers for the appropriate relational operator
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
    
    /**
     * return the list of indicators used by this transaction trigger
     * this is used to add to the BacktestResults object for chart generation
     * @param historicalQuotes : list of historical stock data
     * @param startDate : first trading date
     * @param endDate : last trading date
     * @return
     */
    public List<Indicator> getIndicators(List<HistoricalQuote> historicalQuotes, LocalDate startDate, LocalDate endDate){
        List<Indicator> indicators = new ArrayList<Indicator>();
        indicators.add(new Indicator(firstOperandIndicatorStrategy, historicalQuotes, startDate, endDate));
        indicators.add(new Indicator(secondOperandIndicatorStrategy,historicalQuotes, startDate, endDate));
        return indicators;
    }
    
}
