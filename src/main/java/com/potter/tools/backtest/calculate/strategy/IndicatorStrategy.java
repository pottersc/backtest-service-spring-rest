package com.potter.tools.backtest.calculate.strategy;

import java.math.BigDecimal;
// SMA (Simple Moving Average) : period(int)
// EMA (Exponential Moving Average) : period (int)
// BB (Bollinger Bands) : period(int), stdDev(int)
import java.util.List;

import com.potter.tools.backtest.data.HistoricalQuote;

// MFI (Money Flow Index) : period(int)
// MACD (Moving Average Convergence Divergence) : fastPeriod(int), slowPeriod(int), signalSmoothing(int)
// RSI (Relative Strength Index) : period(int)
// Stochastic Oscillator : length(int), pctKPeriod(int), pctDPeriod(int)
public interface IndicatorStrategy {
    
    public BigDecimal calculate(HistoricalQuote quoteInProcess, List<HistoricalQuote> historicalQuotes);
    
    public String getName();
    
    public boolean isDisplayIndicatorStrategy();

}
