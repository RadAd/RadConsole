@echo off
setlocal
<<<<<<< HEAD
cd /d %~dp0

set JM_PROJECT=Events
set JM_DEPENDS=..\..\make.bat
set JM_MAINCLASS=au.radsoft.example.console.Events

call "%DROPBOX_DIR%\Current\Development\Java\javamake.bat" %*
=======

cd /d %~dp0

call ..\..\radjava\radjava.bat %*
>>>>>>> ac60ce4bdc64de658a4c40cd616696abbcee0a4c
