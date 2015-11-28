package com.potter.tools.backtest.data;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import junitparams.JUnitParamsRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.potter.tools.backtest.data.HistoricalQuote;
import com.potter.tools.backtest.data.HistoricalQuoteYahooFinanceRepositoryImpl;
/**
 * This is really more of an integration test since it calls the actual HistoricalQuoteRepository implementation,
 * but since this runs really fast I have included it with the Unit tests.
 * @author esp003
 *
 */
@RunWith(JUnitParamsRunner.class)
public class HistoricalQuoteRepositoryTest {

    @InjectMocks
    private HistoricalQuoteYahooFinanceRepositoryImpl historicalDataRepository;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void shouldRetrieveAllHistoricalDataForAStock() {
        List<HistoricalQuote> historicalQuotes =  historicalDataRepository.importHistoricalQuotes("MSI");
        assertNotNull(historicalQuotes);
        assertTrue(historicalQuotes.size()>0);
    }

    @Test(expected = RuntimeException.class)
    public void shouldGenerateExceptionForBadTickerSymbol() {
        historicalDataRepository.importHistoricalQuotes("BadTicker");  
    }
    
    
}
