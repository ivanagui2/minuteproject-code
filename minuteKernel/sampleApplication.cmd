@echo off
set LOCALCLASSPATH=
for %%i in ("application\java\lib\*.jar") do call "lcp.cmd" %%i
for %%i in ("lib\*.jar") do call "lcp.cmd" %%i
for %%i in ("lib\extra\*.jar") do call "lcp.cmd" %%i
set LOCALCLASSPATH=%LOCALCLASSPATH%;config\
@rem
echo %LOCALCLASSPATH%
set CP=-cp %LOCALCLASSPATH%

"%JAVA_HOME%\bin\java" %CP% net.sf.minuteProject.application.ModelGenerator generator-config-sample.xml
