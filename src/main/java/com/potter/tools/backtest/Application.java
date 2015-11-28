package com.potter.tools.backtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.potter.tools.backtest.data.HistoricalQuoteRepository;
import com.potter.tools.backtest.data.HistoricalQuoteYahooFinanceRepositoryImpl;

/**
 * 
 * @author Scott Potter
 * Initial configuration of application
 *
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    /**
     * Use Yahoo Finance as the source for historical quotes
     * @return
     */
    @Bean
    public HistoricalQuoteRepository historicalQuoteRepository(){
    	return new HistoricalQuoteYahooFinanceRepositoryImpl();
    }
    
}
