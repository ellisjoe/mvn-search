/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.ellisjoe.maven;

import picocli.CommandLine;

import java.util.concurrent.Callable;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Option;
import static picocli.CommandLine.Parameters;

@Command(name = "mvn-search",
        mixinStandardHelpOptions = true,
        version = "0.0.0",
        description = "Searches Maven Central for dependencies.")
class MavenSearchCli implements Callable<Integer> {

    @Parameters(index = "0", description = "The search query to execute")
    private String query;

    @Option(names = {"-r", "--repository"}, defaultValue = "mvn-repo")
    private String repository;

    public static void main(String... args) {
        int exitCode = new CommandLine(new MavenSearchCli()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        initRepository(repository)
                .search(query)
                .forEach(r -> System.out.println(String.format("%s:%s:%s", r.group(), r.artifact(), r.version())));
        return 0;
    }

    private static Repository initRepository(String repository) {
        switch (repository) {
            case "mvn-repo":
                return new MvnRepository();
            case "central":
                return new MavenCentral();
        }
        throw new IllegalArgumentException("Unknown repository: " + repository);
    }
}