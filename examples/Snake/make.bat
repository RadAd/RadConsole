@echo off
setlocal
cd /d %~dp0

set JM_PROJECT=Snake
set JM_DEPENDS=C:\Users\Adam.Gates\Documents\GitHub\RadConsole\make.bat
set JM_MAINCLASS=au.radsoft.snake.Snake

call ..\..\javamake.bat %*
