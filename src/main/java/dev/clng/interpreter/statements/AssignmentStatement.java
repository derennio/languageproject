package dev.clng.interpreter.statements;

/**
 * @author ennio
 **/
public class AssignmentStatement implements IStatement
{
    private String variableName;
    private String value;

    public AssignmentStatement(String variableName, String value)
    {
        this.variableName = variableName;
        this.value = value;
    }

    @Override
    public void execute()
    {

    }
}