package dev.clng.interpreter.expressions;

import dev.clng.token.LiteralTokenType;

import java.util.Arrays;

/**
 * @author simon & ennio
 **/
public class MultiplicationExpression implements IExpression
{
    private final String left;
    private final String right;

    public MultiplicationExpression(String left, String right)
    {
        this.left = left;
        this.right = right;

        if (!isLiteral(left) || !isLiteral(right)) {
            throw new RuntimeException("MultiplicationExpression only supports literals");
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
                .filter(lt -> left.matches(lt.getPattern()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("'%s' does not qualify as a literal".formatted(left)));

        LiteralTokenType rType = Arrays.stream(LiteralTokenType.values())
                .filter(lt -> right.matches(lt.getPattern()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("'%s' does not qualify as a literal".formatted(right)));

        if (lType != rType) {
            throw new RuntimeException("Cannot multiply literals of different types");
        }

        switch (lType) {
            case Integer -> {
                return Integer.parseInt(left) * Integer.parseInt(right);
            }
            case FloatingPoint -> {
                return Float.parseFloat(left) * Float.parseFloat(right);
            }
            case String -> {
                throw new RuntimeException("Cannot multiply strings");
            }
            case Char -> {
                throw new RuntimeException("Cannot multiply chars");
            }
            case Boolean -> {
                throw new RuntimeException("Cannot multiply booleans");
            }
            default -> throw new RuntimeException("Cannot multiply literals of type '%s'".formatted(lType));
        }
    }
}
