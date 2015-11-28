package com.potter.tools.backtest.results;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.potter.tools.backtest.calculate.strategy.IndicatorStrategy;
import com.potter.tools.backtest.data.HistoricalQuote;

/**
 * An indicator is a representation of an indicator strategy (such as a 50 day moving average) and the 
 * associated value of that strategy for each trading day of the analysis.
 * This is used to chart the value of each indicator strategy on the display chart along side the
 * stock price.
 * @author Scott Potter
 *
 */
public class Indicator {
    private String name;
    private List<IndicatorDay> indicatorDays;   
    private IndicatorStrategy indicatorStrategy;
    
    /**
     * Constructor for indicator class
     * @param indicatorStrategy : strategy implementation such as 50 day simple moving average
     * @param historicalQuotes : list of historical stock quotes
     * @param startDate : analysis start date
     * @param endDate : analysis end date
     */
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
