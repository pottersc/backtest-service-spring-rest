package com.potter.tools.backtest;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import junitparams.JUnitParamsRunner;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.potter.tools.backtest.calculate.BacktestCalculationServiceImpl;
import com.potter.tools.backtest.calculate.BacktestScenario;
import com.potter.tools.backtest.calculate.RelationalOperator;
import com.potter.tools.backtest.calculate.TransactionTrigger;
import com.potter.tools.backtest.calculate.strategy.ClosingPriceStrategy;
import com.potter.tools.backtest.calculate.strategy.FixedValueStrategy;
import com.potter.tools.backtest.calculate.strategy.IndicatorStrategy;
import com.potter.tools.backtest.results.BacktestResults;

@RunWith(JUnitParamsRunner.class)
public class IntegrationTest {
    private static String TICKER_SYMBOL = "AAPL";
    
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
    @Ignore
    public void shouldCalculateBuyTrigger() {       
        BacktestResults backtestResults = backtestCalculationService.runAnalysis(backtestScenario);
        System.out.println(backtestResults);
        assertTrue(true);
    }
}
