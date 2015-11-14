package com.potter.tools.backtest.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.potter.tools.backtest.calculate.BacktestCalculationService;
import com.potter.tools.backtest.calculate.BacktestScenario;
import com.potter.tools.backtest.calculate.BacktestScenarioDTO;
import com.potter.tools.backtest.calculate.RelationalOperator;
import com.potter.tools.backtest.calculate.TransactionTrigger;
import com.potter.tools.backtest.calculate.strategy.IndicatorStrategy;
import com.potter.tools.backtest.calculate.strategy.MovingAverageStrategy;
import com.potter.tools.backtest.data.HistoricalQuote;
import com.potter.tools.backtest.results.BacktestResults;
import com.potter.tools.backtest.utils.StackTraceUtil;

@RestController
@RequestMapping("/backtest")
public class BacktestController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BacktestController.class);

	@Autowired
	private BacktestCalculationService backtestCalculationService;

	@RequestMapping("/dummyquote")
	@ResponseBody
	public HistoricalQuote dummyQuote(@RequestParam(value = "name", defaultValue = "msi") String name) {
		LOGGER.info("INFO: dummyQuote(" + name + ")");
		LOGGER.debug("DEBUG: dummyQuote(" + name + ")");
		return new HistoricalQuote(name, "2015-10-26", (double) 3.33333333);
	}

	@RequestMapping(value = "/dummyquote2", method = RequestMethod.POST)
	@ResponseBody
	public HistoricalQuote dummyQuote2(@RequestBody HistoricalQuote quote) {
		LOGGER.info("INFO: dummyQuote(" + quote.getTickerSymbol() + ")");
		quote.setTickerSymbol("hello");
		return quote;
	}

	@RequestMapping("/relationalOperators")
	@ResponseBody
	public final Map<String, String> prepareOperators() {
		return RelationalOperator.getRelationalOperatorsMap();
	}

	@RequestMapping("/indicatorStrategies")
	@ResponseBody
	public final Map<String, String> prepareIndicatorStrategies() {
		Map<String, String> indicatorStrategies = new TreeMap<String, String>();
		indicatorStrategies.put("SMA", "Simple Moving Average");
		indicatorStrategies.put("CLOSE", "Closing Price");
		indicatorStrategies.put("FIX", "Fixed Price");
		return indicatorStrategies;
	}

	@RequestMapping(value = "/runAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public BacktestResults runAnalysis(@RequestBody BacktestScenarioDTO backtestScenarioDTO) {
		LOGGER.info("runAnalysis() Called");
		LOGGER.info("tickerSymbol=" + backtestScenarioDTO.getTickerSymbol());
		LOGGER.info("startDateStr=" + backtestScenarioDTO.getStartDate().toString());
		LOGGER.info("buyStrategyOperandsStrategyName=" + backtestScenarioDTO.getBuyTrigger().getOperand1().getStrategyName());
		BacktestScenario backtestScenario = new BacktestScenario(backtestScenarioDTO);


		BacktestResults backtestResults = null;
		try {
			backtestResults = backtestCalculationService.runAnalysis(backtestScenario);
			LOGGER.info("ending investment="+backtestResults.getEndingInvestment());
			LOGGER.info("# days="+backtestResults.getTradeDays().size());
		} catch (Exception e) {
			LOGGER.error(StackTraceUtil.getCustomStackTrace(e));
		}

		return backtestResults;
	}

	@RequestMapping(value = "/resultData")
	@ResponseBody
	public BacktestResults getResultDataJson() {
		LOGGER.info("getResultDataJson() Called");
		return getFakeResultData();
	}

	private BacktestResults getFakeResultData() {
		IndicatorStrategy buyStrategyFirstOperand = new MovingAverageStrategy(3);
		RelationalOperator buyOperator = RelationalOperator.GREATER_THAN;
		IndicatorStrategy buyStrategySecondOperand = new MovingAverageStrategy(5);

		IndicatorStrategy sellStrategyFirstOperand = new MovingAverageStrategy(5);
		RelationalOperator sellOperator = RelationalOperator.LESS_THAN;
		IndicatorStrategy sellStrategySecondOperand = new MovingAverageStrategy(8);

		BigDecimal startingInvestment = new BigDecimal("10000");
		BigDecimal transactionCost = new BigDecimal("5");

		final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startDate = LocalDate.parse("2015-06-14", dtf);
		LocalDate endDate = LocalDate.parse("2015-07-14", dtf);
		TransactionTrigger buyTrigger = new TransactionTrigger(buyStrategyFirstOperand, buyOperator,
				buyStrategySecondOperand);
		TransactionTrigger sellTrigger = new TransactionTrigger(sellStrategyFirstOperand, sellOperator,
				sellStrategySecondOperand);

		BacktestScenario backtestScenario = new BacktestScenario("MSI", startDate, endDate, startingInvestment,
				transactionCost, buyTrigger, sellTrigger);

		return backtestCalculationService.runAnalysis(backtestScenario);

	}

}
