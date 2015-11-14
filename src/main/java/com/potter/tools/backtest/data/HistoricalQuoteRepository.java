package com.potter.tools.backtest.data;

import java.util.List;


public interface HistoricalQuoteRepository {
    
    List<HistoricalQuote> importHistoricalQuotes(String tickerSymbol);

}
