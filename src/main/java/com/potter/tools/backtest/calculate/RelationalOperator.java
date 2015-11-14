package com.potter.tools.backtest.calculate;

import java.util.Map;
import java.util.TreeMap;

public enum RelationalOperator {
    GREATER_THAN(">"), LESS_THAN("<"), EQUALS("==");
    
    private String symbol;
    
    private RelationalOperator(String symbol) {
        this.symbol = symbol;
    }
    
    public String getSymbol() {
        return symbol;
    }
  
    public static RelationalOperator findByName(String name){
        for(RelationalOperator relationalOperator:RelationalOperator.values()){
            if(name!=null && name.equals(relationalOperator.name())){
                return relationalOperator;
            }
        }
        throw new RuntimeException("RelationalOperator.findByName("+name+") not found in Enum definition");
    }     
    
/*    public static RelationalOperator findBySymbol(String symbol){
        for(RelationalOperator relationalOperator:RelationalOperator.values()){
            if(symbol!=null && symbol.equals(relationalOperator.symbol)){
                return relationalOperator;
            }
        }
        throw new RuntimeException("RelationalOperator.findBySymbol("+symbol+") not found in Enum definition");
    }  */  
        
    public static Map<String,String> getRelationalOperatorsMap() {
        Map<String, String> relationalOperators = new TreeMap<String, String>();
        for(RelationalOperator relationalOperator:RelationalOperator.values()){
            relationalOperators.put(relationalOperator.name(), relationalOperator.getSymbol());
        }
        return relationalOperators;
    } 
}
