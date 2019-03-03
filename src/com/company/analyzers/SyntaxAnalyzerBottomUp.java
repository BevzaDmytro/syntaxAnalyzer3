package com.company.analyzers;

import com.company.extensions.*;
import com.company.views.MyFrame;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

public class SyntaxAnalyzerBottomUp {

    private Map<String, Map<String,String>> relations;
    private ArrayList<Lexem> lexems;
    private Stack<String> stack;
    private ArrayList<Rule> rules;
    private ArrayList<PassOfSyntaxAnalyzer> analyzeLog;
    private String currentRelation;
    private MyFrame frame;

    public SyntaxAnalyzerBottomUp(MyFrame frame) {
        this.frame = frame;
    }

    public ArrayList<Rule> getRules() {
        return rules;
    }

    public ArrayList<PassOfSyntaxAnalyzer> getAnalyzeLog() {
        return analyzeLog;
    }

    public String getCurrentRelation() {
        return currentRelation;
    }

    public void setRules(ArrayList<Rule> rules) {
        this.rules = rules;
        this.stack = new Stack<>();
        analyzeLog = new ArrayList<>();
    }

    public SyntaxAnalyzerBottomUp() {
        this.stack = new Stack<>();
        analyzeLog = new ArrayList<>();
    }

    public void analyze(){
//        int a = 1;
//        if(a ==1 )
//        return ;

         this.stack.push("#");
        int counter = 0;
        String previousItem = "#";
        String nextItem = this.lexems.get(counter).getName();
        this.lexems.add(new Lexem("#", "", -1));

        while (!checkIfTheEndOfProgram(nextItem)){

            if(!this.checkIfNextItemIsGreater(previousItem, nextItem)) {
                this.stack.push(nextItem);
            }
            else {
                ArrayList<String> possibleBasis = new ArrayList<>();
                previousItem = this.stack.pop();
                String nextInputItem = nextItem;
                this.analyzeLog.add(new PassOfSyntaxAnalyzer(this.stack.toString(), this.currentRelation, this.lexems.get(counter).getName()));
                while (!this.checkIfNextItemIsLess(previousItem, nextItem)){
                    possibleBasis.add(previousItem);
                    nextItem = previousItem;
                    previousItem = this.stack.pop();
                    this.analyzeLog.add(new PassOfSyntaxAnalyzer(this.stack.toString(), this.currentRelation, this.lexems.get(counter).getName()));
                }

                String replacement = this.findTheReplacement(possibleBasis);
                if(replacement.equals("")) this.frame.setStatus("Unexcpected operator on line " + this.lexems.get(counter-1).getLine());


                this.stack.push(previousItem);
                this.stack.push(replacement);
                this.analyzeLog.add(new PassOfSyntaxAnalyzer(this.stack.toString(), this.currentRelation, this.lexems.get(counter).getName()));
                previousItem = replacement;
                nextItem = nextInputItem;

                continue;
            }


            counter++;
            this.analyzeLog.add(new PassOfSyntaxAnalyzer(this.stack.toString(), this.currentRelation, this.lexems.get(counter).getName()));
            previousItem = nextItem;
            nextItem = this.lexems.get(counter).getName();
            if(this.lexems.get(counter).getCode() == 100) nextItem = "ід";
            if(this.lexems.get(counter).getCode() == 101) nextItem = "кфт";

        }

        System.out.println("Done");
        System.out.println("Done");
        System.out.println("Done");

    }

    private String findTheReplacement(ArrayList<String> possibleBasis) {
        String result = "";
        int size = possibleBasis.size();
        boolean flag = false;
        for (Rule rule : this.rules){
            for(Replacement replacement: rule.getReplacements()){
                if(replacement.getUnits().size() != size) continue;
                else {
                    for(int counter = 0; counter < possibleBasis.size(); counter++){
                        if(!possibleBasis.get(size - counter-1).equals(replacement.getUnits().get(counter).getName())) {
                            flag = false;
                            break;
                        }
                        flag = true;
                    }
                    if(flag) {
                        result = rule.getNonTerminal().getName();
                        break;
                    }
                }
            }
            if(flag) break;
        }


        return result;
    }

    private boolean checkIfNextItemIsLess(String lastItem, String nextItem) {
        if(lastItem.equals("#")) {
            currentRelation = "<";
            return true;
        }
        if(nextItem.equals("#")) {
            currentRelation = ">";
            return false;
        }
        currentRelation = this.relations.get(lastItem).get(nextItem);
        return this.relations.get(lastItem).get(nextItem).equals(" <");
    }

    private boolean checkIfTheEndOfProgram(String nextItem) {
        String item = this.stack.peek();
        if(item.equals("<програма>")){
            item = this.stack.pop();
            item = this.stack.pop();
            return item.equals("#");
        }
       return false;
    }

    private boolean checkIfNextItemIsGreater(String lastItem, String nextItem) {
        if(lastItem.equals("#")) {
            currentRelation = "<";
            return false;
        }
        if(nextItem.equals("#")) {
            currentRelation = ">";
            return true;
        }
        currentRelation = this.relations.get(lastItem).get(nextItem);
        return this.relations.get(lastItem).get(nextItem).equals(" >");
//        return true;
    }


    public Map<String, Map<String, String>> getRelations() {
        return relations;
    }

    public void setRelations(Map<String, Map<String, String>> relations) {
        this.relations = relations;
    }

    public ArrayList<Lexem> getLexems() {
        return lexems;
    }

    public void setLexems(ArrayList<Lexem> lexems) {
        this.lexems = lexems;
    }

    public Stack<String> getStack() {
        return stack;
    }

    public void setStack(Stack<String> stack) {
        this.stack = stack;
    }


}
