package com.potter.tools.backtest.data;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsonBigDecimalSerializer extends JsonSerializer<BigDecimal> {    

    @Override
    public void serialize(BigDecimal value, JsonGenerator generator, SerializerProvider provider) throws IOException, JsonProcessingException {
    	DecimalFormat df = new DecimalFormat();
    	df.setMaximumFractionDigits(2);
        generator.writeString(df.format(value));
    }
}