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

public class Main {
    
    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        System.out.println("Digite o arquivo que quer converter!");
        String fileName = read.nextLine();
        String file = "";
        
        System.out.println("\n Conteúdo do arquivo do texto:\n");
        
        try {
            FileReader fr = new FileReader("/home/bruno/"+fileName);
            BufferedReader br = new BufferedReader(fr);
            
            String line = br.readLine(); // Lê a primeira linha
            
            while (line != null) {
                file = file + br.readLine() + "\n";
                line = br.readLine();
            }
            Analysis a = new Analysis(file);
            a.LexicalAnalysis();
            br.close();
        }
        
        catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
            e.getMessage());
        }
        
    }
    
}
