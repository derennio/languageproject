package dev.clng.interpreter;

import dev.clng.common.Tuple;
import dev.clng.token.DataTokenType;

import java.util.HashMap;
import java.util.Map;

public class RuntimeContext {
    private static Map<String, Tuple<DataTokenType, String>> memory = new HashMap<>();

    public static void addVariable(String name, DataTokenType type, String value) {
        memory.put(name, new Tuple<>(type, value));
    }

    public static void updateVariable(String name, String value) {
        memory.put(name, new Tuple<>(memory.get(name).v1(), value));
    }

    public static Map<String, Tuple<DataTokenType, String>> getMemory() {
        return memory;
    }

    public static Object retrieveVar(String name) {
        var val = memory.get(name);

        if (val == null) {
            throw new IllegalStateException(String.format("Variable '%s' not found", name));
        }

        switch (val.v1()) {
            case Integer -> {
                return Integer.valueOf(val.v2());
            }
            case FloatingPoint -> {
                return Float.valueOf(val.v2());
            }
            case Char -> {
                return val.v2().charAt(0);
            }
            case Boolean -> {
                return Boolean.valueOf(val.v2());
            }
            case String -> {
                return val.v2();
            }
            default -> {
                return null;
            }
        }
    }

    public static <X> X getVariable(String name) {
        return (X) retrieveVar(name);
    }
}
