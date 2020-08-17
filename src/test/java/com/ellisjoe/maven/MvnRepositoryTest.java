package com.ellisjoe.maven;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MvnRepositoryTest {
    @Test
    void testQuery() {
        MvnRepository mvnRepository = new MvnRepository();
        mvnRepository.search("guava").forEach(System.out::println);
    }
}