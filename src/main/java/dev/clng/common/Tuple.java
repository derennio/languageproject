package dev.clng.common;

import java.io.Serializable;

public record Tuple<T, V>(T v1, V v2) implements Serializable { }
