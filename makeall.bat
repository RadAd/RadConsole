@echo off
call make.bat %*
for /d %%i in (examples/*) do (
	call "examples\%%i\makedep.bat"
	call "examples\%%i\make.bat" %*
)
