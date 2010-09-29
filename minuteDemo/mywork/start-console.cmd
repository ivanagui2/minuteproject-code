
@rem set JAVA_HOME=.. 
set LOCALCLASSPATH= 
for %%i in ("..\application\lib\minuteKernel*.jar") do call lcp %%i 
for %%i in ("..\application\lib\*.jar") do call lcp %%i 
for %%i in ("..\application\lib\extra\*.jar") do call lcp %%i 
set LOCALCLASSPATH=%LOCALCLASSPATH%;../ 
@rem 
echo %LOCALCLASSPATH% 
set CP=-cp %LOCALCLASSPATH%
echo %CP%

"%JAVA_HOME%\bin\java" %CP% net.sf.minuteProject.console.ConsoleSample

