/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pseudocode_converter;

import java.util.LinkedList;

/**
 *
 * @author bruno
 */
public class Analysis {

    public String[] file;
    public Table t;
    
    public Analysis(String[] f) {
        this.file = f;
    }

    public void testStartEnd(String[] line) throws RWordNotFoundException {
        if (!"inicio".equals(line[0].toLowerCase()) && !"fim".equals(line[0].toLowerCase()))
            throw new RWordNotFoundException("Palavra reservada incorreta.");
    }
    
    public void convertDeclaration(String esq, String dir, boolean flag) throws VarDeclarationException, RWordNotFoundException {
        if (flag) {
            // Testando se as variáveis foram declaradas corretamente.
            String[] var = dir.split("\\s*\\,\\s*");
            for (String var1 : var) {
                if (Character.isDigit(var1.charAt(0)))
                    throw new VarDeclarationException("Erro na declaração das variáveis.");
            }
            if (!"inteiro".equals(esq.toLowerCase()) && !"real".equals(esq.toLowerCase()) && !"logico".equals(esq.toLowerCase()))
                throw new RWordNotFoundException("Palavra reservada incorreta.");
        } else {
            int c = 0;
            String[] var = dir.split("\\s*\\,\\s*");
            String s = (esq + ";");
            for (String var1 : var) {
                c++;
                s += (var1 + ";");
            }
            t.addRow(c + ";" + s);
        }
    }

    public void convertAttribution(String esq, String dir, boolean flag) throws BadExpressionException {
        if (flag) {
            String exp = dir;
            for (int y = 0; y < exp.length() - 1; y++) {
                // Testando repetição de operador.
                if (exp.charAt(y) == '+' || exp.charAt(y) == '-' || exp.charAt(y) == '*' || exp.charAt(y) == '/') {
                    if (exp.charAt(y + 1) == '*' || exp.charAt(y + 1) == '/') // retirado '+' e '-' devido a n negativo e positivo
                        throw new BadExpressionException("Erro na expressão.");
                }
            }
        } else {
            String exp = dir;
            String AT;
            PolishNotation pn = new PolishNotation(exp);
            pn.toPostfix();
            String e = pn.toString();
            AT = "AT;" + esq + ";" + e;
            t.addRow(AT);
        }
    }

    public void convertPrinting(String conteudo) {
        boolean bool = true;
        char letra;
        int ultimaVirgula = 0;
        LinkedList<String> l = new LinkedList();
        if (conteudo.contains(",")) {
            for (int i = 0; i < conteudo.length(); i++) {
                letra = conteudo.charAt(i);
                switch (letra) {
                    case '"':
                        bool = !bool;
                        break;
                    case ',':
                        if (bool) {
                            l.add(conteudo.substring(ultimaVirgula, i).trim());//.replaceAll("[^\\s+|\\s+$]", "")); remove espacos nas extremidades
                            ultimaVirgula = i + 1;
                        }
                        break;
                }
            }
        }
        l.add(conteudo.substring(ultimaVirgula, conteudo.length() - 2).trim());//.replace("[^\\s+|\\s+$]", "")); remove espacos nas extremidades
        for (String l1 : l) {
            if (!l1.isEmpty()) {
                l1 = l1.replace("[^\\s+|\\s+$]", "");
                if (l1.contains("" + '"')) {
                    t.addRow("OUTS;" + l1 + ";");
                } else {
                    t.addRow("OUTV;" + l1 + ";");
                }
            }
        }
    }

    public void convertReading(String conteudo) {
        String saida;
        saida = "IN;";
        String[] variaveis = conteudo.split("[c\\s*,\\s*]");
        for (String var : variaveis)
            saida += var + ";";
        t.addRow(saida);
    }

    public void convertIF(String exp) {
        String saida;
        saida = "SE;";
        saida += (exp.trim() + ";");
        t.addRow(saida);
    }
    
    public void convertWHILE(String exp){
        String saida;
        saida = "ENQUANTO;";
        saida += (exp.trim() + ";");
        t.addRow(saida);
    }

