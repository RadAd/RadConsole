@echo off
setlocal

cd /d %~dp0

set JM_PROJECT=RadConsole

call ..\javamake.bat %*
