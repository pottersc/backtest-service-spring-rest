package com.potter.tools.backtest.controller;

import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.potter.tools.backtest.calculate.BacktestCalculationService;
import com.potter.tools.backtest.calculate.BacktestScenario;
import com.potter.tools.backtest.calculate.BacktestScenarioDTO;
import com.potter.tools.backtest.calculate.RelationalOperator;
import com.potter.tools.backtest.results.BacktestResults;
import com.potter.tools.backtest.utils.StackTraceUtil;


@RestController
@RequestMapping("/backtest")
public class BacktestController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BacktestController.class);

	@Autowired
	private BacktestCalculationService backtestCalculationService;

	@RequestMapping("/relationalOperators")
	@ResponseBody
	public final RelationalOperator[] prepareOperators() {
		return RelationalOperator.values();
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



}
