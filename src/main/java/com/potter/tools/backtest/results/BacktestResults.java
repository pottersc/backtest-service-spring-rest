package com.potter.tools.backtest.results;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.potter.tools.backtest.data.JsonBigDecimalSerializer;

/**
 * Object that contains the results of a backtest analysis
 * @author Scott Potter
 *
 */
public class BacktestResults {
    List<TradeDay> tradeDays = new ArrayList<TradeDay>();
    List<Indicator> indicators = new ArrayList<Indicator>();
    
    /**
     * Add the specified TradDay to the list of tradDays managed by this class
     * @param tradeDay
     */
    public void addTradeDay(TradeDay tradeDay) {
        tradeDays.add(tradeDay);
    }
    
    /**
     * Add unique indicators to the list of indicators managed by this class
     * Only unique indicators are added such that if a 50 day simple moving average is used in both
     * the buy and sell triggers then it only needs to be included in the results object once so that
     * duplicate redundant lines do not appear in the display chart.
     * @param indicators
     */
    public void addIndicators(List<Indicator> indicators) {
    	for(Indicator indicator:indicators){
    		if(indicator.getIndicatorStrategy().isDisplayIndicatorStrategy()  && !this.indicators.contains(indicator)){
    			 this.indicators.add(indicator);
    		}
    	}
    }
    
    /**
     * Calculate and return the ending investment from the backtest analysis which is the 
     * investment value on the last trading day
     * @return
     */
    public BigDecimal getEndingInvestment() {
        return getLastTradeDay().getInvestmentValue();
    }
    
    /**
     * Calculate and return the number of trades that were executed during the analysis
     * @return
     */
    public int getNumberOfTrades() {
        int numberOfTrades = 0;
        for (TradeDay tradeDay : tradeDays) {
            if(tradeDay.isTradeOccurred()) {
                numberOfTrades++;
            }
        }
        return numberOfTrades;
    }
    
    public List<TradeDay> getTradeDays() {
        return tradeDays;
    }

    public List<Indicator> getIndicators() {
        return indicators;
    }

    private TradeDay getLastTradeDay() {
        return tradeDays.get(tradeDays.size()-1);
    }
    
    @Override
    public String toString() {
        String str = "";
        for (TradeDay tradeDay : tradeDays) {
            str = str + tradeDay + "\n";
        }
        str += "endingInvestment="+getEndingInvestment()+"\n";
        return str;
    }
}
