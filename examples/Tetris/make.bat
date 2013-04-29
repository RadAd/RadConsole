@echo off
setlocal
cd /d %~dp0

set JM_PROJECT=Tetris
set JM_DEPENDS=C:\Users\Adam.Gates\Documents\GitHub\RadConsole\make.bat
set JM_INSTALL_DIR=%CD%\libs
set JM_MAINCLASS=au.radsoft.tetris.Tetris

call ..\..\javamake.bat %*
