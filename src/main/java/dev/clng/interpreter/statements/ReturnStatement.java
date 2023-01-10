package dev.clng.interpreter.statements;

import dev.clng.interpreter.FunctionRepository;

import java.util.Arrays;

/**
 * @author simon & ennio
 **/
public class ReturnStatement implements IStatement
{
    private final String functionName;
    private final String value;

    public ReturnStatement(String functionName, String value)
    {
        this.functionName = functionName;
        this.value = value;
    }

    @Override
    public void execute()
    {
        FunctionRepository.setReturnValue(functionName, value);
    }
}
