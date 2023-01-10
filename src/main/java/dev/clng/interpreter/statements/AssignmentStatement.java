package dev.clng.interpreter.statements;

import dev.clng.interpreter.RuntimeContext;
import dev.clng.interpreter.expressions.ExpressionHelper;
import dev.clng.interpreter.expressions.IExpression;
import dev.clng.token.LiteralTokenType;

import java.util.Arrays;

/**
 * @author simon & ennio
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
        if (isLiteral(value))
        {
            RuntimeContext.updateVariable(variableName, retrieveValue(value));
        } else {
            IExpression expression = ExpressionHelper.generateExpression(value);
            var result = expression.eval();
            RuntimeContext.updateVariable(variableName, result.toString());
        }
    }

    private boolean isLiteral(String value)
    {
        return Arrays.stream(LiteralTokenType.values())
                .anyMatch(lt -> value.matches(lt.getPattern()));
    }

    private String retrieveValue(String raw) {
        if (raw.matches(LiteralTokenType.String.getPattern()) || raw.matches(LiteralTokenType.Char.getPattern())) {
            return raw.substring(1, raw.length() - 1);
        }

        return raw;
    }
}