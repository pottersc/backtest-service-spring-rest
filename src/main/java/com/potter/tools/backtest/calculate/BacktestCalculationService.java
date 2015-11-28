package com.potter.tools.backtest.calculate;

import com.potter.tools.backtest.results.BacktestResults;

/**
 * Interface for backtest calculation service
 * @author Scott Potter
 *
 */
public interface BacktestCalculationService {

	/**
	 * run a backtest analysis for the specified scenario and return backtest result object
	 * @param backtestScenario : scenario to execute a backtest analysis on
	 * @return : backtestResults object is returned
	 */
    public BacktestResults runAnalysis(BacktestScenario backtestScenario);
    
}
