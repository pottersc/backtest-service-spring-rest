package com.potter.tools.backtest.calculate.strategy;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

import com.potter.tools.backtest.data.HistoricalQuote;


/**
 * THIS CLASS IS STILL IN DEVELOPMENT AND IS NOT YET WORKING
 * @author Scott Potter
 *
 */
public class ExponentialMovingAverageStrategy implements IndicatorStrategy{
    private static final String STRATEGY_TAG = "EMA";
    private static final MathContext mathContext = new MathContext(5, RoundingMode.HALF_EVEN);
    private int numberOfDays = 0;
      
    public ExponentialMovingAverageStrategy(int numberOfDays) {
        super();
        this.numberOfDays = numberOfDays;
    }

    @Override
    public BigDecimal calculate(HistoricalQuote quoteInProcess, List<HistoricalQuote> historicalQuotes) {
        int indexOfQuoteInProcess = historicalQuotes.indexOf(quoteInProcess);
        BigDecimal k = (new BigDecimal(2)).divide(new BigDecimal(numberOfDays - 1),mathContext);
        
        int lastBar = historicalQuotes.size() - 1;
        int firstBar = lastBar - 2 * numberOfDays + 1;
        BigDecimal ema = historicalQuotes.get(firstBar).getClose();

        for (int bar = firstBar; bar <= lastBar; bar++) {
        	BigDecimal barClose = historicalQuotes.get(bar).getClose();
        	BigDecimal emaTmp = (barClose.subtract(ema,mathContext)).multiply(k,mathContext);
            ema = ema.add(emaTmp,mathContext);
        }
              
        
        return ema;
/*        BigDecimal movingAverage = new BigDecimal("0");
        if(indexOfQuoteInProcess+1 > numberOfDays) {
            for(int historicalQuoteIndex = (indexOfQuoteInProcess-numberOfDays+1); historicalQuoteIndex<=indexOfQuoteInProcess; historicalQuoteIndex++) {
                movingAverage = movingAverage.add(historicalQuotes.get(historicalQuoteIndex).getClose());
            }
            movingAverage = movingAverage.divide(new BigDecimal(numberOfDays),mathContext);
        }   
        return movingAverage;*/
    }
    


    @Override
    public String getName() {
        return STRATEGY_TAG + "("+numberOfDays+")";
    }

	@Override
	public boolean isDisplayIndicatorStrategy() {
		return true;
	}

}
