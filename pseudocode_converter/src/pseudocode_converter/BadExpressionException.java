/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pseudocode_converter;

/**
 *
 * @author bruno
 */
public class BadExpressionException extends Exception {
    public BadExpressionException(){}
    
    public BadExpressionException(String message){
        super(message);
    }
}
