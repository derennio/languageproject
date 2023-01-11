package dev.clng.interpreter.statements;

import dev.clng.interpreter.FunctionRepository;
import dev.clng.interpreter.RuntimeContext;
import dev.clng.interpreter.expressions.ExpressionHelper;
import dev.clng.interpreter.expressions.IExpression;
import dev.clng.token.DataTokenType;
import dev.clng.token.LiteralTokenType;

import java.util.Arrays;
import java.util.List;

public record FunctionImplementation(String name,
                                     String[] args,
                                     List<IStatement> statements) {
    public Object call(String[] provArgs) {
        if (args.length != provArgs.length) {
            throw new RuntimeException("Function '%s' expects %d arguments, but %d were given".formatted(name, args.length, provArgs.length));
        }

        for (int i = 0; i < args.length; i++) {
            String arg = args[i].trim();
            if (arg.equals(""))
                continue;
            var argType = Arrays.stream(DataTokenType.values())
                    .filter(dtt -> arg.matches(dtt.getPattern() + " .*"))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Invalid argument type '%s'".formatted(arg)));

            var argName = arg.split(" ")[1];
            var provided = provArgs[i].trim();
            var providedType = Arrays.stream(LiteralTokenType.values())
                    .filter(ltt -> provided.matches(ltt.getPattern()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Invalid argument type '%s'".formatted(provided)));

            if (!argType.name().equals(providedType.name())) {
                throw new RuntimeException("Function '%s' expected argument '%s' at index %d, but '%s' was provided".formatted(name, argType, i, providedType));
            }

            RuntimeContext.addLocalVariable(argName, argType, provided);
        }

        for (IStatement statement : statements) {
            statement.execute();
        }

        var value = FunctionRepository.getReturnValue(name);

        if (value == null) {
            for (String arg : args)
            {
                if (arg.equals(""))
                    continue;
                var argName = arg.split(" ")[1];
                RuntimeContext.clearLocal(argName);
            }
            return null;
        }

        if (isLiteral(value)) {
            for (String arg : args)
            {
                if (arg.equals(""))
                    continue;
                var argName = arg.split(" ")[1];
                RuntimeContext.clearLocal(argName);
            }

            return value;
        } else {
            IExpression expression = ExpressionHelper.generateExpression(value);
            var rV = expression.eval().toString();

            for (String arg : args)
            {
                if (arg.equals(""))
                    continue;
                var argName = arg.split(" ")[1];
                RuntimeContext.clearLocal(argName);
            }

            return rV;
        }
    }

    private boolean isLiteral(String value)
    {
        return Arrays.stream(LiteralTokenType.values())
                .anyMatch(lt -> value.matches(lt.getPattern()));
    }
}
