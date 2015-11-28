package com.potter.tools.backtest.calculate.strategy;

import java.math.BigDecimal;
import java.util.List;

import com.potter.tools.backtest.data.HistoricalQuote;

/**
 * Default strategy to be defined if no other strategies are found
 * This is an implementation of the 'Null Object Pattern'
 * @author Scott Potter
 *
 */
public class NullStrategy implements IndicatorStrategy{
    private static final String STRATEGY_TAG = "NULL_STRATEGY";
    
    @Override
    public BigDecimal calculate(HistoricalQuote quoteInProcess, List<HistoricalQuote> historicalQuotes) {
        return new BigDecimal(0);
    }

    @Override
    public String getName() {
        return STRATEGY_TAG;
    }

	@Override
	public boolean isDisplayIndicatorStrategy() {
		return false;
	}

}
