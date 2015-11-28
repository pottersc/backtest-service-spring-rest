package com.potter.tools.backtest.data;

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

/**
 * Implementation of the HistoricalQuoteRepository interface using the Yahoo Finance API as 
 * specified at http://financequotes-api.com/
 * Collects historical stock data for the specified ticker symbol to build a List of 
 * HistoricalQuote objects.
 * @author Scott Potter
 *
 */
@Repository
public class HistoricalQuoteYahooFinanceRepositoryImpl implements HistoricalQuoteRepository{
    private static final Logger LOGGER = LoggerFactory.getLogger(HistoricalQuoteRepository.class);


    @Override
    public  List<com.potter.tools.backtest.data.HistoricalQuote> importHistoricalQuotes(String ticker) {
        LOGGER.debug("importAllData("+ticker+")");
        assert(ticker!=null);
        List<com.potter.tools.backtest.data.HistoricalQuote>  historicalQuotes = convertYahooHistoricalQuoteToApplicationHistoricalQuoteFormat(readYahooFinanceHistoricalQuotes(ticker));
        // sort historical quote data in ascending order by trade date
        historicalQuotes.sort(new HistoricalQuoteComparator());
        return historicalQuotes;      
    }
    
    /**
     * Utilize the Yahoo Finance API to import historical stock data 
     * @param ticker : ticker symbol of stock
     * @return : List of HistoricalQuote objects
     */
    private List<HistoricalQuote> readYahooFinanceHistoricalQuotes(String ticker){
        LOGGER.debug("readYahooFinanceHistoricalQuotes("+ticker+")");
        Calendar from = Calendar.getInstance();
        // specify the earliest date that data should be collected from
        from.set(1999, 0, 1);
        Calendar to = Calendar.getInstance();
        List<HistoricalQuote> historicalQuotes = new ArrayList<HistoricalQuote>();
        try {
            Stock stock = YahooFinance.get(ticker, from, to, Interval.DAILY);
            historicalQuotes = stock.getHistory();
        } catch (IOException e) {
            throw new RuntimeException("HistoricalQuoteYahooFinanceRepositoryImpl.readYahooFinanceHistoricalQuotes("+ticker+") Failed.  Insure that ticker symbol is correct.");
        }
        if(historicalQuotes==null || historicalQuotes.size()==0) {
            throw new RuntimeException("HistoricalQuoteYahooFinanceRepositoryImpl.readYahooFinanceHistoricalQuotes("+ticker+") Returned zero elements.  Insure that ticker symbol is correct.");
        }
        return historicalQuotes;
    }
    
    /**
     * convert the Yahoo Finance HistoricalQuote objects to the backtest application HistoricalQuote object
     * Yahoo Finance and the backtest application both use the same name for their HistoricalQuote objects,
     * but the objects are different and so this method converts to the backtest implementations  
     * @param yahooHistoricalQuotes
     * @return
     */
    private List<com.potter.tools.backtest.data.HistoricalQuote> convertYahooHistoricalQuoteToApplicationHistoricalQuoteFormat(List<HistoricalQuote> yahooHistoricalQuotes){
        LOGGER.debug("convertYahooHistoricalQuoteToApplicationHistoricalQuoteFormat()");
        List<com.potter.tools.backtest.data.HistoricalQuote> applicationHistoricalQuotes = new ArrayList<com.potter.tools.backtest.data.HistoricalQuote>();
        for(HistoricalQuote yahooHistoricalQuote : yahooHistoricalQuotes) {
            applicationHistoricalQuotes.add( new com.potter.tools.backtest.data.HistoricalQuote(
                    yahooHistoricalQuote.getSymbol(), 
                    yahooHistoricalQuote.getDate().toInstant().atZone(ZoneId.of("UTC")).toLocalDate(), 
                    yahooHistoricalQuote.getAdjClose(), 
                    yahooHistoricalQuote.getVolume()));
        }
        return applicationHistoricalQuotes;
    }

}
