package dev.clng.token;

/**
 * @author simon & ennio
 **/
public enum TokenType
{
    // Definitions
    FuncDef("def ((?<name>[a-zA-Z_][a-zA-Z0-9_]*)\\((?<args>[a-zA-Z_][a-zA-Z0-9_]*\\s{0,1}([a-zA-Z_][a-zA-Z0-9_]*){0,1}(, [a-zA-Z_][a-zA-Z0-9_]*\\s{0,1}([a-zA-Z_][a-zA-Z0-9_]*){0,1})*){0,1}\\)):"),
    ClassDef("def class (?<name>[a-zA-Z_][a-zA-Z0-9_]*):"),
    ReturnDef("(?<name>[a-zA-Z_][a-zA-Z0-9_]*)\\(\\)\\s*<-\\s*(?<value>.*)"),
    Assignment("(?<name>[a-zA-Z_][a-zA-Z0-9_]*)\\s*=\\s*(?<value>.*)"),
    IfStatement("if (?<condition>.*): (?<code>.*)"),
    PrintStatement("print (?<value>.*)"),
    CommentLine("#(?<comment>.*)"),
    ClassTerminator("!endclass"),
    EmptyLine("^\\s*$"),
    VariableDecl("def (?<type>[a-zA-Z][a-zA-Z_0-9]*) (?<name>[a-zA-Z][a-zA-Z_0-9]*)( = (?<value>.+)){0,1}"),
    ExternalFuncCall("((?<class>[a-zA-Z_][a-zA-Z0-9_]*)\\.){0,1}(?<name>[a-zA-Z_][a-zA-Z0-9_]*)\\((?<args>.*\\s{0,1}([a-zA-Z_][a-zA-Z0-9_]*){0,1}(, .*\\s{0,1}([a-zA-Z_][a-zA-Z0-9_]*){0,1})*){0,1}\\)"),
    For("for (?<name>[a-zA-Z_][a-zA-Z0-9_]*) in (?<range>[0-9]+): (?<code>.*)");
    private final String pattern;

    TokenType(String pattern) {
        this.pattern = pattern;
    }

    /**
     * @return the pattern
     */
    public String getPattern() {
        return pattern;
    }
}