@echo off
setlocal
cd /d %~dp0

rem set JM_PROJECT=CharView
rem set JM_DEPENDS=..\..\make.bat
rem set JM_MAINCLASS=au.radsoft.example.console.CharView

rem call ..\..\..\javamake.bat %*

set JAVA_HOME=D:\Utils\Devel\jdk1.7_x86
set ANT_HOME=D:\Utils\Devel\apache-ant

call "%ANT_HOME%\bin\ant" %*
