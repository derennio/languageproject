package dev.clng;

import dev.clng.common.TextAreaOutputStream;
import dev.clng.gui.CodeEditor;
import dev.clng.interpreter.ProgramRepository;
import dev.clng.interpreter.TokenInterpreter;
import dev.clng.parser.LexicalParser;

import java.awt.*;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author ${USER}
 **/
public class Main
{
    /**
     * @param args cmd args
     */
    public static void main(String[] args) {
        var ce = new CodeEditor();
        setupOutput(ce);
    }

    /**
     * Set up the output stream to the TextAreaOutputStream
     * @param editorInstance
     */
    private static void setupOutput(CodeEditor editorInstance) {
        PrintStream ops = new PrintStream(new TextAreaOutputStream(editorInstance.getOutputArea()));
        System.setOut(ops);
        System.setErr(ops);

        System.out.println("Outputstream connected!");
    }
}