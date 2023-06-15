# Java-DataTables

[![Latest release](https://img.shields.io/github/v/release/PierreAdam/java-datatables)](https://github.com/PierreAdam/java-datatables/releases/latest)
![JDK](https://img.shields.io/badge/JDK-1.8+-blue.svg)
[![Build Status](https://travis-ci.com/PierreAdam/java-datatables.svg?branch=master)](https://travis-ci.com/PierreAdam/java-datatables)
[![GitHub license](https://img.shields.io/github/license/PierreAdam/java-datatables)](https://raw.githubusercontent.com/PierreAdam/java-datatables/master/LICENSE)

Java-DataTables is a library for derivative of my other project Play-DataTables that allows you to easily
integrate [DataTables](https://datatables.net/) into any project or framework.

This library is providing an abstraction for the DataTables requests. __It is not recommended to directly utilize this
library__ as it's the base for other libraries that implements some data providers.

List of the current data providers :

- TODO

*****

## Build the library and local deployment

```shell
$> mvn compile
$> mvn package
$> mvn install
```

### Install or deploy

To install in your local repository.

```shell
$> mvn install
```

To deploy to a remote repository.

```shell
$> mvn verify
$> mvn deploy -P release
```

## How to import the library

### With Sbt

```scala
libraryDependencies += "com.jackson42.java" % "java-datatables" % "23.06"
```

### With Maven

```xml

<dependency>
    <groupId>com.jackson42.play</groupId>
    <artifactId>play-datatables</artifactId>
    <version>23.06</version>
</dependency>
``` 

### Implementing a data provider for Play-DataTables

Example of implementation is available in the tests. For a more concrete example, take a look
at [play-ebean-datatables](https://github.com/PierreAdam/play-ebean-datatables)

## Versions

| Library Version | Tested DataTables Version |
|-----------------|---------------------------|
| 21.06           | 1.10.x                    |

### Changelog

#### 23.06

- Migration from Play-DataTables to a more generic implementation.

## License

This project is released under terms of
the [MIT license](https://raw.githubusercontent.com/PierreAdam/play-ebean-datatables/master/LICENSE).
