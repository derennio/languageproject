package dev.clng.interpreter.statements;

import dev.clng.interpreter.RuntimeContext;
import dev.clng.interpreter.TokenInterpreter;
import dev.clng.token.Token;
import dev.clng.token.TokenType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author simon & ennio
 **/
public class ForStatement implements IStatement
{
    private final String name;
    private final String range;
    private final String codeBlock;

    public ForStatement(String name, String range, String codeBlock)
    {
        this.name = name;
        this.range = range;
        this.codeBlock = codeBlock;
    }

    @Override
    public void execute()
    {
        IStatement codeBlockStatement = null;
        for (int i = 0; i < TokenType.values().length; i++) {
            TokenType tokenType = TokenType.values()[i];
            if (codeBlock.matches(tokenType.getPattern())) {
                Pattern pattern = Pattern.compile(tokenType.getPattern());
                Matcher result = pattern.matcher(codeBlock);
                Token token = new Token(tokenType, codeBlock, i, result);
                codeBlockStatement = new TokenInterpreter().convertTokenToStatement(token);
            }
        }

        int target = Integer.parseInt(range);
        for (int i = 0; i < target; i++) {
            assert codeBlockStatement != null;
            RuntimeContext.updateVariable(name, String.valueOf(i));
            codeBlockStatement.execute();
        }
    }
}
