package com.potter.tools.backtest.results;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.potter.tools.backtest.calculate.TradeAction;
import com.potter.tools.backtest.data.JsonDateDeserializer;
import com.potter.tools.backtest.data.JsonDateSerializer;

public class TradeDay {
    private static final BigDecimal ZERO = new BigDecimal(0);
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDate date; 
    private BigDecimal stockPrice;
    private TradeAction action;
    private BigDecimal numberSharesOwned = ZERO;
    private BigDecimal investableCash = ZERO;
    
    public TradeDay(LocalDate date, BigDecimal stockPrice) {
        super();
        this.date = date;
        this.stockPrice = stockPrice;
    }

    public void execute(BigDecimal numberSharesOwned, boolean buySignal, boolean sellSignal, BigDecimal investableCash, BigDecimal transactionCost) {
        MathContext mathContext = new MathContext(15, RoundingMode.HALF_EVEN);
        if(buySignal && !isOwned(numberSharesOwned) && investableCash.compareTo(transactionCost)>0) {
            action = TradeAction.BUY;
            this.numberSharesOwned = (investableCash.subtract(transactionCost)).divide(stockPrice,mathContext);
            this.investableCash = ZERO;
        }else if(sellSignal && isOwned(numberSharesOwned)) {
            action = TradeAction.SELL;
            this.investableCash = numberSharesOwned.multiply(stockPrice);
            this.investableCash = investableCash.subtract(transactionCost);
            this.numberSharesOwned = ZERO;
        }else if(!isOwned(numberSharesOwned)) {
            action = TradeAction.NONE;
            this.numberSharesOwned = ZERO;
            this.investableCash = investableCash;
        }else {
            action = TradeAction.HOLD;
            this.numberSharesOwned = numberSharesOwned;
            this.investableCash = investableCash;
        }
    }    
    
    private boolean isOwned(BigDecimal numberSharesOwned) {
        return numberSharesOwned.compareTo(ZERO) > 0;
    }

    public BigDecimal getInvestmentValue() {
        return (numberSharesOwned.multiply(stockPrice)).add(investableCash);
    }
    
    protected LocalDate getDate() {
        return date;
    }
    
    public BigDecimal getStockPrice() {
        return stockPrice;
    }

    public TradeAction getAction() {
        return action;
    }

    public BigDecimal getNumberSharesOwned() {
        return numberSharesOwned;
    }

    public BigDecimal getInvestableCash() {
        return investableCash;
    }

    public boolean isTradeOccurred() {
        return action.isTradeOccurred();
    }
      
    @Override
    public String toString() {
        return this.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ": " + this.stockPrice + ": " + this.action + ": "+ this.numberSharesOwned+": " + this.investableCash+": " + this.getInvestmentValue();
    }

}
