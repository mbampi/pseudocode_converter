/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pseudocode_converter;

import java.lang.String;

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

    public void testStartEnd(String[] line) throws RWordNotFoundException{
        if (!"inicio".equals(line[0].toLowerCase()) && !"fim".equals(line[0].toLowerCase())){
                    throw new RWordNotFoundException("Palavra reservada incorreta.");
                }
    }

    public void testDeclaration(String[] line,boolean flag) throws VarDeclarationException, RWordNotFoundException {
        // Alterado ---------------------
        if(flag) {
            String[] var = line[2].split(",");
            for (String var1 : var) {
                if (Character.isDigit(var1.charAt(0))) {
                    throw new VarDeclarationException("Erro na declaração das variáveis.");
                }
            }
            if(!"inteiro".equals(line[0].toLowerCase()) && !"real".equals(line[0].toLowerCase())){//MODIFICADO:Adicionado .toLowerCase() para suportar letras M e m
                throw new RWordNotFoundException("Palavra reservada incorreta.");
            }
        }
        else {
            int c = 0;
            String[] var = line[2].split(",");
            String s = line[0]+";";
            for(String var1 : var){
                c++;
                s += var1+";";
            }
            t.addRow(c+";"+s);
        }
    }

    public void testAttribution(String[] line, boolean flag) throws BadExpressionException{
        if(flag) {
            String exp = line[2];
            for(int y = 0; y<exp.length()-1; y++){
                // Testando repetição de operador.
                if(exp.charAt(y) == '+' || exp.charAt(y) == '-' || exp.charAt(y) == '*' || exp.charAt(y) == '/'){
                    if(exp.charAt(y+1) == '+' || exp.charAt(y+1) == '-' || exp.charAt(y+1) == '*' || exp.charAt(y+1) == '/'){
                        throw new BadExpressionException("Erro na expressão.");
                    }
                }
            }
        }
        else {
            String exp = line[2];
            String AT = "";
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
    }

    public void testPrinting(String[] line) {

            if (line[1].charAt(0) == '"' && line[1].charAt(line[1].length()-1) == '"'){
                t.addRow("OUTS;"+line[1]+";");
            } else {
                t.addRow("OUTV;"+line[1]+";");
            }
    }

    public void testReading(String[] line) {
        int i;
        String var, saida;
        saida = "IN;";
        for(i=1;line[i].charAt(line[i].length()-1) == ','; i++){
            var = line[i].substring(0,line[i].length()-1);
            saida += var+";";
        }
        saida += line[i]+";";
        t.addRow(saida);
    }

    public void testIfElse(String[] line) {

    }

    public boolean LexicalAnalysis() throws  RWordNotFoundException, VarDeclarationException, BadExpressionException{

        for (String file1 : file) {
            String[] line = file1.split("\\s+");//MODIFICADO: não suportava multiplos espaços.
            if(line.length == 1){
<<<<<<< HEAD

            }
            else if (line.length == 3){
                switch (line[1]) {
                    case ":":
                        this.testAttribution(line, true);
                    break;
=======
                if (!"inicio".equals(line[0].toLowerCase()) && !"fim".equals(line[0].toLowerCase())){//MODIFICADO:Adicionado .toLowerCase() para suportar letras M e m
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
                        if(!"inteiro".equals(line[0].toLowerCase()) && !"real".equals(line[0].toLowerCase())){//MODIFICADO:Adicionado .toLowerCase() para suportar letras M e m
                            throw new RWordNotFoundException("Palavra reservada incorreta.");
                        }
                        break;
>>>>>>> 3c5fe00b0b038f76defc32df54a07658e947232e
                    case "=":
                        this.testDeclaration(line, true);
                    break;
                }
            }
        }
        return true;
    }

    public String SyntacticAnalysis() throws VarDeclarationException, RWordNotFoundException, BadExpressionException {
        t = new Table();
        for (String file1 : file){
            String[] line = file1.split("\\s+");//MODIFICADO: não suportava multiplos espaços.OBS:deve ser tratado para retirar espaços duplicados
            if(line.length == 3){
                if(":".equals(line[1])){
<<<<<<< HEAD
                    this.testDeclaration(line, false);
                }
                if("=".equals(line[1])){
                    this.testAttribution(line, false);
=======
                    int c = 0;
                    String[] var = line[2].split("\\s*\\,\\s*");//MODIFICADO: não suportava espaços antes e depois da virgula
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
                       AT = "AT;"+line[0]+";"+exp;//MODIFICAOD += por =; RETIRADO \n
                       t.addRow(AT); //MODIFICADO ADICIONADO .addrow(AT)
                    }
                    else {
                       PolishNotation pn = new PolishNotation(exp);
                       pn.toPostfix();
                       String e = pn.toString();
                       AT = "AT;"+line[0]+";"+e;//+= por =; RETIRADO \n
                       t.addRow(AT);
                    }
>>>>>>> 3c5fe00b0b038f76defc32df54a07658e947232e
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
<<<<<<< HEAD
                if("escreva".equals(line[0])){
                    this.testPrinting(line);
                }
                if("leia".equals(line[0])){
                    this.testReading(line);
                }
=======
            } else {
                if("escreva".equals(line[0].toLowerCase())){//MODIFICADO:Adicionado .toLowerCase() para suportar letras M e m
                    if (line[1].charAt(0) == '"' && line[1].charAt(line[1].length()-1) == '"'){
                        t.addRow("OUTS;"+line[1]+";");
                    } else {
                        t.addRow("OUTV;"+line[1]+";");
                    }
                }
            }
            if("leia".equals(line[0].toLowerCase())){//MODIFICADO:Adicionado .toLowerCase() para suportar letras M e m
                    int i;
                    String var, saida;
                    saida = "IN;";
                    for(i=1;line[i].charAt(line[i].length()-1) == ','; i++){
                        var = line[i].substring(0,line[i].length()-1);
                        saida += var+";";
                    }
                    saida += line[i]+";";
                    t.addRow(saida);
>>>>>>> 3c5fe00b0b038f76defc32df54a07658e947232e
            }
        }
        return t.toString();
    }

}
