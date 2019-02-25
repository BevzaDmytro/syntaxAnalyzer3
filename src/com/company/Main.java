package com.company;


import com.company.views.MyFrame;

public class Main {

    public static void main(String[] args) {


//        Parser parser = new Parser();
//        parser.parse(true, "programm.txt");

        MyFrame frame = new MyFrame();
//        frame.show(parser.getLexemsTable().getLexems(),parser.getIdentificatorsTable().getLexems(), parser.getConstantsTable().getLexems());
        frame.inputData();




//        com.company.analyzers.GrammarParser grammarParser = new com.company.analyzers.GrammarParser();
//        try {
//            grammarParser.parseGrammar();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        com.company.analyzers.RelationsAnalyzer analyzer = new com.company.analyzers.RelationsAnalyzer(grammarParser.getRules(), grammarParser.getRelations());
//        analyzer.analyze();
//
//        MyFrame frame = new MyFrame();
//        frame.show(analyzer.getRelations());
//        System.out.println("Hello World!");
    }
}
