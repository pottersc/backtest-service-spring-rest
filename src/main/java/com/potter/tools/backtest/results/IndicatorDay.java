package com.potter.tools.backtest.results;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.potter.tools.backtest.data.JsonBigDecimalSerializer;
import com.potter.tools.backtest.data.JsonDateDeserializer;
import com.potter.tools.backtest.data.JsonDateSerializer;

/**
 * Stores information on a trading indicator's value for a specified date.
 * For example this might hold the value for a 50 day moving average on date 2015-10-23  
 * @author Scott Potter
 *
 */
public class IndicatorDay {
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDate date;
    @JsonSerialize(using = JsonBigDecimalSerializer.class)
    private BigDecimal value;
    
    /**
     * Constructor for IndicatorDat
     * @param date : date the indicator is calculated for
     * @param value : value of the indicator on the specified date
     */
    public IndicatorDay(LocalDate date, BigDecimal value) {
        super();
        this.date = date;
        this.value = value;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public BigDecimal getValue() {
        return value;
    }
    
    
}
