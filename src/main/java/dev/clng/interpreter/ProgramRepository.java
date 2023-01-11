package dev.clng.interpreter;

import dev.clng.interpreter.statements.*;
import dev.clng.token.DataTokenType;

import java.util.Arrays;

public class ProgramRepository {
    private static Program program;

    /**
     * Method to set the program to be executed.
     *
     * @param program The tokens to be executed.
     */
    public static void setProgram(Program program) {
        ProgramRepository.program = program;
    }

    /**
     * Method to start executing the program.
     */
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

    /**
     * Method to find any method, given a class and name
     *
     * @param classImpl The class
     * @param name The name of the method
     */
    public static FunctionImplementation findMethod(ClassImplementation classImpl, String name) {
        return classImpl.functionImplementations().stream()
                .filter(f -> f.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No such method"));
    }

    /**
     * Method to find any method, given a name
     *
     * @param name The name of the method
     */
    public static FunctionImplementation findMethod(String name) {
        return program.content().stream()
                .flatMap(c -> c.functionImplementations().stream())
                .filter(f -> f.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No such method"));
    }

    /**
     * Method to find any class, given a name
     *
     * @param name The name of the class
     */
    public static ClassImplementation findClass(String name) {
        return program.content().stream()
                .filter(c -> c.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No such class"));
    }
}
