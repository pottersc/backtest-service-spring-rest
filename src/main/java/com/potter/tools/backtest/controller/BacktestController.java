package com.potter.tools.backtest.controller;

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

/**
 * 
 * REST controller for the backtest application
 * 
 * @author Scott Potter
 * 
 */
@RestController
@RequestMapping("/backtest")
public class BacktestController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BacktestController.class);

	@Autowired
	private BacktestCalculationService backtestCalculationService;

	/**
	 * 
	 * @return JSON formatted list of relational operators supported by the backtest tool
	 */
	@RequestMapping("/relationalOperators")
	@ResponseBody
	public final RelationalOperator[] prepareOperators() {
		return RelationalOperator.values();
	}
	
	/**
	 * Execute a backtest analysis for the passed in scenario
	 * @param backtestScenarioDTO : scenario to be executed. This is a Data Transfer Object
	 * @return BacktestResult object in JSON format
	 */
	@RequestMapping(value = "/runAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public BacktestResults runAnalysis(@RequestBody BacktestScenarioDTO backtestScenarioDTO) {
		LOGGER.debug("runAnalysis() Called with ticker symbol of " + backtestScenarioDTO.getTickerSymbol());
		// Convert the backtest scenario data transfer object into a true BacktestScenario object
		BacktestScenario backtestScenario = new BacktestScenario(backtestScenarioDTO);
		BacktestResults backtestResults = null;
		try {
			backtestResults = backtestCalculationService.runAnalysis(backtestScenario);
		} catch (Exception e) {
			LOGGER.error(StackTraceUtil.getCustomStackTrace(e));
		}
		return backtestResults;
	}



}
