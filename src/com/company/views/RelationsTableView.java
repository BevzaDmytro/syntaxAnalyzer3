package com.company.views;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Map;

public class RelationsTableView  extends AbstractTableModel {

    private Map<String, Map<String,String>> relations ;

    public RelationsTableView(Map<String, Map<String, String>> relations) {
        this.relations = relations;
    }

    @Override
    public int getRowCount() {
        return this.relations.size();
    }

    @Override
    public int getColumnCount() {
        return this.relations.size()+1;
    }

    @Override
    public String getColumnName(int c) {
        if(c==0) return " ";
        return (String)  this.relations.keySet().toArray()[c-1]+"\n\n\n";

    }



    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        if(columnIndex == 0){
            return (String)  this.relations.keySet().toArray()[rowIndex];
        }
//        int pos = 1;
//        String value = (new ArrayList<String>(this.relations.)).get(pos);
        Map<String,String> map = (new ArrayList<Map<String,String>>(this.relations.values())).get(rowIndex);
        String result = (new ArrayList<String>(map.values())).get(columnIndex-1);
//        return this.relations.entrySet().toArray()
        return result;
    }
}
