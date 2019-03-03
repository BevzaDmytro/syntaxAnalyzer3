package com.company;

import com.company.analyzers.Parser;
import com.company.analyzers.*;
import com.company.views.MyFrame;

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



    public void run(boolean isFile, String text) throws Exception {
        this.parser.parse(isFile, text);

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

    }

    public RelationsAnalyzer getRelationsAnalyzer() {
        return relationsAnalyzer;
    }
}
