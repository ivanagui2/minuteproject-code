@echo off
@rem set JAVA_HOME=..
set LOCALCLASSPATH=
for %%i in ("..\..\application\lib\*.jar") do call "lcp.cmd" %%i
for %%i in ("..\..\application\lib\extra\*.jar") do call "lcp.cmd" %%i
set LOCALCLASSPATH=%LOCALCLASSPATH%;config\
@rem
echo %LOCALCLASSPATH%
set CP=-cp %LOCALCLASSPATH%

"%JAVA_HOME%\bin\java" %CP% net.sf.minuteProject.application.ModelGenerator mp-config-ibatis.xml
