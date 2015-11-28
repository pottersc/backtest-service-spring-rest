package com.potter.tools.backtest.calculate;

 /**
 * Transaction Trigger Data Transfer Object is used to assist in the transformation of 
 * client passed in JSON data into backtestScenario object.
 * See BacktestScenarioDTO for details as to why this is required 
 * @author Scott Potter
 *
 */
public class TransactionTriggerDTO{
	private OperandDTO operand1;
	private String operatorName;
	private OperandDTO operand2;
	
	public TransactionTriggerDTO(){}
	
	public OperandDTO getOperand1() {
		return operand1;
	}
	public void setOperand1(OperandDTO operand1) {
		this.operand1 = operand1;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public OperandDTO getOperand2() {
		return operand2;
	}
	public void setOperand2(OperandDTO operand2) {
		this.operand2 = operand2;
	}    	
}