package dev.clng.token;

import java.util.regex.Matcher;

/**
 * @author simon & ennio
 **/
public record Token(TokenType type, String value, int line, Matcher matcher) { }
