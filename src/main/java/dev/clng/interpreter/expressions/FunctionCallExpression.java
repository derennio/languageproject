package dev.clng.interpreter.expressions;

import dev.clng.interpreter.ProgramRepository;

/**
 * @author simon & ennio
 **/
public class FunctionCallExpression implements IExpression
{
    private final String className;
    private final String functionName;
    private final String[] arguments;

    public FunctionCallExpression(String className, String functionName, String[] arguments)
    {
        this.className = className;
        this.functionName = functionName;
        this.arguments = arguments;
    }

    @Override
    public Object eval()
    {
        if (className == null) {
            return ProgramRepository.findMethod(functionName).call(arguments);
        } else {
            var classObj = ProgramRepository.findClass(className);
            return ProgramRepository.findMethod(classObj, functionName).call(arguments);
        }
    }
}
