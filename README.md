Maven Search
============
This is a basic CLI that searches Maven Repositories for dependencies based on
some query parameter (eg: "guava").

Usage
=====
```
$ mvn-search guava

com.github.thepacific:guava:2.0.0
com.github.ben-manes.caffeine:guava:2.8.5
org.nd4j:guava:1.0.0-beta7
com.github.cowwoc.requirements:guava:6.0.4
com.google.guava:guava:29.0-jre
org.xbib:guava:28.1
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
