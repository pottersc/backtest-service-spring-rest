package com.potter.tools.backtest.data;

import java.util.Comparator;

public class HistoricalQuoteComparator implements Comparator<HistoricalQuote> {

    @Override
    public int compare(HistoricalQuote historicalQuote1, HistoricalQuote historicalQuote2) {
        return historicalQuote1.getDate().compareTo(historicalQuote2.getDate());
    }

}
