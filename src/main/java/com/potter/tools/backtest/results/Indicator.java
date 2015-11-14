package com.potter.tools.backtest.results;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.potter.tools.backtest.calculate.strategy.IndicatorStrategy;
import com.potter.tools.backtest.data.HistoricalQuote;

public class Indicator {
    private String name;
    private List<IndicatorDay> indicatorDays;    
    
    public Indicator(IndicatorStrategy indicatorStrategy, List<HistoricalQuote> historicalQuotes) {
        this.name = indicatorStrategy.getName();
        indicatorDays = new ArrayList<IndicatorDay>();
        for(HistoricalQuote historicalQuote:historicalQuotes) {
            indicatorDays.add(new IndicatorDay(historicalQuote.getDate(),indicatorStrategy.calculate(historicalQuote, historicalQuotes)));    
        }
    }
    
    public Indicator(IndicatorStrategy indicatorStrategy, List<HistoricalQuote> historicalQuotes, LocalDate startDate, LocalDate endDate) {
        this.name = indicatorStrategy.getName();
        indicatorDays = new ArrayList<IndicatorDay>();
        for(HistoricalQuote historicalQuote:historicalQuotes) {
            if(historicalQuote.getDate().compareTo(startDate)>=0 && historicalQuote.getDate().compareTo(endDate)<=0) {
                indicatorDays.add(new IndicatorDay(historicalQuote.getDate(),indicatorStrategy.calculate(historicalQuote, historicalQuotes)));  
            }
        }
    }    
    
    
    
    public String getName() {
        return name;
    }

    public List<IndicatorDay> getIndicatorDays() {
        return indicatorDays;
    }   
}
