Maven Search
============
This is a basic CLI that searches Maven Repositories for dependencies based on
some query parameter (eg: "guava").

Usage
=====
```
$ mvn-search guava

com.google.guava:guava:29.0-jre
com.github.ben-manes.caffeine:guava:2.8.5
com.google.guava:guava-testlib:29.0-jre
com.fasterxml.jackson.datatype:jackson-datatype-guava:2.11.2
com.google.guava:guava-gwt:29.0-jre
com.google.guava.wso2:guava:12.0.0.wso2v1
com.github.rholder:guava-retrying:2.0.0
com.google:guava:10.0.1-v20120...
com.google.guava:guava-jdk5:17.0
org.assertj:assertj-guava:3.4.0
```

Options
=======
There is build-in support for searching either [MvnRepository](https://mvnrepository.com/) (default) or 
[Maven Central](https://search.maven.org/) which can be toggled using the `-r` or `--repository` flags:
```
$ mvn-search -r mvn-repo <query>
```
or
```
$ mvn-search -r central <query>
```

Installation
============
Running `./gradlew install` will install the `mvn-search` binary in
`/usr/local/bin`.
