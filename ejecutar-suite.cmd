@echo off
rem Ejecuta una suite por consola. Requiere KATALON_HOME y KATALON_API_KEY (licencia KRE).
setlocal
set "SUITE=%~1"
if "%SUITE%"=="" set "SUITE=Test Suites/TS_Smoke"
set "BROWSER=%~2"
if "%BROWSER%"=="" set "BROWSER=Chrome"
if "%KATALON_API_KEY%"=="" (echo Defina KATALON_API_KEY & exit /b 1)

"%KATALON_HOME%\katalonc.exe" -noSplash -runMode=console ^
  -projectPath="%~dp0PichinchaKatalon.prj" ^
  -retry=0 -testSuitePath="%SUITE%" -browserType="%BROWSER%" ^
  -executionProfile="default" -apiKey="%KATALON_API_KEY%" ^
  --config -webui.autoUpdateDrivers=true
