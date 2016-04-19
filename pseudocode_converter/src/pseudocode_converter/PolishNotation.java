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

    // Separa a expressão recebida em uma LinkedList (ja agrupa operadores e operandos com char>1)
    public PolishNotation(String s) {
        expression = new LinkedList<>();
        int i = 0;
        Symbol symb;
        Symbol saux;
        String str;
        String num = "";
        // Transforma operadores aritmeticos duplos em únicos (+- = -),(-+ = -),(-- = +),(++ = +)
        s = s.replaceAll("\\+\\-", "\\-").replaceAll("\\-\\+", "\\-").replaceAll("\\+\\+", "\\+").replaceAll("(\\-\\-)", "\\+");
        // Separa os conjuntos, colocando-os em uma LinkedList (ex: "var2=var1+12" -> [var2, =, var1, +, 12])
        while (i < s.length()) {
            str = "" + s.charAt(i);
            // Agrupa operadores com char>1 (<>, <=, <=, ==)
            if ('<' == s.charAt(i) && '>' == s.charAt(i + 1) || '<' == s.charAt(i) && '=' == s.charAt(i + 1)
                    || '>' == s.charAt(i) && '=' == s.charAt(i + 1) || '=' == s.charAt(i) && '=' == s.charAt(i + 1)) {
                i++;
                str += s.charAt(i);
            }
            symb = new Symbol();
            symb.symbol = str;
            if (symb.isOperand())
                num = num + symb.symbol;
            else {
                if (!num.equals("")) {
                    saux = new Symbol();
                    saux.symbol = num;
                    this.expression.add(saux);
                    num = "";
                }
                this.expression.add(symb);
            }
            i++;
            // Caso esteja na ultima posição da String
            if (i == s.length() || s.length() == 1) {
                if (!num.equals("")) {
                    saux = new Symbol();
                    saux.symbol = num;
                    this.expression.add(saux);
                    num = "";
                }
            }
        }
    }

    // Transforma a LinkedList em uma expressão Pós-Fixa
    public void toPostfix() {
        Stack<Symbol> convStack = new Stack<>();
        Symbol queueSymbol;
        LinkedList<Symbol> infixQueue = this.expression;
        LinkedList<Symbol> postfixQueue = new LinkedList<>();

        while (!infixQueue.isEmpty()) {
            queueSymbol = infixQueue.pop();
            if (queueSymbol.isOperand()) {
                postfixQueue.add(queueSymbol);
            } else if (queueSymbol.isOpenParenthesis()) {
                convStack.add(queueSymbol);
            } else if (queueSymbol.isOperator()) {
                while (!convStack.empty() && queueSymbol.testPriority() < convStack.peek().testPriority()) {
                    postfixQueue.add((Symbol) convStack.pop());
                }
                convStack.add(queueSymbol);
            } else if (queueSymbol.isClosedParenthesis()) {
                while (!convStack.empty() && !convStack.peek().isOpenParenthesis()) {
                    postfixQueue.add(convStack.pop());
                }
                convStack.pop();
            }
        }
        while (!convStack.empty()) {
            postfixQueue.add((Symbol) convStack.pop());
        }
        this.expression = postfixQueue;
    }

    public Symbol calculate() {
        Symbol s;
        Symbol s1;
        Symbol s2;
        Stack<Symbol> calcStack = new Stack<>();

        while (!this.expression.isEmpty()) {
            s = this.expression.pop();
            if (s.isOperand()) {
                calcStack.add(s);
            } else if (s.isOperator()) {
                s1 = calcStack.pop();
                s2 = calcStack.pop();
                s = s.calculate(s1, s2);
                calcStack.add(s);
            }
        }
        s = calcStack.pop();
        return s;
    }
    
    // Retorna a expressão em forma pós-fixa dividida por espaços
    @Override
    public String toString() {
        String str = "";
        for (Symbol s : this.expression) {
            str += s.symbol + " ";
        }
        return str;
    }
}
