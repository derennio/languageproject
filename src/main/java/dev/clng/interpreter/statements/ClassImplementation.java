package dev.clng.interpreter.statements;

import java.util.List;

public record ClassImplementation(String name,
                                  List<FunctionImplementation> functionImplementations,
                                  List<DeclarationImplementation> declarationImplementations) {
}
