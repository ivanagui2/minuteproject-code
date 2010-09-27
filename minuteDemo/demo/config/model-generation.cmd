@echo off
@rem set JAVA_HOME=..
set LOCALCLASSPATH=
for %%i in ("..\..\application\lib\minuteKernel*.jar") do set LOCALCLASSPATH=%LOCALCLASSPATH%;%%i
for %%i in ("..\..\application\lib\*.jar") do set LOCALCLASSPATH=%LOCALCLASSPATH%;%%i
for %%i in ("..\..\application\lib\extra\*.jar") do set LOCALCLASSPATH=%LOCALCLASSPATH%;%%i
set LOCALCLASSPATH=%LOCALCLASSPATH%;config\
@rem
echo %LOCALCLASSPATH%
set CP=-cp %LOCALCLASSPATH%

"%JAVA_HOME%\bin\java" %CP% net.sf.minuteProject.application.ModelViewGenerator %1
