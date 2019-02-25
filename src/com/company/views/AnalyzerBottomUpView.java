package com.company.views;

import com.company.extensions.Lexem;
import com.company.extensions.PassOfSyntaxAnalyzer;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class AnalyzerBottomUpView  extends AbstractTableModel {
    private ArrayList<PassOfSyntaxAnalyzer> analyzeLog;

    AnalyzerBottomUpView(ArrayList<PassOfSyntaxAnalyzer> analyzeLog) {
        super();
        this.analyzeLog = analyzeLog;
    }

    public int getRowCount() {
        return this.analyzeLog.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }
    public Object getValueAt(int r, int c) {
        switch (c) {
            case 0:
                return r;
            case 1:
                return this.analyzeLog.get(r).getStackView();
            case 2:
                return this.analyzeLog.get(r).getCurrentRelation();
            case 3:
                return this.analyzeLog.get(r).getInput();
            default:
                return "Other Column";
        }
    }

    @Override
    public String getColumnName(int c) {
        switch (c) {
            case 0:
                return "No";
            case 1:
                return "Name";
            case 2:
                return "Code";
            case 3:
                return "IDN";
            case 4:
                return "CON";
            default:
                return "Other Column";
        }
    }
}
