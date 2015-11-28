package com.potter.tools.backtest.calculate.strategy;

import java.math.BigDecimal;

import com.potter.tools.backtest.calculate.OperandDTO;

/**
 * Implementation of a factory pattern for IndicatorStrategy
 * Create the appropriate IndicatorStrategy object based on passed in parameters
 * If any new indicator strategy object are supported in the future, this class must be modified 
 * @author Scott Potter
 *
 */
public class IndicatorStrategyFactory {

	/**
	 * Create the appropriate IndicatorStrategy object based on passed in parameters
	 * @param operandDTO : Data Transfer Object that defines an operand
	 * @return
	 */
    public static IndicatorStrategy getIndicatorStrategy(OperandDTO operandDTO) {
    	return getIndicatorStrategy(operandDTO.getStrategyName(), operandDTO.getValue1(), operandDTO.getValue2());
    }  
    
    /**
     * This is the actual heart of the factory pattern implementation that creates the appropriate
     * IndicatorStrategy object.
     * If any new indicator strategy object are supported in the future, this method must be modified 
     * @param strategyTag
     * @param value1
     * @param value2
     * @return
     */
    private static IndicatorStrategy getIndicatorStrategy(String strategyTag, int value1, int value2) {
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

}
