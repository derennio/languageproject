package dev.clng.token;

import java.util.regex.Matcher;

/**
 * @author ennio
 **/
public class Token
{
    private TokenType type;
    private String value;
    private int line;
    private Matcher matcher;

    public Token(TokenType type, String value, int line, Matcher matcher)
    {
        this.type = type;
        this.value = value;
        this.line = line;
        this.matcher = matcher;
    }

    public TokenType getType()
    {
        return type;
    }

    public String getValue()
    {
        return value;
    }

    public int getLine()
    {
        return line;
    }

    public Matcher getMatcher()
    {
        return matcher;
    }

    @Override
    public String toString()
    {
        return "Token{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
