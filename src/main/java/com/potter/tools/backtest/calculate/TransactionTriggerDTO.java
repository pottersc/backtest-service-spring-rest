package com.potter.tools.backtest.calculate;

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