package dev.clng.token;

/**
 * @author simon & ennio
 **/
public enum TokenType
{
    // Definitions
    MainDef("def main():"),
    FuncDef("def ((?<name>(?!main\\(\\):)[a-zA-Z_][a-zA-Z0-9_]*)\\((?<args>[a-zA-Z_][a-zA-Z0-9_]*\\s{0,1}([a-zA-Z_][a-zA-Z0-9_]*){0,1}(, [a-zA-Z_][a-zA-Z0-9_]*\\s{0,1}([a-zA-Z_][a-zA-Z0-9_]*){0,1})*){0,1}\\)):"),
    ObjDef("def object (?<name>[a-zA-Z_][a-zA-Z0-9_]*):"),
    ClassDef("def class (?<name>[a-zA-Z_][a-zA-Z0-9_]*):"),
    ReturnDef("(?<name>[a-zA-Z_][a-zA-Z0-9_]*)\\(((?<args>[a-zA-Z_0-9]+\\s{0,1}(, [a-zA-Z_0-9]+\\s{0,1})*)){0,1}\\)\\s*<-\\s*(?<value>.*)"),
    Assignment("(?<name>[a-zA-Z_][a-zA-Z0-9_]*)\\s*<-\\s*(?<value>.*)"),
    IfStatement("if (?<condition>.*)\\s*:"),
    PrintStatement("print (?<value>.*)"),
    CommentLine("#(?<comment>.*)"),
    ClassTerminator("!endclass"),
    EmptyLine("^\\s*$"),
    VariableDecl("def (?<type>[a-zA-Z][a-zA-Z_0-9]*) (?<name>[a-zA-Z][a-zA-Z_0-9]*)( = (?<value>.+)){0,1}");

    private final String pattern;

    TokenType(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}