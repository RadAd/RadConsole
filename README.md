RadConsole [![GitHub tag](https://img.shields.io/github/release/RadAd/RadConsole.svg?maxAge=2592000)](https://github.com/RadAd/RadConsole/releases) [![GitHub commits](https://img.shields.io/github/commits-since/RadAd/RadConsole/latest.svg?maxAge=2592000)](https://github.com/RadAd/RadConsole/commits/master)
==========

A Java api for a win32-like console.

The core of the module is used through the interface [Console](src/au/radsoft/console/Console.java).

Create an instance:

    Console console = ConsoleUtils.create("Test", 80, 25, true);

Dependencies
------------
[Java Native Access](https://github.com/twall/jna) [![Maven](https://img.shields.io/maven-central/v/net.java.dev.jna/jna.svg?maxAge=2592000)](http://mvnrepository.com/artifact/net.java.dev.jna/jna)
