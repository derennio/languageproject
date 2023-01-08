package dev.clng.interpreter.expressions;

import dev.clng.token.LiteralTokenType;

import java.util.Arrays;

/**
 * @author simon & ennio
 **/
public class OrExpression implements IExpression
{
    private final String left;
    private final String right;

    public OrExpression(String left, String right)
    {
        this.left = left;
        this.right = right;

        if (!isLiteral(left) || !isLiteral(right)) {
            throw new RuntimeException("OrExpression only supports literals");
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
            throw new RuntimeException("Cannot compare literals of different types");
        }

        switch (lType) {
            case Integer -> {
                throw new RuntimeException("Cannot compare integers");
            }
            case FloatingPoint -> {
                throw new RuntimeException("Cannot compare floating point numbers");
            }
            case String -> {
                throw new RuntimeException("Cannot compare strings");
            }
            case Char -> {
                throw new RuntimeException("Cannot compare chars");
            }
            case Boolean -> {
                return Boolean.parseBoolean(left) || Boolean.parseBoolean(right);
            }
            default -> throw new RuntimeException("Cannot compare literals of type '%s'".formatted(lType));
        }
    }
}
