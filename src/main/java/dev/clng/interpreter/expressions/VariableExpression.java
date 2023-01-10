package dev.clng.interpreter.expressions;

import dev.clng.interpreter.RuntimeContext;
import dev.clng.token.LiteralTokenType;

import java.util.Arrays;

/**
 * @author simon & ennio
 **/
public class VariableExpression implements IExpression
{
    private final String variableName;

    public VariableExpression(String variableName)
    {
        this.variableName = variableName;
    }

    @Override
    public Object eval()
    {
        if (isLiteral(variableName))
        {
            return variableName;
        } else {
            return RuntimeContext.retrieveVar(variableName);
        }
    }

    private boolean isLiteral(String value)
    {
        return Arrays.stream(LiteralTokenType.values())
                .anyMatch(lt -> value.matches(lt.getPattern()));
    }
}
