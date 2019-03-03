package com.company.analyzers;

import com.company.extensions.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class RelationsAnalyzer {
    private ArrayList<Rule> rules;
    private Map<String, Map<String,String>> relations ;
    private Map<LinguisticUnit,LinguisticUnit> equals = new LinkedHashMap<>();
    private  ArrayList<LinguisticUnit> terminalsFirstPlus = new ArrayList<>();
    private NonTerminal lastNonTerminal;


    public Map<String, Map<String, String>> getRelations() {
        return relations;
    }

    public RelationsAnalyzer(ArrayList<Rule> rules, Map<String, Map<String, String>> relations) {
        this.rules = rules;
        this.relations = relations;
    }

    public ArrayList<Rule> getRules() {
        return rules;
    }

    public void setEqualRelations(){
      //  Replacement replacement = new Replacement();

        for (Rule rule: this.rules   ) {
            for (Replacement replacement :rule.getReplacements() ) {
                for(int i = 1; i< replacement.getUnits().size();i++){
                    String currentUnitName = replacement.getUnits().get(i).getName();
                    String prevUnitName = replacement.getUnits().get(i-1).getName();

                    //додавання у допоміжний список відношень дорівнює
                    equals.put(replacement.getUnits().get(i-1),replacement.getUnits().get(i));

                    //додавання відношення у таблицю
                    Map<String, String> map = this.relations.get(prevUnitName);
                    if(!map.get(currentUnitName).contains("=")) {
                        map.put(currentUnitName, map.get(currentUnitName) + "=");
                    }
                    this.relations.put(prevUnitName,map);

                }
            }
        }

    }

    public void setLessRelations(){
        for (Map.Entry entry : this.equals.entrySet() ) {
            if(entry.getValue() instanceof  NonTerminal){
                ArrayList<LinguisticUnit> less = this.firstPlus((NonTerminal) entry.getValue());
                this.terminalsFirstPlus.clear();
                for ( LinguisticUnit linguisticUnit: less  ) {
                    String currentUnitName = linguisticUnit.getName();
                    String prevUnitName = ((LinguisticUnit) entry.getKey()).getName();


                    //додавання відношення у таблицю
                    Map<String, String> map = this.relations.get(prevUnitName);
                    if(!map.get(currentUnitName).contains("<")) {
                        map.put(currentUnitName, map.get(currentUnitName) + "<");
                    }
                    this.relations.put(prevUnitName,map);
                }

            }
            else if(entry.getKey() instanceof NonTerminal){
                ArrayList<LinguisticUnit> great = this.lastPlus((NonTerminal) entry.getKey());
                this.terminalsFirstPlus.clear();
                for ( LinguisticUnit linguisticUnit: great  ) {
                    String prevUnitName = linguisticUnit.getName();
                    String currentUnitName = ((LinguisticUnit) entry.getValue()).getName();


                    //додавання відношення у таблицю
                    Map<String, String> map = this.relations.get(prevUnitName);
                    if(!map.get(currentUnitName).contains(">")) {
                        map.put(currentUnitName, map.get(currentUnitName) + ">");
                    }
                    this.relations.put(prevUnitName,map);
                }
            }
            if(entry.getKey() instanceof NonTerminal && entry.getValue() instanceof  NonTerminal){
                ArrayList<LinguisticUnit> lastPlus = this.lastPlus((NonTerminal) entry.getKey());
                this.terminalsFirstPlus.clear();
                ArrayList<LinguisticUnit> firstPlus = this.firstPlus((NonTerminal) entry.getValue());
                this.terminalsFirstPlus.clear();
                for ( LinguisticUnit linguisticUnit: lastPlus  ) {
                    for (LinguisticUnit linguisticUnit1 : firstPlus  ){

                        String prevUnitName = linguisticUnit.getName();
                        String currentUnitName = linguisticUnit1.getName();

                        //додавання відношення у таблицю
                        Map<String, String> map = this.relations.get(prevUnitName);
                        if(!map.get(currentUnitName).contains(">")) {
                            map.put(currentUnitName, map.get(currentUnitName) + ">");
                        }
                        this.relations.put(prevUnitName, map);
                    }
                }
            }
        }
    }

    public void analyze(){
        this.setEqualRelations();
        this.setLessRelations();
        System.out.println("Relations established");
    }

    public ArrayList<LinguisticUnit> lastPlus(NonTerminal nonTerminal){
        ArrayList<LinguisticUnit> terminals = new ArrayList<>();
        Rule currentRule = new Rule();
        for ( Rule rule: this.rules) {
            if(rule.getNonTerminal().getName().equals(nonTerminal.getName())){
                currentRule = rule;
                break;
            }
        }

        for (Replacement replacement : currentRule.getReplacements() ) {
            LinguisticUnit unit = replacement.getUnits().get(replacement.getUnits().size()-1);
            if(unit instanceof Terminal){
                if(!this.hasUnit(terminals, unit))
                terminals.add(unit);
                this.terminalsFirstPlus.add(unit);
            }
            else{
//                if(!this.hasUnit(terminals, unit)) {
                if(!this.hasUnit(this.terminalsFirstPlus, unit)) {
                    terminals.add(unit);
                    this.terminalsFirstPlus.add(unit);
//                    this.lastNonTerminal = (NonTerminal) unit;
                    terminals.addAll(lastPlus((NonTerminal) unit));
                }
            }
        }

        return terminals;
    }

    private boolean hasUnit(ArrayList<LinguisticUnit> units, LinguisticUnit unit){
        for (LinguisticUnit currentUnit: units    ) {
            if(currentUnit.getName().equals(unit.getName())) return true;
        }
        return false;
    }

    public ArrayList<LinguisticUnit> firstPlus(NonTerminal nonTerminal){
        ArrayList<LinguisticUnit> terminals = new ArrayList<>();
        Rule currentRule = new Rule();
        for ( Rule rule: this.rules) {
            if(rule.getNonTerminal().getName().equals(nonTerminal.getName())){
                currentRule = rule;
                break;
            }
        }

        for (Replacement replacement : currentRule.getReplacements() ) {
            LinguisticUnit unit = replacement.getUnits().get(0);
            if(unit instanceof Terminal){
                if(!this.hasUnit(terminals, unit)) {
                    terminals.add(unit);
                    this.terminalsFirstPlus.add(unit);
                }
            }
            else{

//                if(!this.hasUnit(terminals, unit) ) {
                if(!this.hasUnit(this.terminalsFirstPlus, unit) ) {
                    terminals.add(unit);
                    this.lastNonTerminal = (NonTerminal) unit;
                    this.terminalsFirstPlus.add(unit);
                    terminals.addAll(firstPlus((NonTerminal) unit));

                }
            }
        }

        return terminals;
    }
}
