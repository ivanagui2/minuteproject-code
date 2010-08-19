@echo off
call setCp.cmd

"%JAVA_HOME%\bin\java" %CP% net.sf.minuteProject.application.ModelViewGenerator mp-config-roo-last-features.xml
