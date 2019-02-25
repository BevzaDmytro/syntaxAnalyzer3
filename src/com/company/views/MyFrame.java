package com.company.views;

import com.company.Controller;
import com.company.extensions.Lexem;
import com.company.extensions.PassOfSyntaxAnalyzer;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;


public class MyFrame {

    private boolean isFile;
    private String path;
    private String text;
    private JFrame frame;
    private JTabbedPane panel;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private JTextArea status;


    public MyFrame() {
        this.frame =  new JFrame("Lab 5");
//        this.frame.getContentPane().setLayout(new FlowLayout());
//        this.frame.getContentPane().setLayout(new FlowLayout());
        this.frame.setSize(800, 600);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        this.status = new JTextArea("Waiting for an input...");
        status.setEditable(false);
        status.setBackground(new Color(212, 212, 212));
        status.setFont(new Font("Dialog", Font.PLAIN, 14));


        this.panel = new JTabbedPane();
        this.panel.setPreferredSize(new Dimension(1420,800));
        this.panel1= new JPanel();
        this.panel2 = new JPanel();
        this.panel3 = new JPanel();
        this.panel4 = new JPanel();
        this.panel5 = new JPanel();
        panel4.setPreferredSize(new Dimension(1420, 800));
        panel5.setPreferredSize(new Dimension(1420, 800));

    }

    public void setStatus(String status) {
        this.status.setText(status);
        this.panel.repaint();
    }

    private void analyze(boolean isFile, String text) throws Exception {
        Controller controller = new Controller();
        controller.run(isFile, text);
//        MyFrame.this.show(controller.getParser().getLexemsTable().getLexems(), controller.getParser().getIdentificatorsTable().getLexems(), controller.getParser().getConstantsTable().getLexems());
        MyFrame.this.show(controller.getParser().getLexemsTable().getLexems(), controller.getParser().getIdentificatorsTable().getLexems(), controller.getParser().getConstantsTable().getLexems(),controller.getRelationsAnalyzer().getRelations(), controller.getSyntaxAnalyzerBottomUp().getAnalyzeLog());

        this.setStatus("Well done, everything work clear");

    }

    public void inputData(){
        JPanel input = new JPanel();
        input.setLayout(null);
        ButtonGroup group = new ButtonGroup();
        JRadioButton button1 = new JRadioButton("File", false);
        JRadioButton button2 = new JRadioButton("Input", false);
        button1.setBounds(5,5,10,10);
        button2.setBounds(5,20,10,10);
        group.add(button1);
        group.add(button2);

        JButton browse = new JButton("Browse!");
        browse.setEnabled(false);

        JTextArea in = new JTextArea("", 10, 40);
        in.setFont(new Font("Dialog", Font.PLAIN, 14));
        in.setTabSize(10);
        in.setLineWrap(true);
        in.setEnabled(false);
        JButton insertButton = new JButton("Parse it!");
        insertButton.setEnabled(false);


        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch ( ((JRadioButton)ae.getSource()).getText() ) {
                    case "File" :
                        insertButton.setEnabled(false);
                        in.setEnabled(false);
                        browse.setEnabled(true);

                        browse.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent event) {
                                File selectedFile = new File("src/programm.txt");
                                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getParentDirectory(new File("src/programm.txt")));
                                int returnValue = fileChooser.showOpenDialog(null);
                                if (returnValue == JFileChooser.APPROVE_OPTION) {
                                    selectedFile = fileChooser.getSelectedFile();
                                }

                                try {
                                    String s = "";
                                    Scanner i = new Scanner(new File(selectedFile.getPath()));
                                    while(i.hasNext())
                                        s += i.nextLine() + "\r\n";
                                    i.close();
                                    in.setText(s);
                                    in.setEditable(true);
                                    in.setEnabled(true);
                                    panel.repaint();
                                    MyFrame.this.analyze(true, selectedFile.getPath());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        break;
                    case "Input" :
                        insertButton.setEnabled(true);
                        in.setEnabled(true);
                        browse.setEnabled(false);

                        insertButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent event) {
                                String text = in.getText();
                                try {
                                    MyFrame.this.analyze(false, text);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;

                    default:
                        break;
                }
                panel.repaint();
            }
        };
        button1.addActionListener(listener);
        button2.addActionListener(listener);

//        input.add(in);
//        input.add(insertButton);
//        input.add(browse);
//        input.add(button1);
//        input.add(button2);

        in.setBounds(10,10,500,300);

        status.setBounds(530, 10, 150, 300);
        status.setPreferredSize(new Dimension(150, 300));
        status.setLineWrap(true);
        button1.setBounds(5,320,70,15);
        button2.setBounds(5,360,70,15);
        browse.setBounds(90,320,100,30);
        insertButton.setBounds(90,360,100,30);
        input.add(status);
        input.add(in);
        input.add(insertButton);
        input.add(browse);
        input.add(button1);
        input.add(button2);

        this.panel.addTab("Input", input);
        this.frame.add(this.panel);
        this.frame.setVisible(true);
    }

    public void show(ArrayList<Lexem> lexemsTable, ArrayList<Lexem> IDNTable, ArrayList<Lexem> CONTable, Map<String, Map<String, String>> relations, ArrayList<PassOfSyntaxAnalyzer> analyzeLog){


        JTable jTab1 = new JTable(new ShowLexemsTable(lexemsTable));
        JTable jTab2 = new JTable(new ShowIDNTable(IDNTable));
        JTable jTab3 = new JTable(new ShowCONTable(CONTable));
        JTable jTab4 = new JTable(new RelationsTableView(relations));
        JTable jTab5 = new JTable(new AnalyzerBottomUpView(analyzeLog));
        jTab4.getColumnModel().getColumn(1).setMaxWidth(20);
        jTab4.setPreferredScrollableViewportSize(new Dimension(1400, 700));
        jTab5.setPreferredScrollableViewportSize(new Dimension(800, 700));

        for (int i =10 ; i<jTab4.getColumnCount();i++) {
            jTab4.getColumnModel().getColumn(i).setPreferredWidth(20);
        }

        panel1.removeAll();
        panel2.removeAll();
        panel3.removeAll();
        panel4.removeAll();
        panel5.removeAll();

        panel1.add(jTab1);
        panel1.add(new JScrollPane(jTab1));
        panel2.add(jTab2);
        panel2.add(new JScrollPane(jTab2));
        panel3.add(jTab3);
        panel3.add(new JScrollPane(jTab3));

        panel4.add(jTab4);
        panel4.add(new JScrollPane(jTab4));
        panel4.setPreferredSize(new Dimension(1420, 800));

        panel5.add(jTab5);
        panel5.add(new JScrollPane(jTab5));
        panel4.setPreferredSize(new Dimension(800, 800));

        this.panel.addTab("Lexems", this.panel1);
        this.panel.addTab("Identificators", this.panel2);
        this.panel.addTab("Constants", this.panel3);
        this.panel.addTab("Relations", this.panel4);
        this.panel.addTab("Passes", this.panel5);
        this.frame.add(this.panel);
        this.frame.setVisible(true);

    }
}
