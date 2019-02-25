package com.company.extensions;

import java.util.ArrayList;

public class Rule {
    private NonTerminal nonTerminal;
    private ArrayList<Replacement> replacements;

    public NonTerminal getNonTerminal() {
        return nonTerminal;
    }

    public ArrayList<Replacement> getReplacements() {
        return replacements;
    }

    public void setNonTerminal(NonTerminal nonTerminal){
        this.nonTerminal = nonTerminal;
    }

    public void setReplacements(ArrayList<Replacement> replacements) {
        this.replacements = replacements;
    }
}
