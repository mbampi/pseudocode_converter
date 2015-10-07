/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pseudocode_converter;

import java.util.*;
/**
 *
 * @author Matheus Dussin Bampi
 */
public class PolishNotation {
    private LinkedList<Symbol> expression;
    
    public PolishNotation(String s){
        expression = new LinkedList<>();
        int i = 0;
        Symbol symb;
        Symbol saux;
        String str;
        String num = "";
        
        while(i < s.length()){
            str = "" + s.charAt(i);
            symb = new Symbol();
            symb.symbol = str;
            if(symb.isOperand()){
                num = num + symb.symbol;
            }else{
                if(!num.equals("")){
                    saux = new Symbol();
                    saux.symbol = num;
                    this.expression.add(saux);
                    num = "";
                }
                this.expression.add(symb);
            }
            i++;
            if(i == s.length() || s.length() == 1){
                if(!num.equals("")){
                    saux = new Symbol();
                    saux.symbol = num;
                    this.expression.add(saux);
                    num = "";
                }
            }
        }
    }
    
    public void toPostfix(){
        Stack<Symbol> convStack = new Stack<>();
        Symbol queueSymbol;
        LinkedList<Symbol> infixQueue = this.expression;
        LinkedList<Symbol> postfixQueue = new LinkedList<>();
        
        while(!infixQueue.isEmpty()){
            queueSymbol = infixQueue.removeLast();
            if(queueSymbol.isOperand()){
                postfixQueue.addFirst(queueSymbol);
            }else if(queueSymbol.isOpenParenthesis()){
                convStack.add(queueSymbol);
            }else if(queueSymbol.isOperator()){
                while(!convStack.empty() && queueSymbol.testPriority() < convStack.peek().testPriority()){
                    postfixQueue.addFirst((Symbol)convStack.pop());
                }
                convStack.add(queueSymbol);
            }else if(queueSymbol.isClosedParenthesis()){
                while(!convStack.empty() && !convStack.peek().isOpenParenthesis()){
                    postfixQueue.addFirst(convStack.pop());
                }
                convStack.pop();
            }
        }
        while(!convStack.empty()){
            postfixQueue.addFirst((Symbol)convStack.pop());
        }
        this.expression = postfixQueue;
        
    }
    
    public Symbol calculate(){
        Symbol s;
        Symbol s1;
        Symbol s2;
        Stack<Symbol> calcStack = new Stack<>();
        
        while(!this.expression.isEmpty()){
            s = this.expression.removeLast();
            if(s.isOperand()){
                calcStack.add(s);
            }else if(s.isOperator()){
                s1 = calcStack.pop();
                s2 = calcStack.pop();
                s = s.calculate(s1, s2);
                calcStack.add(s);
            }
        }
        s = calcStack.pop();
        return s;
    }
}
