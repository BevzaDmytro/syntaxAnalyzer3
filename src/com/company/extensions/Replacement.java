package com.company.extensions;

import java.util.ArrayList;

public class Replacement {
    private ArrayList<LinguisticUnit> units = new ArrayList<>();

    public ArrayList<LinguisticUnit> getUnits() {
        return units;
    }

    public void addUnit(NonTerminal unit){
        this.units.add(unit);
    }
    public void addUnit(Terminal unit){
        this.units.add(unit);
    }
}
