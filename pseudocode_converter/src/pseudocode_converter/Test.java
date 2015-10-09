/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pseudocode_converter;

import java.util.Scanner;

/**
 *
 * @author Matheus
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner scan = new Scanner(System.in);
        String s;
        System.out.println("\nDigite a Expressão Matemática: ('s' para sair)");
        s = scan.next();
        PolishNotation pn = new PolishNotation(s);
        pn.toPostfix();
        s = pn.calculate().symbol;
        System.out.println("\nO Resultado é: " + s);
        s = scan.next(); 
    }
}
