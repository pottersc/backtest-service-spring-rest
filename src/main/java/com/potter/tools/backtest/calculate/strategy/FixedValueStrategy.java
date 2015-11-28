package com.potter.tools.backtest.calculate.strategy;

import java.math.BigDecimal;
import java.util.List;

import com.potter.tools.backtest.data.HistoricalQuote;

/**
 * Implementation of a fixed value strategy IndicatorStrategy
 * This is a simple indicator where a user can enter a fixed floating point value
 * to be used in any comparison of a transaction trigger.
 * For example if you wanted to buy when closing stock price > 32.5 you would use
 * the FixedValueStrategy to specify the 32.5 part of that transaction trigger
 * @author Scott Potter
 *
 */
public class FixedValueStrategy implements IndicatorStrategy {
    private static final String STRATEGY_TAG = "FIX";
    BigDecimal fixedValue = new BigDecimal(0);
    
    /**
     * Constructor for FixedValueStrategy
     * @param fixedValue : floating point number that acts as the fixed value
     */
    public FixedValueStrategy(BigDecimal fixedValue) {
        super();
        this.fixedValue = fixedValue;
    }

    @Override
    public BigDecimal calculate(HistoricalQuote quoteInProcess, List<HistoricalQuote> historicalQuotes) {
        return fixedValue;
    }
    
    @Override
    public String getName() {
        return STRATEGY_TAG + "("+fixedValue+")";
    }

	@Override
	public boolean isDisplayIndicatorStrategy() {
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fixedValue == null) ? 0 : fixedValue.hashCode());
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
		FixedValueStrategy other = (FixedValueStrategy) obj;
		if (fixedValue == null) {
			if (other.fixedValue != null)
				return false;
		} else if (!fixedValue.equals(other.fixedValue))
			return false;
		return true;
	}
	
}
