@echo off
setlocal
cd /d %~dp0

set JM_PROJECT=Example1
set JM_DEPENDS=..\..\make.bat
set JM_MAINCLASS=au.radsoft.example.console.Test

call "%DROPBOX_DIR%\Current\Development\Java\javamake.bat" %*
