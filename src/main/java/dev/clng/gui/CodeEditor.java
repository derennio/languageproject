package dev.clng.gui;

import dev.clng.interpreter.ProgramRepository;
import dev.clng.interpreter.TokenInterpreter;
import dev.clng.parser.LexicalParser;
import dev.clng.token.Token;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;
import javax.swing.*;

public class CodeEditor extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;
    private final JTextArea textArea;
    private final JTextArea outputArea;
    private File currentFile;

    public CodeEditor() throws IOException, FontFormatException {
        super("Code Editor");
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));

        File initialFile = new File("src/main/resources/JetBrainsMono.ttf");
        InputStream targetStream = new FileInputStream(initialFile);

        textArea = new JTextArea();
        textArea.setFont(Font.createFont(Font.TRUETYPE_FONT, targetStream).deriveFont(14f));
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        outputArea = new JTextArea(10, 1);
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.SOUTH);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });
        fileMenu.add(openMenuItem);

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });
        fileMenu.add(saveMenuItem);

        JMenuItem saveAsMenuItem = new JMenuItem("Save As...");
        saveAsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFileAs();
            }
        });
        fileMenu.add(saveAsMenuItem);

        JMenu runMenu = new JMenu("Run");
        menuBar.add(runMenu);

        JMenuItem runMenuItem = new JMenuItem("Run");
        runMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runCode();
            }
        });
        runMenu.add(runMenuItem);

        fileMenu.addSeparator();

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(currentFile));
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                reader.close();
                textArea.setText(sb.toString());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error reading file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void saveFile() {
        if (currentFile == null) {
            saveFileAs();
        } else {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile));
                writer.write(textArea.getText());
                writer.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFileAs() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile));
                writer.write(textArea.getText());
                writer.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void runCode() {
        var code = textArea.getText();

        LexicalParser lexicalParser = new LexicalParser(code);
        List<Token> tokens = lexicalParser.parseLines();

        TokenInterpreter tokenInterpreter = new TokenInterpreter();
        tokenInterpreter.createStructure(tokens);

        new ProgramRepository().execute();
    }

    public JTextArea getOutputArea()
    {
        return outputArea;
    }
}