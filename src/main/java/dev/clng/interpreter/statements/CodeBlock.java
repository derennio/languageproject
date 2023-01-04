package dev.clng.interpreter.statements;

import java.util.List;

/**
 * @author simon & ennio
 **/
public class CodeBlock {
    private final List<IStatement> statements;
    private final boolean isMain;

    public CodeBlock(List<IStatement> statements, boolean isMain)
    {
        this.statements = statements;
        this.isMain = isMain;
    }

    public void execute()
    {
        for (IStatement statement : statements) {
            statement.execute();
        }
    }
}
