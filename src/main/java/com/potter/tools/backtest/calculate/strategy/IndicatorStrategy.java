package com.potter.tools.backtest.calculate.strategy;

import java.math.BigDecimal;
// SMA (Simple Moving Average) : period(int)
// EMA (Exponential Moving Average) : period (int)
// BB (Bollinger Bands) : period(int), stdDev(int)
import java.util.List;

import com.potter.tools.backtest.data.HistoricalQuote;

/**
 * Interface for defining a stock trading indicator strategy.
 * This is using the strategy design pattern such that new stock indicators can be added in the future 
 * with the only additional required changes being to the IndicatorStrategyFactory class
 * 
 * Additional IndicatorStrategy implementations that are recommended to add to this application are:
 * Exponential Moving Average (EMA)
 * Moving Average Convergence Divergence (MACD)
 * Relative Strength Index (RSI)
 * Bollinger Bands (BB)
 * Stochastic Oscillator (SO)
 * @author Scott Potter
 *
 */
public interface IndicatorStrategy {
    
	/**
	 * Calculate and return the IndicatorStrategy value (ie what is the 50 day moving average) for the 
	 * specified 'quoteInProcess' entry.  Most of the calculated indicator strategy values are determined
	 * from a history of stock prices leading up to the calculation date in question, which is why the 
	 * list of historical quotes is passed in.  
	 * @param quoteInProcess : histricalQuote (ie date) that the indicator is to be calculated for
	 * @param historicalQuotes : list of historical stock price information
	 * @return
	 */
    public BigDecimal calculate(HistoricalQuote quoteInProcess, List<HistoricalQuote> historicalQuotes);
    
    /**
     * The display name for the indicatorStrategy object
     * for example the 'name' of a 50 day simple moving average indicator would be 'SMA(50)' and this
     * name would be used in the legend of the associated indicator line in the display chart
     * @return
     */
    public String getName();
    
    /**
     * Indicates if an indicator strategy should be included in the resultData object.
     * Most strategies would be included, but special ones like 'ClosingPriceStrategy' may not be 
     * included because the information is already available as the TradyDay.stockPrice value  
     * @return
     */
    public boolean isDisplayIndicatorStrategy();

}
