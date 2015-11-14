package com.potter.tools.backtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.potter.tools.backtest.data.HistoricalQuoteLocalRepositoryImpl;
import com.potter.tools.backtest.data.HistoricalQuoteRepository;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public HistoricalQuoteRepository historicalQuoteRepository(){
    	return new HistoricalQuoteLocalRepositoryImpl();
    }
}
