package com.potter.tools.backtest.data;

import java.util.ArrayList;
import java.util.List;

public class HistoricalQuotesCollection{
    List<HistoricalQuote> historicalQuotes = new ArrayList<HistoricalQuote>();

    public List<HistoricalQuote> getHistoricalQuotes() {
        return historicalQuotes;
    }

    public void setHistoricalQuotes(List<HistoricalQuote> historicalQuotes) {
        this.historicalQuotes = historicalQuotes;
    }
    
}
