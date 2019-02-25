package com.company.extensions;

public class PassOfSyntaxAnalyzer {
    private String stackView;
    private String currentRelation;
    private String input;


    public PassOfSyntaxAnalyzer(String stackView, String currentRelation, String input) {
        this.stackView = stackView;
        this.currentRelation = currentRelation;
        this.input = input;
    }

    public String getStackView() {
        return stackView;
    }

    public String getCurrentRelation() {
        return currentRelation;
    }

    public String getInput() {
        return input;
    }
}