    public boolean lexicalAnalysis() throws RWordNotFoundException, VarDeclarationException, BadExpressionException {
        for (String linha : file) {
            if (linha.matches(":")) {
                String[] lado = linha.split(":", 2); // divide em 2 substring que dividem no primeiro ':' encontrado
                this.convertDeclaration(lado[0], lado[1], true);
            } else if (linha.matches("=")) {
                String[] lado = linha.split("=", 2); // divide em 2 substring que dividem no primeiro ':' encontrado
                this.convertAttribution(lado[0], lado[1], true);
            }
        }
        return true;
    }
    
    public void linesCounterIF(boolean b){
        int nivel = 0;
        for(int n=t.getCount()-1; n>0; n--){
            if(b && t.getRow(n).contains("SENAO")){
                if(nivel == 0){
                    t.setRow(n, t.getRow(n) + String.valueOf(t.getCount() - n));
                    return;
                }
            }else if(t.getRow(n).contains("FIM_SE")){
                nivel++;
            }else if(t.getRow(n).contains("SE")){
                if(nivel == 0){
                    t.setRow(n, t.getRow(n) + String.valueOf(t.getCount()-n));
                    return;
                }else
                    nivel--;
            }
        }
    }     
    
    public void linesCounterWHILE(){
        int nivel = -1;
        for(int n=t.getCount()-1; n>0; n--){
            if(t.getRow(n).contains("FIM_ENQUANTO")){
                nivel++;
            }else if(t.getRow(n).contains("ENQUANTO")){
                if(nivel == 0){
                    t.setRow(n, t.getRow(n) + String.valueOf(t.getCount()-n-1)); // NLinhas no começo
                    t.setRow(t.getCount()-1, t.getRow(t.getCount()-1) + String.valueOf(t.getCount()-n-1)); // NLinhas no final
                    return;
                }else
                    nivel--;
            }
        }
    } 
        
    public String syntacticAnalysis() throws VarDeclarationException, RWordNotFoundException, BadExpressionException {
        t = new Table();
        t.addRow("INICIO_CODIGO;");
        for (String linha : file) {
            if (linha.contains(":")) {
                String[] lado = linha.split(":", 2); // Divide em 2 substring que dividem no primeiro ':' encontrado
                this.convertDeclaration(lado[0].trim(), lado[1].trim(), false);
            }else if (linha.toLowerCase().contains("fimenquanto")) {
                t.addRow("FIM_ENQUANTO;");
                this.linesCounterWHILE();
            }else if (linha.toLowerCase().contains("enquanto")) {
                this.convertWHILE(linha.split("\\(", 2)[1].split("\\)", 2)[0].trim());
            }else if (linha.toLowerCase().contains("senao")) {//Acha if anterior e adiciona nLinhas após o então;
                this.linesCounterIF(false);
                t.addRow("SENAO;");
            } else if (linha.toLowerCase().contains("fimse")) {//Acha Senão ou If anterior e adiciona nLinhas após o então;
                this.linesCounterIF(true);
                t.addRow("FIM_SE;");
            } else if (linha.toLowerCase().contains("se")) { //uso do lowerCase para suportar M e m
                this.convertIF(linha.split("\\(", 2)[1].split("\\)entao", 2)[0].trim());  //.replace("[\\s*)\\s*]", ""));
            } else if (linha.toLowerCase().contains("escreva")) {
                this.convertPrinting(linha.split("\\(", 2)[1].trim());  //.split("[\\s*);\\s*$]", 2)[0]); Tirar ); do final
            } else if (linha.toLowerCase().contains("leia")) {
                this.convertReading(linha.split("\\(", 2)[1].trim().split("[\\s*);\\s*$]", 2)[0].trim());
            } else if (linha.contains("=")) {
                String[] lado = linha.split("=", 2); // divide em 2 substring que dividem no primeiro '=' encontrado
                this.convertAttribution(lado[0].trim(), lado[1].replaceAll(" ", ""), false);
            }
        }
        t.addRow("FIM_CODIGO;");
        return t.toString();
    }
}