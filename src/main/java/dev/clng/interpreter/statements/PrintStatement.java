package dev.clng.interpreter.statements;

/**
 * @author simon & ennio
 **/
public class PrintStatement implements IStatement
{
    private String value;

    public PrintStatement(String value)
    {
        this.value = value;
    }

    @Override
    public void execute()
    {

    }
}