package dev.clng.interpreter;

import dev.clng.interpreter.statements.*;
import dev.clng.token.Token;
import dev.clng.token.TokenType;

import java.util.List;

/**
 * @author ennio
 **/
public class TokenInterpreter
{
    private IStatement convertTokenToStatement(Token token)
    {
        switch (token.getType()) {
            case ReturnDef:
                return new ReturnStatement(token.getMatcher().group("name"), token.getMatcher().group("args"), token.getMatcher().group("value"));
            case Assignment:
                return new AssignmentStatement(token.getMatcher().group("name"), token.getMatcher().group("value"));
            case IfStatement:
                return new IfStatement(token.getMatcher().group("condition"), token.getMatcher().group("code"));
            case PrintStatement:
                return new PrintStatement(token.getMatcher().group("value"));
            default:
                return null;
        }
    }

    public void interpret(List<Token> tokens)
    {
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            if (token.getType() == TokenType.FuncDef) {
                System.out.println("Found function definition");
                for (int j = i + 1; j < tokens.size(); j++) {
                    Token nextToken = tokens.get(j);
                    if (nextToken.getType() == TokenType.EmptyLine) {
                        System.out.println("Found code block terminator");
                        List<Token> funcTokens = tokens.subList(i, j);
                        // call convertTokenToStatement for each token in funcTokens and add to new list
                        // then create new CodeBlock with that list
                        funcTokens.forEach(this::convertTokenToStatement);
                        CodeBlock codeBlock = new CodeBlock(funcTokens);
                    }
                }
            }
        }
    }
}
