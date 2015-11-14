package com.potter.tools.backtest.calculate.strategy;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

import com.potter.tools.backtest.data.HistoricalQuote;

public class MovingAverageStrategy implements IndicatorStrategy{
    private static final String STRATEGY_TAG = "SMA";
    private static final MathContext mathContext = new MathContext(5, RoundingMode.HALF_EVEN);
    private int numberOfDays = 0;
      
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

}
