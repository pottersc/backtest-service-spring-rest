package com.potter.tools.backtest.results;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BacktestResults {
    List<TradeDay> tradeDays = new ArrayList<TradeDay>();
    List<Indicator> indicators = new ArrayList<Indicator>();
    
    public void addTradeDay(TradeDay tradeDay) {
        tradeDays.add(tradeDay);
    }
    
    public void addIndicators(List<Indicator> indicators) {
    	for(Indicator indicator:indicators){
    		if(indicator.getIndicatorStrategy().isDisplayIndicatorStrategy()  && !this.indicators.contains(indicator)){
    			 this.indicators.add(indicator);
    		}
    	}
    }
    
    
    
    public BigDecimal getEndingInvestment() {
        return getLastTradeDay().getInvestmentValue();
    }
    
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
