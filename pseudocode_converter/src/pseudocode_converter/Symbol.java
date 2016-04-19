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
            
    public boolean isOperator(){ //MODIFICADO: Adicionado op logicos e relacionais
        return(symbol.equals("+") || symbol.equals("-") || symbol.equals("/") || symbol.equals("*") ||
            symbol.equals("não") || symbol.equals("nao") || symbol.equals("&") || symbol.equals("ou")||
            symbol.equals("<>") || symbol.equals("==") || symbol.equals("<") || symbol.equals(">") || 
            symbol.equals("<=") || symbol.equals(">="));
    }
    
    public boolean isOperand(){
        return(!this.isOperator() && !this.isOpenParenthesis() && !this.isClosedParenthesis());//MODIFICADO: isOperand() não levava em conta variaveis, por não serem numeros. Ex: "n1"
    }
    
    public boolean isOpenParenthesis(){
        return(symbol.equals("("));
    }
    
    public boolean isClosedParenthesis(){
        return(symbol.equals(")"));
    }
    
    public int testPriority(){
        switch(symbol){
            case "*": case "/":
                return 4;
            case "+": case "-":
                return 3;
            case ">": case ">=": case "<": case "<=": case "==": case "<>":
                return 2;
            case "não": case "e": case "ou": case "nao":
                return 1;
            default:
                return 0;
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
