package dev.clng.interpreter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author simon & ennio
 **/
public class FunctionRepository
{
    private static Map<String, String> currentReturns = new HashMap<>();

    /**
     * Method to set a function's temporary return value.
     * @param name the name of the function
     * @param value the value
     */
    public static void setReturnValue(String name, String value)
    {
        currentReturns.put(name, value);
    }

    /**
     * Method to get a function's temporary return value.
     * @param name the name of the function
     * @return the value
     */
    public static String getReturnValue(String name)
    {
        return currentReturns.get(name);
    }
}
