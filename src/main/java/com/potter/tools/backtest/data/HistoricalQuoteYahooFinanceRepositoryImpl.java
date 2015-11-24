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

@Repository
public class HistoricalQuoteYahooFinanceRepositoryImpl implements HistoricalQuoteRepository{
    private static final Logger LOGGER = LoggerFactory.getLogger(HistoricalQuoteRepository.class);

    @Override
    public  List<com.potter.tools.backtest.data.HistoricalQuote> importHistoricalQuotes(String ticker) {
        LOGGER.debug("importAllData("+ticker+")");
        List<com.potter.tools.backtest.data.HistoricalQuote>  historicalQuotes = convertYahooHistoricalQuoteToApplicationHistoricalQuoteFormat(readYahooFinanceHistoricalQuotes(ticker));
        historicalQuotes.sort(new HistoricalQuoteComparator());
        return historicalQuotes;      
    }
    
    private List<HistoricalQuote> readYahooFinanceHistoricalQuotes(String ticker){
        LOGGER.debug("readYahooFinanceHistoricalQuotes("+ticker+")");
        Calendar from = Calendar.getInstance();
        from.set(1999, 0, 1);
   //     from.add(Calendar.YEAR, -5);
      //  from.add(Calendar.MONTH, -1);
        Calendar to = Calendar.getInstance();
     //   to.set(2011, 0, 6);
     //   to.add(Calendar.YEAR, -5);
      //  to.add(Calendar.DATE, 5);
        List<HistoricalQuote> historicalQuotes = new ArrayList<HistoricalQuote>();
        try {
            Stock stock = YahooFinance.get(ticker, from, to, Interval.DAILY);
            System.out.println("stock="+stock+", stats="+stock.getStats());
            historicalQuotes = stock.getHistory();
        } catch (IOException e) {
            throw new RuntimeException("HistoricalQuoteYahooFinanceRepositoryImpl.readYahooFinanceHistoricalQuotes("+ticker+") Failed.  Insure that ticker symbol is correct.");
        }
        if(historicalQuotes==null || historicalQuotes.size()==0) {
            throw new RuntimeException("HistoricalQuoteYahooFinanceRepositoryImpl.readYahooFinanceHistoricalQuotes("+ticker+") Returned zero elements.  Insure that ticker symbol is correct.");
        }
        return historicalQuotes;
    }
    
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
