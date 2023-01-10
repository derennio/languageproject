package dev.clng.interpreter;

import dev.clng.common.Tuple;
import dev.clng.token.DataTokenType;
import dev.clng.token.LiteralTokenType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RuntimeContext {
    private static Map<String, Tuple<DataTokenType, String>> memory = new HashMap<>();
    private static Map<String, Tuple<DataTokenType, String>> locals = new HashMap<>();

    public static void addVariable(String name, DataTokenType type, String value) {
        memory.put(name, new Tuple<>(type, value));
    }

    public static void addLocalVariable(String name, DataTokenType type, String value) {
        locals.put(name, new Tuple<>(type, value));
    }

    public static void updateVariable(String name, String value) {
        if (locals.containsKey(name)) {
            locals.put(name, new Tuple<>(locals.get(name).v1(), value));
        } else if (memory.containsKey(name)) {
            memory.put(name, new Tuple<>(memory.get(name).v1(), value));
        } else {
            var providedType = Arrays.stream(LiteralTokenType.values())
                    .filter(ltt -> value.matches(ltt.getPattern()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Invalid argument type '%s'".formatted(value)));

            locals.put(name, new Tuple<>(convert(providedType), value));
        }
    }

    private static DataTokenType convert(LiteralTokenType ltt) {
        return Arrays.stream(DataTokenType.values())
                .filter(dtt -> dtt.name().equals(ltt.name()))
                .findFirst()
                .orElseThrow();
    }

    public static void clearLocal(String name) {
        locals.remove(name);
    }

    public static Object retrieveVar(String name) {
        Tuple<DataTokenType, String> val = locals.get(name);

        if (val == null) {
            val = memory.get(name);
        }

        if (val == null) {
            throw new IllegalStateException("Variable '%s' not found".formatted(name));
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
}
