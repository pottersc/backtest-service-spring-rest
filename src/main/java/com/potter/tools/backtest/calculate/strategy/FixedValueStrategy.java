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

}
