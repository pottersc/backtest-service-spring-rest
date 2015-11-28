package com.potter.tools.backtest.calculate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import junitparams.JUnitParamsRunner;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.potter.tools.backtest.calculate.strategy.ClosingPriceStrategy;
import com.potter.tools.backtest.calculate.strategy.FixedValueStrategy;
import com.potter.tools.backtest.calculate.strategy.IndicatorStrategy;
import com.potter.tools.backtest.data.HistoricalQuote;

@RunWith(JUnitParamsRunner.class)
public class TransactionTriggerTest {
    @Rule 
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    
    List<HistoricalQuote> HISTORICAL_QUOTES = new ArrayList<HistoricalQuote>();
    HistoricalQuote HISTORICAL_QUOTE = null;
    BigDecimal TRIGGER_VALUE = new BigDecimal(50.5); 
    BigDecimal BELOW_TRIGGER_VALUE = new BigDecimal(49.5);
    BigDecimal ABOVE_TRIGGER_VALUE = new BigDecimal(50.6);
  
    @Mock
    private IndicatorStrategy firstOperandIndicatorStrategy = new ClosingPriceStrategy();
 
    @Mock
    private IndicatorStrategy secondOperandIndicatorStrategy = new FixedValueStrategy(TRIGGER_VALUE);

    @InjectMocks
    private TransactionTrigger transactionTrigger;    
    
   
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);  
    }
    
    
    @Test
    public void shouldRespondToLessThanOperatorInTransactionTrigger() {       
        RelationalOperator relationalOperator = RelationalOperator.LESS_THAN;
        transactionTrigger = new TransactionTrigger(firstOperandIndicatorStrategy, relationalOperator, secondOperandIndicatorStrategy);       
        given(secondOperandIndicatorStrategy.calculate(HISTORICAL_QUOTE, HISTORICAL_QUOTES)).willReturn(TRIGGER_VALUE);
        
        // BELOW_TRIGGER_VALUE < TRIGGER_VALUE must return true
        given(firstOperandIndicatorStrategy.calculate(HISTORICAL_QUOTE, HISTORICAL_QUOTES)).willReturn(BELOW_TRIGGER_VALUE);
        assertTrue(transactionTrigger.isTransactionTriggerActivated(HISTORICAL_QUOTE, HISTORICAL_QUOTES));
 
        // ABOVE_TRIGGER_VALUE < TRIGGER_VALUE must return false
        given(firstOperandIndicatorStrategy.calculate(HISTORICAL_QUOTE, HISTORICAL_QUOTES)).willReturn(ABOVE_TRIGGER_VALUE);
        assertFalse(transactionTrigger.isTransactionTriggerActivated(HISTORICAL_QUOTE, HISTORICAL_QUOTES));
        
        // TRIGGER_VALUE < TRIGGER_VALUE must return false
        given(firstOperandIndicatorStrategy.calculate(HISTORICAL_QUOTE, HISTORICAL_QUOTES)).willReturn(TRIGGER_VALUE);
        assertFalse(transactionTrigger.isTransactionTriggerActivated(HISTORICAL_QUOTE, HISTORICAL_QUOTES));
    }  
    
    @Test
    public void shouldRespondToGreaterThanOperatorInTransactionTrigger() {       
        RelationalOperator relationalOperator = RelationalOperator.GREATER_THAN;
        transactionTrigger = new TransactionTrigger(firstOperandIndicatorStrategy, relationalOperator, secondOperandIndicatorStrategy);
        given(secondOperandIndicatorStrategy.calculate(HISTORICAL_QUOTE, HISTORICAL_QUOTES)).willReturn(TRIGGER_VALUE);
        
        // BELOW_TRIGGER_VALUE > TRIGGER_VALUE must return false
        given(firstOperandIndicatorStrategy.calculate(HISTORICAL_QUOTE, HISTORICAL_QUOTES)).willReturn(BELOW_TRIGGER_VALUE);
        assertFalse(transactionTrigger.isTransactionTriggerActivated(HISTORICAL_QUOTE, HISTORICAL_QUOTES));
 
        // ABOVE_TRIGGER_VALUE > TRIGGER_VALUE must return true
        given(firstOperandIndicatorStrategy.calculate(HISTORICAL_QUOTE, HISTORICAL_QUOTES)).willReturn(ABOVE_TRIGGER_VALUE);
        assertTrue(transactionTrigger.isTransactionTriggerActivated(HISTORICAL_QUOTE, HISTORICAL_QUOTES));
        
        // TRIGGER_VALUE > TRIGGER_VALUE must return false
        given(firstOperandIndicatorStrategy.calculate(HISTORICAL_QUOTE, HISTORICAL_QUOTES)).willReturn(TRIGGER_VALUE);
        assertFalse(transactionTrigger.isTransactionTriggerActivated(HISTORICAL_QUOTE, HISTORICAL_QUOTES));
    } 

    @Test
    public void shouldRespondToEqualsOperatorInTransactionTrigger() {       
        RelationalOperator relationalOperator = RelationalOperator.EQUALS;
        transactionTrigger = new TransactionTrigger(firstOperandIndicatorStrategy, relationalOperator, secondOperandIndicatorStrategy);
        given(secondOperandIndicatorStrategy.calculate(HISTORICAL_QUOTE, HISTORICAL_QUOTES)).willReturn(TRIGGER_VALUE);
        
        // BELOW_TRIGGER_VALUE == TRIGGER_VALUE must return false
        given(firstOperandIndicatorStrategy.calculate(HISTORICAL_QUOTE, HISTORICAL_QUOTES)).willReturn(BELOW_TRIGGER_VALUE);
        assertFalse(transactionTrigger.isTransactionTriggerActivated(HISTORICAL_QUOTE, HISTORICAL_QUOTES));
 
        // ABOVE_TRIGGER_VALUE == TRIGGER_VALUE must return false
        given(firstOperandIndicatorStrategy.calculate(HISTORICAL_QUOTE, HISTORICAL_QUOTES)).willReturn(ABOVE_TRIGGER_VALUE);
        assertFalse(transactionTrigger.isTransactionTriggerActivated(HISTORICAL_QUOTE, HISTORICAL_QUOTES));
        
        // TRIGGER_VALUE == TRIGGER_VALUE must return true
        given(firstOperandIndicatorStrategy.calculate(HISTORICAL_QUOTE, HISTORICAL_QUOTES)).willReturn(TRIGGER_VALUE);
        assertTrue(transactionTrigger.isTransactionTriggerActivated(HISTORICAL_QUOTE, HISTORICAL_QUOTES));
    } 
}
