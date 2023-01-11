package dev.clng.token;

public enum DataTokenType {
    // Types
    Integer("int"),
    FloatingPoint("float"),
    Char("char"),
    String("string"),
    Vector("vector"),
    Boolean("bool"),
    Tuple("tuple");

    private final String pattern;

    DataTokenType(String pattern) {
        this.pattern = pattern;
    }

    /**
     * @return the pattern
     */
    public String getPattern() {
        return pattern;
    }
}
