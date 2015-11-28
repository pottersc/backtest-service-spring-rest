package com.potter.tools.backtest.calculate;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import junitparams.JUnitParamsRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.potter.tools.backtest.calculate.strategy.ClosingPriceStrategy;
import com.potter.tools.backtest.calculate.strategy.FixedValueStrategy;
import com.potter.tools.backtest.calculate.strategy.IndicatorStrategy;
import com.potter.tools.backtest.data.HistoricalQuote;
import com.potter.tools.backtest.data.HistoricalQuoteRepository;
import com.potter.tools.backtest.results.BacktestResults;

@RunWith(JUnitParamsRunner.class)
public class BacktestScenarioTest {
    private static String TICKER_SYMBOL = "MSI";
    
    @Mock
    private HistoricalQuoteRepository mockHistoricalQuoteRepository;
    
    @InjectMocks
    private BacktestCalculationServiceImpl backtestCalculationService;    
    
    BacktestScenario backtestScenario;
    
    @Before
    public void setup() {
        IndicatorStrategy buyStrategyFirstOperand = new ClosingPriceStrategy();
        RelationalOperator buyOperator = RelationalOperator.LESS_THAN;
        IndicatorStrategy buyStrategySecondOperand = new FixedValueStrategy(new BigDecimal(50));
        
        IndicatorStrategy sellStrategyFirstOperand = new ClosingPriceStrategy();
        RelationalOperator sellOperator = RelationalOperator.GREATER_THAN;
        IndicatorStrategy sellStrategySecondOperand = new FixedValueStrategy(new BigDecimal(55));
        
        BigDecimal startingInvestment = new BigDecimal("10000");
        BigDecimal transactionCost = new BigDecimal("5");
        
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse("2015-06-01",dtf);
        LocalDate endDate = LocalDate.parse("2015-07-01",dtf);
        
        TransactionTrigger buyTrigger = new TransactionTrigger(buyStrategyFirstOperand, buyOperator, buyStrategySecondOperand);
        TransactionTrigger sellTrigger = new TransactionTrigger(sellStrategyFirstOperand, sellOperator, sellStrategySecondOperand);
        
        backtestScenario = new BacktestScenario(TICKER_SYMBOL, startDate, endDate, startingInvestment, transactionCost, buyTrigger, sellTrigger);        
        
        MockitoAnnotations.initMocks(this);  
    }
    
    
    @Test
    public void shouldCalculateBuyTrigger() {       
        List<HistoricalQuote> historicalQuotes = new ArrayList<HistoricalQuote>();
        historicalQuotes.add(new HistoricalQuote(TICKER_SYMBOL, "2015-06-01", 51));
        historicalQuotes.add(new HistoricalQuote(TICKER_SYMBOL, "2015-06-02",50.5));
        historicalQuotes.add(new HistoricalQuote(TICKER_SYMBOL, "2015-06-03",49));
        historicalQuotes.add(new HistoricalQuote(TICKER_SYMBOL, "2015-06-04",50));
        historicalQuotes.add(new HistoricalQuote(TICKER_SYMBOL, "2015-06-05",51));
        historicalQuotes.add(new HistoricalQuote(TICKER_SYMBOL, "2015-06-06",52));
        historicalQuotes.add(new HistoricalQuote(TICKER_SYMBOL, "2015-06-07",53));
        historicalQuotes.add(new HistoricalQuote(TICKER_SYMBOL, "2015-06-08",54));
        historicalQuotes.add(new HistoricalQuote(TICKER_SYMBOL, "2015-06-09",55));
        historicalQuotes.add(new HistoricalQuote(TICKER_SYMBOL, "2015-06-10",56));
        historicalQuotes.add(new HistoricalQuote(TICKER_SYMBOL, "2015-06-11",57));
        
        given(mockHistoricalQuoteRepository.importHistoricalQuotes(TICKER_SYMBOL)).willReturn(historicalQuotes);
        
        BacktestResults backtestResults = backtestCalculationService.runAnalysis(backtestScenario);
        System.out.println(backtestResults);
        assertTrue(true);
    }

}
