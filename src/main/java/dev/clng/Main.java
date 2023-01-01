package dev.clng;

import dev.clng.parser.LexicalParser;

/**
 * @author ${USER}
 **/
public class Main
{
    public static void main(String[] args)
    {
        LexicalParser parser = new LexicalParser("def add(int a, int b):\n" +
                "# Test case\n" +
                "add(1, 2) <- 4\n" +
                "add(a, b) <- a + b\n" +
                "\n" +
                "def main():\n" +
                "print add(3, 4)");
        parser.parseLines();
    }
}