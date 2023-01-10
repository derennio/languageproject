package dev.clng.interpreter.statements;

import dev.clng.interpreter.TokenInterpreter;
import dev.clng.interpreter.expressions.ExpressionHelper;
import dev.clng.interpreter.expressions.IExpression;
import dev.clng.token.Token;
import dev.clng.token.TokenType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author simon & ennio
 **/
public class IfStatement implements IStatement
{
    public String condition;
    private final String codeBlock;

    public IfStatement(String condition, String codeBlock)
    {
        this.condition = condition;
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

        IExpression expression = ExpressionHelper.generateExpression(condition);
        boolean res = false;
        try {
            res = (boolean) expression.eval();
        } catch (Exception e) {
            res = Boolean.parseBoolean(expression.eval().toString());
        }
        if (res)
        {
            assert codeBlockStatement != null;
            codeBlockStatement.execute();
        }
    }
}
