package com.potter.tools.backtest.data;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class HistoricalQuoteComparatorTest {
    private static final String TICKER_SYMBOL = "MSI";
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    @Test
    public void shouldOrderHistoricalQuotesInAscendingOrderByDate() {
        List<HistoricalQuote> historicalQuotes = new ArrayList<HistoricalQuote>();
        historicalQuotes.add(new HistoricalQuote(TICKER_SYMBOL, "2015-06-02", 51));
        historicalQuotes.add(new HistoricalQuote(TICKER_SYMBOL, "2015-06-03",50.5));
        historicalQuotes.add(new HistoricalQuote(TICKER_SYMBOL, "2015-06-01",49));
        
        historicalQuotes.sort(new HistoricalQuoteComparator());
        assertEquals(historicalQuotes.get(0).getDate(), LocalDate.parse("2015-06-01",dateTimeFormatter));
        assertEquals(historicalQuotes.get(1).getDate(), LocalDate.parse("2015-06-02",dateTimeFormatter));
        assertEquals(historicalQuotes.get(2).getDate(), LocalDate.parse("2015-06-03",dateTimeFormatter));
    }

}
