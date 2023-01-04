package dev.clng.interpreter.statements;

import java.util.List;

public record FunctionImplementation(String name,
                                     String args,
                                     List<IStatement> statements) {
}
