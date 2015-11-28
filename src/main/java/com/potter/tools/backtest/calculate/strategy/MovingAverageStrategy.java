package com.potter.tools.backtest.calculate.strategy;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

import com.potter.tools.backtest.data.HistoricalQuote;

/**
 * Implementation of a simple moving average strategy IndicatorStrategy
 * Simple Moving Average strategy is just the average of the past N
 * days closing stock price.  It is a very popular trading strategy for smoothing
 * out stock price changes.
 * @author Scott Potter
 *
 */
public class MovingAverageStrategy implements IndicatorStrategy{
    private static final String STRATEGY_TAG = "SMA";
    private static final MathContext mathContext = new MathContext(5, RoundingMode.HALF_EVEN);
    private int numberOfDays = 0;
      
    /**
     * Constructor for MovingAverageStrategy
     * @param numberOfDays : number of historical days to calculate average from
     */
    public MovingAverageStrategy(int numberOfDays) {
        super();
        this.numberOfDays = numberOfDays;
    }

    @Override
    public BigDecimal calculate(HistoricalQuote quoteInProcess, List<HistoricalQuote> historicalQuotes) {
        int indexOfQuoteInProcess = historicalQuotes.indexOf(quoteInProcess);
        BigDecimal movingAverage = new BigDecimal("0");
        if(indexOfQuoteInProcess+1 > numberOfDays) {
            for(int historicalQuoteIndex = (indexOfQuoteInProcess-numberOfDays+1); historicalQuoteIndex<=indexOfQuoteInProcess; historicalQuoteIndex++) {
                movingAverage = movingAverage.add(historicalQuotes.get(historicalQuoteIndex).getClose());
            }
            movingAverage = movingAverage.divide(new BigDecimal(numberOfDays),mathContext);
        }   
        return movingAverage;
    }

    @Override
    public String getName() {
        return STRATEGY_TAG + "("+numberOfDays+")";
    }

	@Override
	public boolean isDisplayIndicatorStrategy() {
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numberOfDays;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovingAverageStrategy other = (MovingAverageStrategy) obj;
		if (numberOfDays != other.numberOfDays)
			return false;
		return true;
	}	

}
