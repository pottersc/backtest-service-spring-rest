package com.potter.tools.backtest.calculate;

/**
 * Enum that defines the various stock trading actions that are supported 
 * @author Scott Potter
 *
 */
public enum TradeAction { 
    BUY(true), SELL(true), HOLD(false) , NONE(false);
    
    boolean tradeOccurred = false;
    private TradeAction(boolean tradeOccurred) {
        this.tradeOccurred = tradeOccurred;
    }
    public boolean isTradeOccurred() {
        return tradeOccurred;
    }
    
}
