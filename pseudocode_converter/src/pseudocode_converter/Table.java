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

import java.util.Map;
import java.util.TreeMap;

public class Table {
    
    static int count = 0;
    
    public Map<Integer,String> row;
    
    public Table(){
        this.row = new TreeMap();
    }
    
    public void addRow(String v){
        this.row.put(count, v);
        count++;
    }
    
    @Override
    public String toString(){
        String s = "";
        for (int i = 0; i<row.size();i++){
            s += row.get(i)+"\n";
        }
        return s;
    }
    
    public String getRow(int n){
        return this.row.get(n);
    }
    
    public void setRow(int n, String s){
        this.row.replace(n, s);
    }
    
    public int getCount(){
        return count;
    }
}