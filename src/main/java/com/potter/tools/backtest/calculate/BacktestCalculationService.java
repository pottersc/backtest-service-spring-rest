package com.potter.tools.backtest.calculate;

import com.potter.tools.backtest.results.BacktestResults;

public interface BacktestCalculationService {

    public BacktestResults runAnalysis(BacktestScenario backtestScenario);
    
}
