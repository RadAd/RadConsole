RadConsole [![GitHub tag](https://img.shields.io/github/tag/RadAd/RadConsole.svg?maxAge=2592000)](https://github.com/RadAd/RadConsole/tags) [![GitHub tag](https://img.shields.io/github/release/RadAd/RadConsole.svg?maxAge=2592000)](https://github.com/RadAd/RadConsole/releases)
==========

A Java api for a win32-like console.

The core of the module is used through the interface [Console](src/au/radsoft/console/Console.java).

Create an instance:

    Console console = ConsoleUtils.create("Test", 80, 25, true);

Dependency: [Java Native Access](https://github.com/twall/jna).
