@echo off
SETLOCAL ENABLEDELAYEDEXPANSION
cd /d %~dp0

set MYJAR=..\..\bin\Example1.jar
set JAVA_LIBS=;..\..\libs\jna.jar;..\..\bin\RadConsole.jar

call "%DROPBOX_DIR%\Current\Development\Java\javamake.bat" %*

