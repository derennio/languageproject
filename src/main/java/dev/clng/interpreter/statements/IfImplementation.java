package dev.clng.interpreter.statements;

import dev.clng.interpreter.expressions.ExpressionHelper;
import dev.clng.interpreter.expressions.IExpression;

import java.util.List;

/**
 * @author simon & ennio
 **/
public record IfImplementation(String condition,
                               List<IStatement> successOps,
                               List<IStatement> failureOps)
{
    public void execute()
    {
        IExpression expression = ExpressionHelper.generateExpression(condition);
        boolean res = (boolean) expression.eval();
        if (res)
        {
            successOps.forEach(IStatement::execute);
        } else {
            failureOps.forEach(IStatement::execute);
        }
    }
}
