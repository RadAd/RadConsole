@echo off
setlocal

cd /d %~dp0

set JM_PROJECT=RadConsole

call "%DROPBOX_DIR%\Current\Development\Java\javamake.bat" %*
