package dev.clng.interpreter;

import dev.clng.common.Tuple;

import java.util.Map;

/**
 * @author simon & ennio
 **/
public class FunctionRepository
{
    private Map<String, Tuple<Object[], Object>> functions;

    public FunctionRepository(Map<String, Tuple<Object[], Object>> functions)
    {
        this.functions = functions;
    }

    public void setReturnValue(String name, Object[] args, Object value)
    {
        functions.put(name, new Tuple<>(args, value));
    }

    public Object call(String name, Object[] args)
    {
        Tuple<Object[], Object> function = functions.get(name);
        if (function == null) {
            throw new RuntimeException("Function '%s' does not exist".formatted(name));
        }

        Object[] params = function.v1();
        if (params.length != args.length) {
            throw new RuntimeException("Function '%s' expects %d arguments, but %d were given".formatted(name, params.length, args.length));
        }

        for (int i = 0; i < params.length; i++) {
            if (!params[i].equals(args[i].getClass())) {
                throw new RuntimeException("Function '%s' expects argument %d to be of type %s, but %s was given".formatted(name, i, params[i], args[i].getClass()));
            }
        }

        return function.v2();
    }
}
