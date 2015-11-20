package com.potter.tools.backtest.calculate.strategy;

import java.math.BigDecimal;

import com.potter.tools.backtest.calculate.OperandDTO;

public class IndicatorStrategyFactory {

    public static IndicatorStrategy getIndicatorStrategy(String strategyTag, int value1, int value2) {
        if("SMA".equalsIgnoreCase(strategyTag)) {
            return new MovingAverageStrategy(value1);
        }else if("EMA".equalsIgnoreCase(strategyTag)) {
            return new ExponentialMovingAverageStrategy(value1);
        }else if("FIX".equalsIgnoreCase(strategyTag)) {
            return new FixedValueStrategy(new BigDecimal(value1));
        }else if("CLOSE".equalsIgnoreCase(strategyTag)) {
            return new ClosingPriceStrategy();
        }
        return new NullStrategy();
    }
    
    public static IndicatorStrategy getIndicatorStrategy(OperandDTO operandDTO) {
    	return getIndicatorStrategy(operandDTO.getStrategyName(), operandDTO.getValue1(), operandDTO.getValue2());
    }    

}
