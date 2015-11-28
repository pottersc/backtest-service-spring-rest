package com.potter.tools.backtest.calculate.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.math.BigDecimal;
import java.util.List;

import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.potter.tools.backtest.data.BacktestDataMapper;
import com.potter.tools.backtest.data.HistoricalQuote;

@RunWith(JUnitParamsRunner.class)
public class ClosingPriceStrategyTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    
    private static final BigDecimal BAD_EXPECTED_RESULT = new BigDecimal(-2);

    @Test
    @FileParameters(value="src/test/resources/quotesForClosingPriceStrategy.csv",mapper = BacktestDataMapper.class)
    public void shouldCalculateClosingPriceStrategyForEachQuote(HistoricalQuote historicalQuote, List<HistoricalQuote> historicalQuotes, BigDecimal expectedResult){
        IndicatorStrategy indicatorStrategy = new ClosingPriceStrategy();
        assertEquals(indicatorStrategy.calculate(historicalQuote, historicalQuotes), expectedResult);
        
        assertNotEquals(indicatorStrategy.calculate(historicalQuote, historicalQuotes), BAD_EXPECTED_RESULT);
    }
}
