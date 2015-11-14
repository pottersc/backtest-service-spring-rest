package com.potter.tools.backtest.calculate;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.potter.tools.backtest.data.HistoricalQuote;
import com.potter.tools.backtest.data.HistoricalQuoteRepository;
import com.potter.tools.backtest.results.BacktestResults;
import com.potter.tools.backtest.results.TradeDay;

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
        List<HistoricalQuote> historicalQuotes = historicalQuoteRepository.importHistoricalQuotes(backtestScenario.getTickerSymbol());
        for(HistoricalQuote quote:historicalQuotes) {
            if(quote.getDate().compareTo(backtestScenario.getStartDate())>=0 && quote.getDate().compareTo(backtestScenario.getEndDate())<=0) {
                TradeDay tradeDay = new TradeDay(quote.getDate(), quote.getClose());
                boolean buySignal = backtestScenario.getBuyTrigger().isTransactionTriggerActivated(quote, historicalQuotes);
                boolean sellSignal = backtestScenario.getSellTrigger().isTransactionTriggerActivated(quote, historicalQuotes);                
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

}
