@echo off
cd /d %~dp0

set MYJAR=bin\RadConsole.jar

xcopy /I /D libs\* bin

call "%DROPBOX_DIR%\Current\Development\Java\javamake.bat" %*
