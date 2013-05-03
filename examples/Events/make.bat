@echo off
setlocal
cd /d %~dp0

set JM_PROJECT=Events
set JM_DEPENDS=..\..\make.bat
set JM_MAINCLASS=au.radsoft.example.console.Events

call "%DROPBOX_DIR%\Current\Development\Java\javamake.bat" %*
