package com.company;

import com.company.analyzers.Parser;
import com.company.analyzers.*;
import com.company.views.MyFrame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;

public class Controller {
    private Parser parser;
    private GrammarParser grammarParser;
    private RelationsAnalyzer relationsAnalyzer;
    private SyntaxAnalyzerBottomUp syntaxAnalyzerBottomUp;
    private MyFrame frame;

    public SyntaxAnalyzerBottomUp getSyntaxAnalyzerBottomUp() {
        return syntaxAnalyzerBottomUp;
    }

    public Controller() {
        this.parser = new Parser();
        this.grammarParser =  new GrammarParser();
        this.syntaxAnalyzerBottomUp = new SyntaxAnalyzerBottomUp();
        this.frame = new MyFrame(this);

    }

    public Controller(MyFrame frame) {
        this.parser = new Parser();
        this.grammarParser =  new GrammarParser();
        this.frame = frame;
        this.syntaxAnalyzerBottomUp = new SyntaxAnalyzerBottomUp(frame);
    }

    public Parser getParser() {
        return parser;
    }




    public void execute(){

        frame.input();
    }

    public void analyzeFile(String path){
        try {
            this.parser.parse(new BufferedReader(new FileReader(new File(path))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.analyzeSyntax();
    }
    public void analyzeText(String text){
        try {
            this.parser.parse(new BufferedReader(new StringReader(text)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.analyzeSyntax();
    }

    private void analyzeSyntax(){
        try {
            this.grammarParser.parseGrammar();
        } catch (Exception e) {
            e.printStackTrace();
        }

        relationsAnalyzer = new RelationsAnalyzer(grammarParser.getRules(), grammarParser.getRelations());
        relationsAnalyzer.analyze();

        this.syntaxAnalyzerBottomUp.setLexems(this.parser.getLexemsTable().getLexems());
        this.syntaxAnalyzerBottomUp.setRelations(this.relationsAnalyzer.getRelations());
        this.syntaxAnalyzerBottomUp.setRules(this.relationsAnalyzer.getRules());
        this.syntaxAnalyzerBottomUp.analyze();
        frame.show(this.getParser().getLexemsTable().getLexems(), this.getParser().getIdentificatorsTable().getLexems(), this.getParser().getConstantsTable().getLexems(),this.getRelationsAnalyzer().getRelations(), this.getSyntaxAnalyzerBottomUp().getAnalyzeLog());
    }

    public RelationsAnalyzer getRelationsAnalyzer() {
        return relationsAnalyzer;
    }
}
