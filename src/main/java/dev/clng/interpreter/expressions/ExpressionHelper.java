package dev.clng.interpreter.expressions;

import dev.clng.interpreter.RuntimeContext;
import dev.clng.token.ExpressionType;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author simon &ennio
 **/
public class ExpressionHelper
{
    public static IExpression generateExpression(String expr) {
        ExpressionType expressionType = Arrays.stream(ExpressionType.values())
                .filter(et -> expr.matches(et.getPattern()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("'%s' does not qualify as an expression".formatted(expr)));
        Matcher matcher = Pattern.compile(expressionType.getPattern()).matcher(expr);
        matcher.find();
        matcher.matches();
        switch (expressionType) {
            case Power -> {
                return new PowerExpression(determineLiteralValue(matcher.group("left")), determineLiteralValue(matcher.group("right")));
            }
            case Multiplication -> {
                return new MultiplicationExpression(determineLiteralValue(matcher.group("left")), determineLiteralValue(matcher.group("right")));
            }
            case Division -> {
                return new DivisionExpression(determineLiteralValue(matcher.group("left")), determineLiteralValue(matcher.group("right")));
            }
            case Addition -> {
                return new AdditionExpression(determineLiteralValue(matcher.group("left")), determineLiteralValue(matcher.group("right")));
            }
            case Subtraction -> {
                return new SubtractionExpression(determineLiteralValue(matcher.group("left")), determineLiteralValue(matcher.group("right")));
            }
            case Modulo -> {
                return new ModuloExpression(determineLiteralValue(matcher.group("left")), determineLiteralValue(matcher.group("right")));
            }
            case GreaterThan -> {
                return new GreaterThanExpression(determineLiteralValue(matcher.group("left")), determineLiteralValue(matcher.group("right")));
            }
            case GreaterThanOrEqual -> {
                return new GreaterThanOrEqualExpression(determineLiteralValue(matcher.group("left")), determineLiteralValue(matcher.group("right")));
            }
            case LessThan -> {
                return new LessThanExpression(determineLiteralValue(matcher.group("left")), determineLiteralValue(matcher.group("right")));
            }
            case LessThanOrEqual -> {
                return new LessThanOrEqualExpression(determineLiteralValue(matcher.group("left")), determineLiteralValue(matcher.group("right")));
            }
            case Equal -> {
                return new EqualExpression(determineLiteralValue(matcher.group("left")), determineLiteralValue(matcher.group("right")));
            }
            case NotEqual -> {
                return new NotEqualExpression(determineLiteralValue(matcher.group("left")), determineLiteralValue(matcher.group("right")));
            }
            case And -> {
                return new AndExpression(determineLiteralValue(matcher.group("left")), determineLiteralValue(matcher.group("right")));
            }
            case Or -> {
                return new OrExpression(determineLiteralValue(matcher.group("left")), determineLiteralValue(matcher.group("right")));
            }
            case FunctionCall -> {
                return new FunctionCallExpression(determineLiteralValue(matcher.group("name")), matcher.group("args").split(","));
            }
            case Variable -> {
                return new VariableExpression(determineLiteralValue(matcher.group("name")));
            }
            default -> throw new RuntimeException("'%s' does not qualify as an expression".formatted(expr));
        }
    }

    private static String determineLiteralValue(String expr) {
        if (expr.matches(ExpressionType.Variable.getPattern())) {
            return Objects.requireNonNull(RuntimeContext.retrieveVar(expr)).toString();
        }
        if (expr.matches(ExpressionType.FunctionCall.getPattern())) {
            Matcher matcher = Pattern.compile(ExpressionType.FunctionCall.getPattern()).matcher(expr);
            matcher.find();
            matcher.matches();
            return "0";
        }
        return expr;
    }
}
