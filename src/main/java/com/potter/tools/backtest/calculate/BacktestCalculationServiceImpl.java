package com.potter.tools.backtest.calculate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.potter.tools.backtest.data.HistoricalQuote;
import com.potter.tools.backtest.data.HistoricalQuoteRepository;
import com.potter.tools.backtest.results.BacktestResults;
import com.potter.tools.backtest.results.TradeDay;

/**
 * Implementation of BacktestCalculationService 
 * This is the heart of the backtest analysis and is responsible for 
 * synchronizing and executing the backtest analysis algorithm
 * @author Scott Potter
 *
 */
@Service("backtestCalculationService")
public class BacktestCalculationServiceImpl implements BacktestCalculationService{
    @Autowired
    private HistoricalQuoteRepository historicalQuoteRepository;  
    
    @Override
    public BacktestResults runAnalysis(BacktestScenario backtestScenario) {
        assert (historicalQuoteRepository!=null);
        assert (backtestScenario.getTickerSymbol()!=null);
        BacktestResults backtestResults = new BacktestResults(); 
        BigDecimal investableCash = backtestScenario.getStartingInvestment();
        BigDecimal numberSharesOwned = new BigDecimal(0);
        boolean tradingEnabled = false;
        // collect historical quotes from a historical quote repository such as yahoo finance API
        List<HistoricalQuote> historicalQuotes = historicalQuoteRepository.importHistoricalQuotes(backtestScenario.getTickerSymbol());
        // process each day of the historical quotes
        for(HistoricalQuote quote:historicalQuotes) {
            if(isDateInRange(quote.getDate(),backtestScenario.getStartDate(),backtestScenario.getEndDate())) {
                TradeDay tradeDay = new TradeDay(quote.getDate(), quote.getClose());
                boolean buySignal = backtestScenario.getBuyTrigger().isTransactionTriggerActivated(quote, historicalQuotes) && tradingEnabled;
                boolean sellSignal = backtestScenario.getSellTrigger().isTransactionTriggerActivated(quote, historicalQuotes);       
                // trading is not enabled until we are in a SELL state, so that the first BUY is triggered on the date that state switches from SELL to BUY
                if(sellSignal && !tradingEnabled){
                	tradingEnabled = true;
                }
                tradeDay.execute(numberSharesOwned, buySignal, sellSignal, investableCash, backtestScenario.getTransactionCost());
                backtestResults.addTradeDay(tradeDay);
                numberSharesOwned = tradeDay.getNumberSharesOwned();
                investableCash = tradeDay.getInvestableCash();    
            }
        }
        backtestResults.addIndicators(backtestScenario.getBuyTrigger().getIndicators(historicalQuotes, backtestScenario.getStartDate(), backtestScenario.getEndDate()));
        backtestResults.addIndicators(backtestScenario.getSellTrigger().getIndicators(historicalQuotes, backtestScenario.getStartDate(), backtestScenario.getEndDate()));
        return backtestResults;
    }
    
    /**
     * Helper method that determines if the specified date is between startDate and endDate
     * @param date : date to be tested
     * @param startDate : start date
     * @param endDate : end date
     * @return : true if startDate > date < endDate; false otherwise
     */
    private boolean isDateInRange(LocalDate date, LocalDate startDate, LocalDate endDate){
    	return (date.compareTo(startDate)>=0 && date.compareTo(endDate)<=0);
    }

}
