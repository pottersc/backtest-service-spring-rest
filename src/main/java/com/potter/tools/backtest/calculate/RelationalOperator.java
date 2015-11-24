package com.potter.tools.backtest.calculate;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RelationalOperator {
    GREATER_THAN(">"), LESS_THAN("<"), EQUALS("==");
    
    private String symbol;
    
    private RelationalOperator(String symbol) {
        this.symbol = symbol;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public String getName() {
    	return this.toString();
    }
  
    public static RelationalOperator findByName(String name){
        for(RelationalOperator relationalOperator:RelationalOperator.values()){
            if(name!=null && name.equals(relationalOperator.name())){
                return relationalOperator;
            }
        }
        throw new RuntimeException("RelationalOperator.findByName("+name+") not found in Enum definition");
    }     
    

}
