package com.potter.tools.backtest.data;

import java.util.List;

/**
 * Interface for collecting historical stock quotes from an external source
 * The interface approach allows other data sources to be supported in the future with minimal code changes
 * 
 * @author Scott Potter
 */
public interface HistoricalQuoteRepository {
    
	/**
	 * Import the historical stock data for the specified ticker symbol
	 * @param tickerSymbol : ticker symbol of the stock
	 * @return : List of HistoricalQuote objects (one for each trade day)
	 */
    List<HistoricalQuote> importHistoricalQuotes(String tickerSymbol);

}
