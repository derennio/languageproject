package dev.clng.interpreter.expressions;

import dev.clng.token.LiteralTokenType;

import java.util.Arrays;

/**
 * @author simon & ennio
 **/
public class NotExpression implements IExpression
{
    private final String condition;

    public NotExpression(String condition)
    {
        this.condition = condition;

        if (!isLiteral(condition)) {
            throw new RuntimeException("NotExpression only supports literals");
        }
    }

    @Override
    public Object eval()
    {
        return determineValue();
    }

    private boolean isLiteral(String value)
    {
        return Arrays.stream(LiteralTokenType.values())
                .anyMatch(lt -> value.matches(lt.getPattern()));
    }

    private Object determineValue() {
        LiteralTokenType lType = Arrays.stream(LiteralTokenType.values())
                .filter(lt -> condition.matches(lt.getPattern()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("'%s' does not qualify as a literal".formatted(condition)));

        switch (lType) {
            case Integer -> {
                throw new RuntimeException("Cannot negate integer");
            }
            case FloatingPoint -> {
                throw new RuntimeException("Cannot negate floating point");
            }
            case String -> {
                throw new RuntimeException("Cannot negate string");
            }
            case Char -> {
                throw new RuntimeException("Cannot negate char");
            }
            case Boolean -> {
                return !Boolean.parseBoolean(condition);
            }
            default -> throw new RuntimeException("Cannot compare literals of type '%s'".formatted(lType));
        }
    }
}
