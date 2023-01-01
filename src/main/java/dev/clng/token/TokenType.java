package dev.clng.token;

/**
 * @author ennio
 **/
public enum TokenType
{
    // Definitions
    FuncDef("def ((?<name>[a-zA-Z_][a-zA-Z0-9_]*)\\((?<args>[a-zA-Z_][a-zA-Z0-9_]*\\s{0,1}([a-zA-Z_][a-zA-Z0-9_]*){0,1}(, [a-zA-Z_][a-zA-Z0-9_]*\\s{0,1}([a-zA-Z_][a-zA-Z0-9_]*){0,1})*){0,1}\\)):"),
    ObjDef("def object (?<name>[a-zA-Z_][a-zA-Z0-9_]*):"),
    ClassDef("def class (?<name>[a-zA-Z_][a-zA-Z0-9_]*):"),
    ReturnDef("(?<name>[a-zA-Z_][a-zA-Z0-9_]*)\\((?<args>[a-zA-Z_][a-zA-Z0-9_]*\\s{0,1}([a-zA-Z_][a-zA-Z0-9_]*){0,1}(, [a-zA-Z_][a-zA-Z0-9_]*\\s{0,1}([a-zA-Z_][a-zA-Z0-9_]*){0,1})*)\\)\\s*<-\\s*(?<value>.*)"),
    Assignment("(?<name>[a-zA-Z_][a-zA-Z0-9_]*)\\s*<-\\s*(?<value>.*)"),
    IfStatement("if (?<condition>.*)\\s*:"),
    PrintStatement("print (?<value>.*)"),
    CommentLine("#(?<comment>.*)"),
    EmptyLine("^\\s*$");

    private final String pattern;

    TokenType(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
