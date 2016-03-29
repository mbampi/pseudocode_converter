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
        if (!"inicio".equals(line[0].toLowerCase()) && !"fim".equals(line[0].toLowerCase())) {//MODIFICADO:Adicionado .toLowerCase() para suportar letras M e m
            throw new RWordNotFoundException("Palavra reservada incorreta.");
        }
    }

    public void testDeclaration(String esq, String dir, boolean flag) throws VarDeclarationException, RWordNotFoundException {
        if (flag) {
            // Testando se as variáveis foram declaradas corretamente.
            String[] var = dir.split("\\s*\\,\\s*");
            for (String var1 : var) {
                if (Character.isDigit(var1.charAt(0))) {
                    throw new VarDeclarationException("Erro na declaração das variáveis.");
                }
            }
            if (!"inteiro".equals(esq.toLowerCase()) && !"real".equals(esq.toLowerCase())) {//.toLowerCase() para suportar letras M e m
                throw new RWordNotFoundException("Palavra reservada incorreta.");
            }
        } else {
            int c = 0;
            String[] var = dir.split("\\s*\\,\\s*");
            String s = esq + ";";
            for (String var1 : var) {
                c++;
                s += var1 + ";";
            }
            t.addRow(c + ";" + s);
        }
    }

    /*
    String esq e dir no ligar de String[] line
    esq == line[0] && dir == line[2]
    retirado if e else que comparava length == 1
     */
    public void testAttribution(String esq, String dir, boolean flag) throws BadExpressionException {
        if (flag) {
            String exp = dir;
            for (int y = 0; y < exp.length() - 1; y++) {
                // Testando repetição de operador.
                if (exp.charAt(y) == '+' || exp.charAt(y) == '-' || exp.charAt(y) == '*' || exp.charAt(y) == '/') {
                    if (exp.charAt(y + 1) == '*' || exp.charAt(y + 1) == '/') { // retirado '+' e '-' devido a n negativo e positivo
                        throw new BadExpressionException("Erro na expressão.");
                    }
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

    public void testPrinting(String conteudo) {
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

    public void testReading(String conteudo) {
        int i;
        String saida;
        saida = "IN;";
        String[] variaveis = conteudo.split("[\\s*,\\s*]");
        for (String var : variaveis) {
            saida += var + ";";
        }
        t.addRow(saida);
    }

    public void testIfElse(String[] line) {

    }

    public boolean LexicalAnalysis() throws RWordNotFoundException, VarDeclarationException, BadExpressionException {
        for (String linha : file) { //MOD: String "file1" por "linha"
            if (linha.matches(":")) {
                String[] lado = linha.split(":", 2); // divide em 2 substring que dividem no primeiro ':' encontrado
                this.testDeclaration(lado[0], lado[1], true);
            } else if (linha.matches("=")) {
                String[] lado = linha.split("=", 2); // divide em 2 substring que dividem no primeiro ':' encontrado
                this.testAttribution(lado[0], lado[1], true);
            }
        }
        return true;
    }

    public String SyntacticAnalysis() throws VarDeclarationException, RWordNotFoundException, BadExpressionException {
        t = new Table();
        for (String linha : file) {
            if (linha.contains(":")) {
                String[] lado = linha.split(":", 2); // Divide em 2 substring que dividem no primeiro ':' encontrado
                this.testDeclaration(lado[0].trim(), lado[1].trim(), false);
            } else if ("se".matches(linha.toLowerCase())) { //uso do lowerCase para suportar M e m

            } else if ("senao".matches(linha.toLowerCase())) {

            } else if ("fimse".matches(linha.toLowerCase())) {

            } else if (linha.toLowerCase().contains("escreva")) {
                this.testPrinting(linha.split("\\(", 2)[1].trim());  //.split("[\\s*);\\s*$]", 2)[0]); Tirar ); do final
            } else if (linha.toLowerCase().contains("leia")) {
                this.testReading(linha.split("\\(", 2)[1].trim().split("[\\s*);\\s*$]", 2)[0].trim());
            } else if (linha.contains("=")) {
                String[] lado = linha.split("=", 2); // divide em 2 substring que dividem no primeiro '=' encontrado
                this.testAttribution(lado[0].trim(), lado[1].replaceAll(" ", ""), false);
            }
        }
        return t.toString();
    }
}