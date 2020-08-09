package com.ellisjoe.maven;

import java.util.List;

public interface Repository {
    List<Result> search(String query);
}
