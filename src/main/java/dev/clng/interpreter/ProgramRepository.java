package dev.clng.interpreter;

import dev.clng.interpreter.statements.*;
import dev.clng.token.DataTokenType;

import java.util.Arrays;

public class ProgramRepository {
    private static Program program;

    public static void setProgram(Program program) {
        ProgramRepository.program = program;
    }

    public void execute() {
        // Find declarations and create variables
        for (ClassImplementation classImplementation : program.content()) {
            for (DeclarationImplementation declarationImplementation : classImplementation.declarationImplementations()) {
                DataTokenType type = Arrays.stream(DataTokenType.values())
                        .filter(dt -> dt.getPattern().equals(declarationImplementation.type()))
                        .findFirst()
                        .orElse(null);
                RuntimeContext.addVariable(declarationImplementation.name(), type, declarationImplementation.value());
            }
        }
        var x = RuntimeContext.getMemory();
        int v = RuntimeContext.getVariable("a");
        System.out.println(v);

        // Find entry point and execute
        FunctionImplementation main = findEntryPoint();
        for (IStatement statement : main.statements()) {
            statement.execute();
        }
    }

    private FunctionImplementation findEntryPoint() {
        return program.content().stream()
                .flatMap(c -> c.functionImplementations().stream())
                .filter(f -> f.name().equalsIgnoreCase("main"))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No main method found"));
    }

    private FunctionImplementation findMethod(ClassImplementation classImpl, String name) {
        return classImpl.functionImplementations().stream()
                .filter(f -> f.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No such method"));
    }
}
