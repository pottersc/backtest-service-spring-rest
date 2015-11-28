package com.potter.tools.backtest.data;

import java.util.Comparator;

/**
 * Comparator for HistoricalQuote objects that sorts by stock trade date
 * @author Scott Potter
 *
 */
public class HistoricalQuoteComparator implements Comparator<HistoricalQuote> {

    @Override
    public int compare(HistoricalQuote historicalQuote1, HistoricalQuote historicalQuote2) {
        return historicalQuote1.getDate().compareTo(historicalQuote2.getDate());
    }

}
