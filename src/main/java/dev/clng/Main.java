package dev.clng;

import dev.clng.gui.CodeEditor;
import dev.clng.interpreter.ProgramRepository;
import dev.clng.interpreter.TokenInterpreter;
import dev.clng.parser.LexicalParser;

import java.awt.*;
import java.io.IOException;

/**
 * @author ${USER}
 **/
public class Main
{
    public static void main(String[] args) throws IOException, FontFormatException {
        LexicalParser parser = new LexicalParser("""
                def class Main:
                def int a = 5
                def boolean b
                def add(int a, int b):
                # Test case
                add(1, 2) <- 4
                add(a, b) <- a + b

                def main():
                print "test"
                print a
                print add(3, 4)

                !endclass
                def class Test:
                def test1():
                test1() <- 1

                def test2():
                test2() <- 2

                !endclass""");
        var result = parser.parseLines();
        new TokenInterpreter().createStructure(result);
        //new ProgramRepository().execute();
        new CodeEditor();
    }
}