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

public class Analysis {
    public String[] file;
    
    public Analysis(String[] f) {
        this.file = f;
    }
    
    public boolean LexicalAnalysis() throws  RWordNotFoundException, VarDeclarationException, BadExpressionException{
        
        for(int i = 0; i < file.length; i++){
            String[] line = file[i].split(" ");
            
            if(line.length == 1){
                if (!"inicio".equals(line[0]) && !"fim".equals(line[0])){
                    throw new RWordNotFoundException("Palavra reservada incorreta."); 
                }
            } 
            else if (line.length == 3){
                switch (line[1]) {
                    case ":":
                        // Testando se as variáveis foram declaradas corretamente.
                        String[] var = line[2].split(",");
                        for (int y = 0; y < var.length;y++){
                            if(Character.isDigit(var[y].charAt(0))){
                                throw new VarDeclarationException("Erro na declaração das variáveis.");
                            }
                        }
                        if(!"inteiro".equals(line[0]) && !"real".equals(line[0])){
                            throw new VarDeclarationException("Erro na declaração das variáveis.");
                        }
                    break;
                    case "=":
                        String exp = line[2];
                        for(int y = 0; y<exp.length()-1; y++){
                            // Testando repetição de operador.
                            if(exp.charAt(y) == '+' || exp.charAt(y) == '-' || exp.charAt(y) == '*' || exp.charAt(y) == '/'){
                                if(exp.charAt(y+1) == '+' || exp.charAt(y+1) == '*' || exp.charAt(y+1) == '/'){
                                    throw new BadExpressionException("Erro na expressão.");
                                } else if(exp.charAt(y+1) == '-') {
                                    
                                }
                            }
                        }
                    break;
                }
            }
        }
        return true;
    }
    
}
