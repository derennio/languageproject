package dev.clng.interpreter.statements;

/**
 * @author ennio
 **/
public class IfStatement implements IStatement
{
    private String condition;
    private String codeBlock;

    public IfStatement(String condition, String codeBlock)
    {
        this.condition = condition;
        this.codeBlock = codeBlock;
    }

    @Override
    public void execute()
    {

    }
}
