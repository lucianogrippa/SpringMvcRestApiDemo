@rem sets only if must build with specific version of jdk otherwise comment it
@rem export JAVA_HOME=/home/lucianogrippa/sdk/jdk8u252-b09

@echo off

set "MVNW_FILE=mvnw.cmd"

@rem search  JAVA_HOME
if exist "%JAVA_HOME%\bin\java.exe" (
	echo "JAVA_HOME found in %JAVA_HOME%"
) else (
	echo "no JAVA_HOME environment settings found please set it"
	exit
)

if not exist "%MVNW_FILE%" (
    echo "%MVNW_FILE% does not exist"
    echo "create wrapper"
    call mvn -N io.takari:maven:wrapper
)

call mvnw.cmd test