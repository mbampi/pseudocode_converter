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

  import java.util.ArrayList;

public class Analysis {
    public String[] file;
    final ArrayList word;

    public Analysis(String[] f) {
        this.file = f;
        this.word = new ArrayList<>();
        word.add("inicio");
        word.add("inteiro");
        word.add("real");
        word.add("fim");
        word.add(":");
        word.add("=");
    }
    
    public void LexicalAnalysis() throws RWordNotFoundException, VarDeclarationException{
        for(int i = 0; i < file.length; i++){
            String[] line = file[i].split(" ");
            
            if(line.length == 1){
                if(line[0] != "inicio" || line[0] != "fim"){
                    throw new RWordNotFoundException("Palavra reservada incorreta.");
                }
            } 
            else if (line.length == 3){
                if(line[1] == ":"){ // Testando se as variáveis foram declaradas corretamente.
                    String[] var = line[2].split(",");
                    for (int y = 0; y < var.length;y++){
                        
                        /* Gambiarra altamente perigosa. Não faça isso em casa.
                        try {
                            int s = Integer.parseInt(var[i].charAt(0)+"");
                            throw new VarDeclarationException("Erro na declaração das variáveis.");
                        }
                        catch (NumberFormatException e){}
                        */
                        
                        if(Character.isDigit(var[y].charAt(0))){
                            throw new VarDeclarationException("Erro na declaração das variáveis.");
                        }
                    }
                }
                else
                if(line[1] == "="){
                    boolean bool = false;
                    String exp = line[2];
                    for(int y = 0; bool == false; y++){
                        if(exp.charAt(y) == '+' || exp.charAt(y) == '-' || exp.charAt(y) == '*' || exp.charAt(y) == '/'){
                            
                        }
                    }
                    //String[] exp = line[2].split(" ");
                }
            }
        }
    }
    
}
