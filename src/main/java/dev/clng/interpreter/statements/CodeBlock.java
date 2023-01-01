package dev.clng.interpreter.statements;

/**
 * @author ennio
 **/
public class CodeBlock
{
    private final IStatement[] statements;

    public CodeBlock(IStatement[] statements)
    {
        this.statements = statements;
    }

    public void execute()
    {
        for (IStatement statement : statements) {
            statement.execute();
        }
    }
}
