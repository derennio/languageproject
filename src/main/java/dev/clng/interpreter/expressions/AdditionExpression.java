package dev.clng.interpreter.expressions;

import dev.clng.token.LiteralTokenType;

import java.util.Arrays;

/**
 * @author simon & ennio
 **/
public class AdditionExpression implements IExpression
{
    private final String left;
    private final String right;

    public AdditionExpression(String left, String right)
    {
        this.left = left;
        this.right = right;

        if (!isLiteral(left) || !isLiteral(right)) {
            throw new RuntimeException("AdditionExpression only supports literals");
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
            throw new RuntimeException("Cannot add literals of different types");
        }

        switch (lType) {
            case Integer -> {
                return Integer.parseInt(left) + Integer.parseInt(right);
            }
            case FloatingPoint -> {
                return Float.parseFloat(left) + Float.parseFloat(right);
            }
            case String -> {
                return stripString(left) + stripString(right);
            }
            case Char -> {
                return stripString(left).charAt(0) + stripString(right).charAt(0);
            }
            case Boolean -> {
                throw new RuntimeException("Cannot add booleans");
            }
            default -> throw new RuntimeException("Cannot add literals of type '%s'".formatted(lType));
        }
    }

    private String stripString(String value) {
        return value.substring(1, value.length() - 1);
    }
}
