/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pseudocode_converter;

/**
 *
 * @author Matheus Dussin Bampi
 */
public class Symbol {
    public String symbol;
            
    public boolean isOperator(){
        return(symbol.equals("+") || symbol.equals("-") || symbol.equals("/") || symbol.equals("*"));
    }
    
    public boolean isOperand(){
        return(symbol.matches("^[0-9]*[.]{0,1}[0-9]*$"));
    }
    
    public boolean isOpenParenthesis(){
        return(symbol.equals("("));
    }
    
    public boolean isClosedParenthesis(){
        return(symbol.equals(")"));
    }
    
    public int testPriority(){
        if(symbol.equals("-") || symbol.equals("+")){
            return 1;
        }else if(symbol.equals("(")){
            return 0;
        }else{
            return 2;
        }
    }
    
    public Symbol calculate(Symbol s1, Symbol s2){
        float n1 = Float.parseFloat(s1.symbol);
        float n2 = Float.parseFloat(s2.symbol);
        switch(this.symbol){
            case "-":
                n1 = n2-n1;
                s1.symbol = ""+n1;
                break;
            case "+":
                n1 = n2+n1;
                s1.symbol = ""+n1;
                break;
            case "/":
                n1 = n2/n1;
                s1.symbol = ""+n1;      
                break;
            case "*":
                n1 = n2*n1;
                s1.symbol = ""+n1;
                break;
        }
        return s1;
    }
}
