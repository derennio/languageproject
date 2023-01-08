package dev.clng.token;

public enum LiteralTokenType {
    // Literals
    Integer("([0-9]+)$"),
    FloatingPoint("([0-9]+\\.[0-9]+)$"),
    Char("('(?<value>.*)')$"),
    String("\"(?<value>[^\"]*)\"$"),
    Boolean("(true|false)$");

    private final String pattern;

    LiteralTokenType(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
