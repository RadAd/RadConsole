@echo off
setlocal

cd /d %~dp0

rem set JM_PROJECT=RadConsole
rem call ..\javamake.bat %*

set JAVA_HOME=D:\Utils\Devel\jdk1.7_x86
set ANT_HOME=D:\Utils\Devel\apache-ant

call "%ANT_HOME%\bin\ant" %*
