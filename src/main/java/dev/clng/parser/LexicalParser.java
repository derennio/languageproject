package dev.clng.parser;

import dev.clng.token.Token;
import dev.clng.token.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author simon & ennio
 **/
public class LexicalParser
{
    private final String[] input;

    public LexicalParser(String input)
    {
        this.input = input.split("\n");
    }

    public List<Token> parseLines() {
        List<Token> tokens = new ArrayList<>();
        for (int i = 0; i < input.length; i++) {
            String line = input[i];
            for (int j = 0; j < TokenType.values().length; j++) {
                TokenType tokenType = TokenType.values()[j];
                if (line.matches(tokenType.getPattern())) {
                    Pattern pattern = Pattern.compile(tokenType.getPattern());
                    Matcher result = pattern.matcher(line);
                    Token token = new Token(tokenType, line, i, result);
                    tokens.add(token);
                    System.out.println("Found token: " + tokenType);
                }
            }
        }

        return tokens;
    }
}
