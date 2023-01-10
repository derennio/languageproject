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
    public IStatement convertTokenToStatement(Token token)
    {
        // check if either t1 or t2 are false
        var t1 = token.matcher().find();
        var t2 = token.matcher().matches();

        if (!t1 || !t2) {
            System.out.println(token.value());
            throw new IllegalArgumentException(String.format("Syntax error in line %d: Syntax does not match type %s", token.line() + 1, token.type()));
        }

        return switch (token.type()) {
            case ReturnDef -> new ReturnStatement(token.matcher().group("name"), token.matcher().group("value"));
            case Assignment -> new AssignmentStatement(token.matcher().group("name"), token.matcher().group("value"));
            case IfStatement -> new IfStatement(token.matcher().group("condition"), token.matcher().group("code"));
            case PrintStatement -> new PrintStatement(token.matcher().group("value"));
            case CommentLine -> new CommentStatement();
            case ExternalFuncCall -> new FunctionCallStatement(token.matcher().group("class"), token.matcher().group("name"), token.matcher().group("args").split(","));
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
                if (args == null) {
                    args = "";
                }
                List<IStatement> fStatements = new ArrayList<>();
                for (Token token : function.subList(1, function.size() - 1)) {
                    IStatement statement = convertTokenToStatement(token);
                    fStatements.add(statement);
                }

                //List<IfImplementation> ifStatements = collectIfStatements(function.subList(1, function.size() - 1));

                var impl = new FunctionImplementation(name, args.split(","), fStatements);
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

    public List<IfImplementation> collectIfStatements(List<Token> tokens) {
        List<IfImplementation> ifStatements = new ArrayList<>();
        List<List<Token>> ifBlocks = new ArrayList<>();
        for (Token token : tokens) {
            if (token.type().equals(TokenType.IfStatement)) {
                List<Token> functionTokens = new ArrayList<>();
                functionTokens.add(token);
                while (!token.type().equals(TokenType.EndIf)) {
                    token = tokens.get(tokens.indexOf(token) + 1);
                    functionTokens.add(token);
                }
                ifBlocks.add(functionTokens);
            }
        }

        for (List<Token> ifBlock : ifBlocks) {
            List<Token> successOps = new ArrayList<>();
            List<Token> failureOps = new ArrayList<>();
            for (Token token : ifBlock) {
                while (!token.type().equals(TokenType.Else)) {
                    token = ifBlock.get(ifBlock.indexOf(token) + 1);
                    successOps.add(token);
                }
                while (!token.type().equals(TokenType.EndIf)) {
                    token = ifBlock.get(ifBlock.indexOf(token) + 1);
                    failureOps.add(token);
                }
            }

            List<IStatement> fsStatements = new ArrayList<>();
            for (Token token : successOps.subList(1, successOps.size() - 1)) {
                IStatement statement = convertTokenToStatement(token);
                fsStatements.add(statement);
            }

            List<IStatement> ffStatements = new ArrayList<>();
            for (Token token : failureOps.subList(1, failureOps.size() - 1)) {
                IStatement statement = convertTokenToStatement(token);
                ffStatements.add(statement);
            }

            IfImplementation ifImplementation = new IfImplementation(ifBlock.get(0).value(), fsStatements, ffStatements);
            ifStatements.add(ifImplementation);
        }

        return ifStatements;
    }
}
