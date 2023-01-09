package dev.clng.interpreter.statements;

import dev.clng.interpreter.FunctionRepository;

import java.util.Arrays;

/**
 * @author simon & ennio
 **/
public class ReturnStatement implements IStatement
{
    private final String functionName;
    private final String[] provArgs;
    private final String value;

    public ReturnStatement(String functionName, String provArgs, String value)
    {
        this.functionName = functionName;
        this.provArgs = provArgs.split(",");
        this.value = value;
    }

    @Override
    public void execute()
    {
        Arrays.stream(provArgs).forEach(arg -> arg = arg.trim());
        FunctionRepository.setReturnValue(functionName, provArgs, value);
    }
}
