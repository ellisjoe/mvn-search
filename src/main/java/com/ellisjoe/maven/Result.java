package com.ellisjoe.maven;

import org.immutables.value.Value;

@Value.Immutable
@Value.Style(allParameters = true)
public interface Result {
    String group();

    String artifact();

    String version();

    static Result of(String group, String artifact, String version) {
        return ImmutableResult.of(group, artifact, version);
    }
}
