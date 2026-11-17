@echo off
title Tourist_Records_EndLabExam - Spring Boot Application
echo ======================================================================
echo Starting Tourist Records & Hotel Check-in Service (End Lab Exam)
echo ======================================================================

set JAVA_HOME=C:\Program Files\Java\jdk-21
set PATH=%JAVA_HOME%\bin;%PATH%

cd /d "%~dp0"
call mvnw.cmd spring-boot:run
pause
