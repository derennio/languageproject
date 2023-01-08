package dev.clng.interpreter.statements;

import dev.clng.interpreter.expressions.ExpressionHelper;
import dev.clng.interpreter.expressions.IExpression;
import dev.clng.token.LiteralTokenType;

import java.util.Arrays;

/**
 * @author simon & ennio
 **/
public class PrintStatement implements IStatement
{
    private final String value;

    public PrintStatement(String value)
    {
        this.value = value;
    }

    @Override
    public void execute()
    {
        if (isLiteral(value))
        {
            System.out.println(retrieveValue(value));
        } else {
            IExpression expression = ExpressionHelper.generateExpression(value);
            System.out.println(expression.eval());
        }
    }

    private boolean isLiteral(String value)
    {
        return Arrays.stream(LiteralTokenType.values())
                .anyMatch(lt -> value.matches(lt.getPattern()));
    }

    private String retrieveValue(String raw) {
        if (raw.matches(LiteralTokenType.String.getPattern()) || raw.matches(LiteralTokenType.Char.getPattern())) {
            return raw.substring(1, raw.length() - 1);
        }

        return raw;
    }
}