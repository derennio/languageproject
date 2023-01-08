package dev.clng.interpreter.expressions;

import dev.clng.interpreter.RuntimeContext;

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
        return RuntimeContext.retrieveVar(variableName);
    }
}
