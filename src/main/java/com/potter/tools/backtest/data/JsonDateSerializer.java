package com.potter.tools.backtest.data;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsonDateSerializer extends JsonSerializer<LocalDate> {    
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  
    @Override
    public void serialize(LocalDate date, JsonGenerator generator, SerializerProvider provider) throws IOException, JsonProcessingException {
        String dateString = date.format(formatter);
        generator.writeString(dateString);
    }
}