package com.potter.tools.backtest.calculate.strategy;

import java.math.BigDecimal;
import java.util.List;

import com.potter.tools.backtest.data.HistoricalQuote;

public class FixedValueStrategy implements IndicatorStrategy {
    private static final String STRATEGY_TAG = "FIX";
    BigDecimal fixedValue = new BigDecimal(0);
     
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
