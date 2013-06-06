@echo off
setlocal
cd /d %~dp0

set JM_PROJECT=EventView
set JM_DEPENDS=..\..\make.bat
set JM_MAINCLASS=au.radsoft.example.console.EventView

call ..\..\..\javamake.bat %*
