package com.potter.tools.backtest.results;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.potter.tools.backtest.calculate.TradeAction;
import com.potter.tools.backtest.data.JsonBigDecimalSerializer;
import com.potter.tools.backtest.data.JsonDateDeserializer;
import com.potter.tools.backtest.data.JsonDateSerializer;

/**
 * Stores information for stock price, investment value, and trading activity for a 
 * single specified date
 * @author Scott Potter
 *
 */
public class TradeDay {
    private static final BigDecimal ZERO = new BigDecimal(0);
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDate date; 
    @JsonSerialize(using = JsonBigDecimalSerializer.class)
    private BigDecimal stockPrice;
    private TradeAction action;
    @JsonSerialize(using = JsonBigDecimalSerializer.class)
    private BigDecimal numberSharesOwned = ZERO;
    @JsonSerialize(using = JsonBigDecimalSerializer.class)
    private BigDecimal numberSharesTraded = ZERO;
    @JsonSerialize(using = JsonBigDecimalSerializer.class)
    private BigDecimal investableCash = ZERO;
    
    /**
     * Constructor for TradeDay
     * @param date : date for the stock
     * @param stockPrice : closing price of the stock on specified date
     */
    public TradeDay(LocalDate date, BigDecimal stockPrice) {
        super();
        this.date = date;
        this.stockPrice = stockPrice;
    }

    /**
     * Execute a trade based on the specified parameters and then update the status of the 
     * TradeDay attributes based on whatever execution may or may not have been applied
     * @param numberSharesOwned : # of shares owned heading into this trade execution
     * @param buySignal : true if there is an active buy signal, false otherwise
     * @param sellSignal : true if there is an active sell signal, false otherwise
     * @param investableCash : amount of investable cash heading into this trade
     * @param transactionCost : cost of every buy or sell transaction from stock broker
     */
    public void execute(BigDecimal numberSharesOwned, boolean buySignal, boolean sellSignal, BigDecimal investableCash, BigDecimal transactionCost) {
        MathContext mathContext = new MathContext(15, RoundingMode.HALF_EVEN);
        if(buySignal && !isOwned(numberSharesOwned) && investableCash.compareTo(transactionCost)>0) {
            action = TradeAction.BUY;
            this.numberSharesOwned = (investableCash.subtract(transactionCost)).divide(stockPrice,mathContext);
            this.investableCash = ZERO;
            this.numberSharesTraded = this.numberSharesOwned;
        }else if(sellSignal && isOwned(numberSharesOwned)) {
            action = TradeAction.SELL;
            this.investableCash = numberSharesOwned.multiply(stockPrice);
            this.investableCash = this.investableCash.subtract(transactionCost);
            this.numberSharesTraded = numberSharesOwned;
            this.numberSharesOwned = ZERO;
        }else if(!isOwned(numberSharesOwned)) {
            action = TradeAction.NONE;
            this.numberSharesOwned = ZERO;
            this.numberSharesTraded = ZERO;
            this.investableCash = investableCash;
        }else {
            action = TradeAction.HOLD;
            this.numberSharesOwned = numberSharesOwned;
            this.numberSharesTraded = ZERO;
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

    public BigDecimal getNumberSharesTraded() {
		return numberSharesTraded;
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
