package dev.clng.interpreter.expressions;

/**
 * @author simon & ennio
 **/
public class FunctionCallExpression implements IExpression
{
    private final String functionName;
    private final String[] arguments;

    public FunctionCallExpression(String functionName, String[] arguments)
    {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    @Override
    public Object eval()
    {
        return null;
    }
}
