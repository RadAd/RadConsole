@echo off
setlocal
cd /d %~dp0

set JM_PROJECT=Tetris
set JM_DEPENDS=..\..\make.bat
set JM_MAINCLASS=au.radsoft.tetris.Tetris

call ..\..\..\javamake.bat %*
