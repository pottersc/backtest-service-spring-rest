package com.potter.tools.backtest.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class HistoricalQuoteLocalRepositoryImpl implements HistoricalQuoteRepository {
    private final Logger LOGGER = LoggerFactory.getLogger(HistoricalQuoteLocalRepositoryImpl.class);
    private static final String DATA_FOLDER = "C:\\Data\\java\\JavaSpringTest\\backtest\\localHistoricalQuoteData" + File.separatorChar;
    
    @Autowired
    HistoricalQuoteYahooFinanceRepositoryImpl historicalQuoteYahooFinanceRepositoryImpl;
    
    @Override
    public List<HistoricalQuote> importHistoricalQuotes(String tickerSymbol) {
        LOGGER.info("HistoricalQuoteLocalRepositoryImpl.importHistoricalQuotes("+tickerSymbol+"):Called");
        assert(historicalQuoteYahooFinanceRepositoryImpl!=null);
        List<HistoricalQuote> historicalQuotes =readLocalHistoricalQuotes(tickerSymbol);
        if(historicalQuotes==null || historicalQuotes.size()<=0) {
            historicalQuotes = historicalQuoteYahooFinanceRepositoryImpl.importHistoricalQuotes(tickerSymbol);
            LOGGER.info("HistoricalQuoteLocalRepositoryImpl.importHistoricalQuotes("+tickerSymbol+"):Imported from Yahoo");
            saveLocalHistoricalQuotes(historicalQuotes, tickerSymbol);
        }   
        historicalQuotes.sort(new HistoricalQuoteComparator());
        return historicalQuotes;
    }   
    
    private void saveLocalHistoricalQuotes(List<HistoricalQuote> historicalQuotes, String tickerSymbol) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            HistoricalQuotesCollection historicalQuotesCollection = new HistoricalQuotesCollection();
            historicalQuotesCollection.setHistoricalQuotes(historicalQuotes);
            mapper.writeValue(getLocalDataFile(tickerSymbol), historicalQuotesCollection);
        } catch (Exception e){
           throw new RuntimeException("saveHistoricalQuotesLocally("+historicalQuotes.size()+","+tickerSymbol+"):"+e);           
        }       
    }
    
    private List<HistoricalQuote> readLocalHistoricalQuotes(String tickerSymbol) {
        List<HistoricalQuote> historicalQuotes = new ArrayList<HistoricalQuote>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = getLocalDataFile(tickerSymbol);
            if(file.exists()) {
                HistoricalQuotesCollection historicalQuotesCollection = mapper.readValue(file, HistoricalQuotesCollection.class);
                historicalQuotes = historicalQuotesCollection.getHistoricalQuotes();
            }
        } catch (Exception e) {
           throw new RuntimeException("saveHistoricalQuotesLocally("+tickerSymbol+"):"+e);    
        }       
        return historicalQuotes;
    }


    private File getLocalDataFile(String fileName) {
        return new File(DATA_FOLDER + fileName);
    }

}
