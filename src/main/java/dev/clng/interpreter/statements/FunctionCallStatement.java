package dev.clng.interpreter.statements;

import dev.clng.interpreter.ProgramRepository;

/**
 * @author simon & ennio
 **/
public class FunctionCallStatement implements IStatement
{
    private final String className;
    private final String name;
    private final String[] args;

    public FunctionCallStatement(String className, String name, String[] args)
    {
        this.name = name;
        this.args = args;
        this.className = className;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public void execute()
    {
        var classObj = ProgramRepository.findClass(className);
        var function = ProgramRepository.findMethod(classObj, name);
        function.call(args);
    }
}
