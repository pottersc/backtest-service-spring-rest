package com.potter.tools.backtest.calculate.strategy;

import java.math.BigDecimal;
import java.util.List;

import com.potter.tools.backtest.data.HistoricalQuote;

/**
 * Implementation of a closing price strategy IndicatorStrategy
 * Simple strategy that is just the stock's closing price for the specified quote date
 * @author Scott Potter
 *
 */
public class ClosingPriceStrategy implements IndicatorStrategy{
    private static final String STRATEGY_TAG = "CLOSE";
    
    @Override
    public BigDecimal calculate(HistoricalQuote quoteInProcess, List<HistoricalQuote> historicalQuotes) {
        return quoteInProcess.getClose();
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
