package com.potter.tools.backtest.results;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.potter.tools.backtest.data.JsonDateDeserializer;
import com.potter.tools.backtest.data.JsonDateSerializer;

public class IndicatorDay {
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDate date;
    private BigDecimal value;
    
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
