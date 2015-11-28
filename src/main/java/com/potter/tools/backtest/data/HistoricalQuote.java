package com.potter.tools.backtest.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Object that holds all the information associated with a historical stock quote
 * 
 * @author Scott Potter
 */
public class HistoricalQuote implements Serializable{ 
    private static final long serialVersionUID = -2972167241680417277L;
    private String tickerSymbol;  
    // serialize and deserialize date string format 'yyyy-MM-dd' to LocalDate 
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDate date; 
    @JsonSerialize(using = JsonBigDecimalSerializer.class)
    private BigDecimal close; 
    private long volume;

    public HistoricalQuote() {};
    
    /**
     * Constructor for HistoricalQuote
     * @param tickerSymbol : stock ticker symbol 
     * @param dateStr : date in format 'yyyy-MM-dd'
     * @param close : closing price of stock
     */
    public HistoricalQuote(String tickerSymbol, String dateStr, double close) {
        this.tickerSymbol = tickerSymbol;
        this.date = LocalDate.parse(dateStr,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.close = new BigDecimal(close);
    }
    
    /**
     * Constructor for HistoricalQuote
     * @param tickerSymbol : stock ticker symbol 
     * @param date : date of stock trade
     * @param close : closing price of stock
     * @param volume : volume of stock traded on the specified date
     */
    public HistoricalQuote(String tickerSymbol, LocalDate date, BigDecimal close, long volume) {
        this.tickerSymbol = tickerSymbol;
        this.date = date;
        this.close = close;
        this.volume = volume;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((tickerSymbol == null) ? 0 : tickerSymbol.hashCode());
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
        HistoricalQuote other = (HistoricalQuote) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (tickerSymbol == null) {
            if (other.tickerSymbol != null)
                return false;
        } else if (!tickerSymbol.equals(other.tickerSymbol))
            return false;
        return true;
    }

    @Override
    public String toString() {
        String dateStr = this.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return this.tickerSymbol + "@" + dateStr + ": "+ this.close + " (" + this.volume + ")";
    }
}