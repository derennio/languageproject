package dev.clng.interpreter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author simon & ennio
 **/
public class FunctionRepository
{
    private static Map<String, String> currentReturns = new HashMap<>();

    public static void setReturnValue(String name, String value)
    {
        currentReturns.put(name, value);
    }

    public static String getReturnValue(String name)
    {
        return currentReturns.get(name);
    }
}
