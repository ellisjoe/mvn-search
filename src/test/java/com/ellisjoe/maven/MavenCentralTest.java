package com.ellisjoe.maven;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MavenCentralTest {
    @Test
    void testQuery() {
        MavenCentral mavenCentral = new MavenCentral();
        List<Result> result = mavenCentral.search("guava");
        result.forEach(System.out::println);

        assertThat(result).isNotEmpty();
    }
}