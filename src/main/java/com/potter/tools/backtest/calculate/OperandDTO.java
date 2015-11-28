package com.potter.tools.backtest.calculate;

/**
 * Operand Data Transfer Object is used to assist in the transformation of 
 * client passed in JSON data into backtestScenario object.
 * See BacktestScenarioDTO for details as to why this is required
 * @author Scott Potter
 *
 */
public class OperandDTO {
	private String strategyName;
	private int value1;
	private int value2;
	
	public OperandDTO(){}
	
	public String getStrategyName() {
		return strategyName;
	}
	public void setStrategyName(String strategyName) {
		this.strategyName = strategyName;
	}
	public int getValue1() {
		return value1;
	}
	public void setValue1(int value1) {
		this.value1 = value1;
	}
	public int getValue2() {
		return value2;
	}
	public void setValue2(int value2) {
		this.value2 = value2;
	} 	
}