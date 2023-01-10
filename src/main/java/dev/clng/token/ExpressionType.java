package dev.clng.token;

/**
 * @author simon & ennio
 **/
public enum ExpressionType
{
    Addition("(?<left>.*) \\+ (?<right>.*)"),
    Subtraction("(?<left>.*) - (?<right>.*)"),
    Multiplication("(?<left>.*) \\* (?<right>.*)"),
    Division("(?<left>.*) / (?<right>.*)"),
    Modulo("(?<left>.*) % (?<right>.*)"),
    Power("(?<left>.*) \\^ (?<right>.*)"),
    FunctionCall("(?<name>[a-zA-Z_][a-zA-Z0-9_]*)\\((?<args>.*\\s{0,1}([a-zA-Z_][a-zA-Z0-9_]*){0,1}(, .*\\s{0,1}([a-zA-Z_][a-zA-Z0-9_]*){0,1})*){0,1}\\)"),
    Variable("(?<name>[a-zA-Z_][a-zA-Z0-9_]*)"),
    GreaterThan("(?<left>.*) > (?<right>.*)"),
    GreaterThanOrEqual("(?<left>.*) >= (?<right>.*)"),
    LessThan("(?<left>.*) < (?<right>.*)"),
    LessThanOrEqual("(?<left>.*) <= (?<right>.*)"),
    Equal("(?<left>.*) == (?<right>.*)"),
    NotEqual("(?<left>.*) != (?<right>.*)"),
    And("(?<left>.*) && (?<right>.*)"),
    Or("(?<left>.*) \\|\\| (?<right>.*)");
    private final String pattern;

    ExpressionType(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
