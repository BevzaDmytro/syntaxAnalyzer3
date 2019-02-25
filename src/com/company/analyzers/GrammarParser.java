package com.company.analyzers;

import com.company.extensions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class GrammarParser {
    private ArrayList<Rule> rules = new ArrayList<>();
    private Map<String, Map<String,String>> relations = new LinkedHashMap<>();

    public ArrayList<Rule> getRules() {
        return rules;
    }

    public Map<String, Map<String, String>> getRelations() {
        return relations;
    }

    public Map<String, String> getMap() {
        return map;
    }

    private Map<String, String> map =  new LinkedHashMap<>();


    public void parseGrammar() throws Exception {
        try {
            File file = new File("src/grammar.txt");
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            String line;
            while ((line = reader.readLine()) != null){
//                line = reader.readLine();
                this.parseLine(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.createTable();

        System.out.println("Grammar parsed!");
    }

    private void createTable() {
//        Map<String, String> map =  new HashMap<>();
        for (Rule rule: this.rules   ) {
            String name = rule.getNonTerminal().getName();
            if(!this.map.containsKey(name)){
                this.map.put(name, " ");
            }
        }

        for (Rule rule: this.rules   ) {
            ArrayList<Replacement> array = rule.getReplacements();
            for (Replacement replacement : array   ) {
                ArrayList<LinguisticUnit> units = replacement.getUnits();
                for (LinguisticUnit unit: units) {
                    String name = unit.getName();
                    if(!this.map.containsKey(name)){
                        this.map.put(name, " ");
                    }
                }
            }
        }

        for (Map.Entry<String, String> entry : this.map.entrySet()) {

            Map<String,String> currentMap = new LinkedHashMap<>(this.map);
//            this.relations.put(entry.getKey(),this.map);
            this.relations.put(entry.getKey(),currentMap);
        }


    }

    private void parseLine(String line) throws Exception {
        Rule rule = new Rule();
        ArrayList<Replacement> replacementsRules = new ArrayList<>();
        String [] partsOfRule = line.split(" ::= ");
            if(Pattern.matches("^<.+>$",partsOfRule[0])){
                rule.setNonTerminal(new NonTerminal(partsOfRule[0]));
//                if(!this.map.containsKey(partsOfRule[0])){
//                    this.map.put(partsOfRule[0], " ");
//                }
            }
            else throw new Exception("Left part of rule is wrong");

            String[] replacements = partsOfRule[1].split(" \\| ");
                for ( String replacement : replacements  ) {
                    Replacement currentReplacement = new Replacement();
                    String[] elements = replacement.split(" ");

                    for (String element: elements  ) {
                        if(Pattern.matches("^<.+>$",element)){
                            currentReplacement.addUnit(new NonTerminal(element));
                        }
                        else {
                            currentReplacement.addUnit(new Terminal(element));
                        }
//                        if(!this.map.containsKey(element)){
//                            this.map.put(element, " ");
//                        }
                    }
                    replacementsRules.add(currentReplacement);
                }
        rule.setReplacements(replacementsRules);
        this.rules.add(rule);
    }
}
