package dev.clng.interpreter.statements;

import java.util.function.Consumer;

/**
 * @author ennio
 **/
public class ReturnStatement implements IStatement
{
    private String functionName;
    private String provArgs;
    private String value;

    public ReturnStatement(String functionName, String provArgs, String value)
    {
        this.functionName = functionName;
        this.provArgs = provArgs;
        this.value = value;
    }

    @Override
    public void execute()
    {

    }
}
