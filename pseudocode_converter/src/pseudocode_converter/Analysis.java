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
        
        for (String file1 : file) {
            String[] line = file1.split(" ");
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
                        for (String var1 : var) {
                            if (Character.isDigit(var1.charAt(0))) {
                                throw new VarDeclarationException("Erro na declaração das variáveis.");
                            }
                        }
                        if(!"inteiro".equals(line[0]) && !"real".equals(line[0])){
                            throw new RWordNotFoundException("Palavra reservada incorreta.");
                        }
                        break;
                    case "=":
                        String exp = line[2];
                        for(int y = 0; y<exp.length()-1; y++){
                            // Testando repetição de operador.
                            if(exp.charAt(y) == '+' || exp.charAt(y) == '-' || exp.charAt(y) == '*' || exp.charAt(y) == '/'){
                                if(exp.charAt(y+1) == '+' || exp.charAt(y+1) == '-' || exp.charAt(y+1) == '*' || exp.charAt(y+1) == '/'){
                                    throw new BadExpressionException("Erro na expressão.");
                                }
                            }
                        }
                        break;
                }
            }
        }
        return true;
    }
    
    public String SyntacticAnalysis() {
        Table t = new Table();
        String AT = "";
        for (String file1 : file){
            String[] line = file1.split(" ");
            if(line.length == 3){
                if(":".equals(line[1])){
                    int c = 0;
                    String[] var = line[2].split(",");
                    String s = line[0]+";";
                    for(String var1 : var){
                        c++;
                        s += var1+";";
                    }
                    t.addRow(c+";"+s);
                }
                if("=".equals(line[1])){
                    String exp = line[2];
                    if(exp.length() == 1 ){
                       AT += "AT;"+line[0]+";"+exp+"\n";
                    }
                    else {
                       PolishNotation pn = new PolishNotation(exp);
                       pn.toPostfix();
                       String e = pn.toString();
                       AT += "AT;"+line[0]+";"+e+"\n";
                       t.addRow(AT);
                    }
                }
                if("escreva".equals(line[0])){
                    for (String line1: line){
                        
                    }
                }
                /*if("se".equals(line[0])){
                    for (String line1 : line) {
                        switch(line1){
                            case "==":
                                String esq = "";
                                String dir = "";
                                for (int i = 0; line[i] != line1;i++){
                                   esq += line[i];
                                }
                                for (int i = line.length; line[i] != line1; i--){
                                   dir += line[i];
                                }
                                break;
                            
                            case "!=":
                                break;
                            
                            case ">":
                                break;
                            
                            case "<":
                                break;
                    }
                }*/
            }
        }
        return t.toString();
    }
    
}
