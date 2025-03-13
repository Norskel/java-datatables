# Java-DataTables

[![Latest release](https://img.shields.io/github/v/release/PierreAdam/java-datatables)](https://github.com/PierreAdam/java-datatables/releases/latest)
![JDK](https://img.shields.io/badge/JDK-1.8+-blue.svg)
[![GitHub license](https://img.shields.io/github/license/PierreAdam/java-datatables)](https://raw.githubusercontent.com/PierreAdam/java-datatables/master/LICENSE)

Java-DataTables is a library for derivative of my other project Play-DataTables that allows you to easily
integrate [DataTables](https://datatables.net/) into any project or framework.

This library is providing an abstraction for the DataTables requests. __It is not recommended to directly utilize this
library__ as it's the base for other libraries that implements some data providers.

List of the current data providers :

- JOOQ using `java-datatables-jooq`

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
libraryDependencies += "com.github.PierreAdam.java-datatables" % "java-datatables-core" % "1.1.0"
```

### With Maven

```xml

<dependency>
    <groupId>com.github.PierreAdam.java-datatables</groupId>
    <artifactId>java-datatables-core</artifactId>
    <version>1.1.0</version>
</dependency>
``` 

## Versions

| Library Version | Tested DataTables Version |
|-----------------|---------------------------|
| 1.0.0           | 1.10.x                    |
| 1.1.0           | 1.10.x                    |
| 1.2.0           | 1.10.x                    |

## License

This project is released under terms of
the [MIT license](https://raw.githubusercontent.com/PierreAdam/java-datatables/master/LICENSE).
