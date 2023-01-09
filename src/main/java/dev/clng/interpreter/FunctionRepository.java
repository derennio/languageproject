package dev.clng.interpreter;

import dev.clng.common.Tuple;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author simon & ennio
 **/
public class FunctionRepository
{
    private static Map<String, Tuple<String[], Object>> functions = new HashMap<>();

    public static void setReturnValue(String name, String[] args, Object value)
    {
        functions.put(name, new Tuple<>(args, value));
    }

    public static Object call(String name, String[] args)
    {
        Tuple<String[], Object> function = functions.get(name);
        if (function == null) {
            throw new RuntimeException("Function '%s' does not exist".formatted(name));
        }

        String[] params = function.v1();
        if (params.length != args.length) {
            throw new RuntimeException("Function '%s' expects %d arguments, but %d were given".formatted(name, params.length, args.length));
        }

        for (int i = 0; i < params.length; i++) {
            if (!Objects.equals(params[i], args[i])) {
                throw new RuntimeException("Function '%s' expected argument '%s' at index %d, but '%s' was provided".formatted(name, params[i], i, args[i]));
            }
        }

        return function.v2();
    }
}
