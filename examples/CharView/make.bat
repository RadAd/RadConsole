@echo off
setlocal
cd /d %~dp0

set JM_PROJECT=CharView
set JM_DEPENDS=..\..\make.bat
set JM_MAINCLASS=au.radsoft.example.console.CharView

call ..\..\..\javamake.bat %*
