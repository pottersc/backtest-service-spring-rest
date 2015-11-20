package com.potter.tools.backtest.results;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.potter.tools.backtest.calculate.strategy.IndicatorStrategy;
import com.potter.tools.backtest.data.HistoricalQuote;

public class Indicator {
    private String name;
    private List<IndicatorDay> indicatorDays;   
    private IndicatorStrategy indicatorStrategy;
    
    public Indicator(IndicatorStrategy indicatorStrategy, List<HistoricalQuote> historicalQuotes) {
        this.name = indicatorStrategy.getName();
        this.indicatorStrategy = indicatorStrategy;
        indicatorDays = new ArrayList<IndicatorDay>();
        for(HistoricalQuote historicalQuote:historicalQuotes) {
            indicatorDays.add(new IndicatorDay(historicalQuote.getDate(),indicatorStrategy.calculate(historicalQuote, historicalQuotes)));    
        }
    }
    
    public Indicator(IndicatorStrategy indicatorStrategy, List<HistoricalQuote> historicalQuotes, LocalDate startDate, LocalDate endDate) {
        this.name = indicatorStrategy.getName();
        this.indicatorStrategy = indicatorStrategy;
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

	public IndicatorStrategy getIndicatorStrategy() {
		return indicatorStrategy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((indicatorStrategy == null) ? 0 : indicatorStrategy.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Indicator other = (Indicator) obj;
		if (indicatorStrategy == null) {
			if (other.indicatorStrategy != null)
				return false;
		} else if (!indicatorStrategy.equals(other.indicatorStrategy))
			return false;
		return true;
	}


    
}
