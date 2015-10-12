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

import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    
    public static void main(String[] args) throws RWordNotFoundException, VarDeclarationException, BadExpressionException {
        Scanner read = new Scanner(System.in);
        System.out.println("Digite o arquivo que quer converter!");
        String fileName = read.nextLine();
        ArrayList fileList = new ArrayList<String>();
        String[] file = null;
        
        try {
            FileReader fr = new FileReader("/home/bruno/"+fileName);
            BufferedReader br = new BufferedReader(fr);
            
            String line = br.readLine();
            
            for(int i = 1; line != null; i++){
                fileList.add(line);
                line = br.readLine();
            }
            
            file = new String[fileList.size()];
            file = (String[]) fileList.toArray(file);
            
            for(String s : file)
                System.out.println(s);
            
            Analysis a = new Analysis(file);
            if(a.LexicalAnalysis()){
                System.out.println("Sem erros no código.");
            } else {
                // Tratar erros...
            }
            br.close();
        }
        
        catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
            e.getMessage());
        }
        
    }
    
}
