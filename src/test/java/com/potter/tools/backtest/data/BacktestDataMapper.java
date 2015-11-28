package com.potter.tools.backtest.data;

import java.io.Reader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import junitparams.mappers.IdentityMapper;


public class BacktestDataMapper extends IdentityMapper{
    
    @SuppressWarnings("unchecked")
    @Override
    public Object[] map(Reader reader) {
        Object[] map = super.map(reader);
        @SuppressWarnings("rawtypes")
        List result = new LinkedList();
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<HistoricalQuote> historicalQuotes = new ArrayList<HistoricalQuote>();
        for (Object lineObj : map) {
            String line = (String) lineObj; 
            if(line!=null && !line.contains("#")) {
                String[] tokens = line.split(",");
                HistoricalQuote historicalQuote = new HistoricalQuote(tokens[0], LocalDate.parse(tokens[1],dtf), new BigDecimal(tokens[2]), Long.parseLong(tokens[3]));
                historicalQuotes.add(historicalQuote);
                result.add(new Object[] {historicalQuote, historicalQuotes, new BigDecimal(tokens[4])}); 
            }
        }
        return result.toArray();
    }
}
