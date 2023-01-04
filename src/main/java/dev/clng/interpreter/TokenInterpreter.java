package dev.clng.interpreter;

import dev.clng.common.Tuple;
import dev.clng.interpreter.statements.*;
import dev.clng.token.Token;
import dev.clng.token.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * @author simon & ennio
 **/
public class TokenInterpreter
{
    private IStatement convertTokenToStatement(Token token)
    {
        // check if either t1 or t2 are false
        var t1 = token.matcher().find();
        var t2 = token.matcher().matches();

        if (!t1 || !t2) {
            System.out.println(token.value());
            throw new IllegalArgumentException(String.format("Syntax error in line %d: Syntax does not match type %s", token.line() + 1, token.type()));
        }

        return switch (token.type()) {
            case ReturnDef -> new ReturnStatement(token.matcher().group("name"), token.matcher().group("args"), token.matcher().group("value"));
            case Assignment -> new AssignmentStatement(token.matcher().group("name"), token.matcher().group("value"));
            case IfStatement -> new IfStatement(token.matcher().group("condition"), token.matcher().group("code"));
            case PrintStatement -> new PrintStatement(token.matcher().group("value"));
            case CommentLine -> new CommentStatement();
            default -> null;
        };
    }

    public void createStructure(List<Token> tokens) {
        List<ClassImplementation> program = new ArrayList<>();
        var classes = collectClasses(tokens);
        for (Tuple<String, List<Token>> c : classes) {
            List<FunctionImplementation> functionImplementations = new ArrayList<>();

            var functions = collectFunctions(c.v2());
            for (List<Token> function : functions) {
                Matcher fMatcher = function.get(0).matcher();
                fMatcher.find();
                fMatcher.matches();
                var name = fMatcher.group("name");
                var args = fMatcher.group("args");
                List<IStatement> fStatements = new ArrayList<>();
                for (Token token : function.subList(1, function.size() - 1)) {
                    IStatement statement = convertTokenToStatement(token);
                    fStatements.add(statement);
                }
                var impl = new FunctionImplementation(name, args, fStatements);
                functionImplementations.add(impl);
            }

            var declarations = collectDeclarations(c.v2());
            List<DeclarationImplementation> variableDeclarations = new ArrayList<>();

            for (Token declaration : declarations) {
                Matcher dMatcher = declaration.matcher();
                dMatcher.find();
                dMatcher.matches();
                var type = dMatcher.group("type").toLowerCase();
                var name = dMatcher.group("name");
                var value = dMatcher.group("value");
                var impl = new DeclarationImplementation(name, type, value);
                variableDeclarations.add(impl);
            }

            var cimpl = new ClassImplementation(c.v1(), functionImplementations, variableDeclarations);
            program.add(cimpl);
        }
        ProgramRepository.setProgram(new Program(program));
    }

    public List<Tuple<String, List<Token>>> collectClasses(List<Token> tokens)
    {
        List<Tuple<String, List<Token>>> classes = new ArrayList<>();
        for (Token token : tokens) {
            if (token.type().equals(TokenType.ClassDef)) {
                token.matcher().find();
                token.matcher().matches();
                String name = token.matcher().group("name");
                List<Token> classTokens = new ArrayList<>();
                classTokens.add(token);
                while (!token.type().equals(TokenType.ClassTerminator)) {
                    token = tokens.get(tokens.indexOf(token) + 1);
                    classTokens.add(token);
                }
                classes.add(new Tuple<>(name, classTokens));
            }
        }
        return classes;
    }

    public List<List<Token>> collectFunctions(List<Token> tokens) {
        List<List<Token>> functions = new ArrayList<>();
        for (Token token : tokens) {
            if (token.type().equals(TokenType.FuncDef)) {
                List<Token> functionTokens = new ArrayList<>();
                functionTokens.add(token);
                while (!token.type().equals(TokenType.EmptyLine)) {
                    token = tokens.get(tokens.indexOf(token) + 1);
                    functionTokens.add(token);
                }
                functions.add(functionTokens);
            }
        }
        return functions;
    }

    public List<Token> collectDeclarations(List<Token> tokens) {
        List<Token> declarations = new ArrayList<>();
        for (Token token : tokens) {
            if (token.type().equals(TokenType.VariableDecl)) {
                declarations.add(token);
            }
        }
        return declarations;
    }
}
