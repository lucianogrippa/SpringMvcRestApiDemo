@rem sets only if must build with specific version of jdk otherwise comment it
@rem export JAVA_HOME=/home/lucianogrippa/sdk/jdk8u252-b09

@echo off

set "JAVA_HOME=C:\\Projects\\rai-projects\\sdks\\openjdk-11.0.2\\jdk-11.0.2"
set "MVNW_FILE=mvnw.cmd"
set "DEPLOYING_PATH=.\docker\wildfly\standalone\deployments\SpringRestApiDemo.war"
set "DEPLOYING_PATH_TOMCAT=.\docker\tomcat\webapps\SpringRestApiDemo.war"
set "SERVER_PATH=C:\\jboss-platforms\\wildfly-19.1.0.Final\\standalone\deployments\\SpringRestApiDemo.war"

@rem set "DEPLOYING_PATH=C:\jboss-platforms\wildfly-10.1.0.Final\standalone\deployments\SpringRestApiDemo.war"
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

if [%1]==["-skip"] (
	goto skiptest
)

if [%1]==[] (
	goto all
)

if [%1]==["-server"] (
	goto toserver
)

:toserver{ 
	echo "skipping test ------------------------"
	call mvnw.cmd clean package
	goto doneServer 
}

:skiptest (
    echo "skipping test ------------------------"
	call mvnw.cmd clean package -Dmaven.test.skip=true
	goto done
)


:all (
    echo "compiling  ------------------------"
	call mvnw.cmd clean package
	goto done
)

:doneServer (
	echo "try to copy .\target/SpringRestApiDemo.war  %DEPLOYING_PATH%" \
	copy /Y .\target\SpringRestApiDemo.war %SERVER_PATH%
)

:done (
	echo "try to copy .\target/SpringRestApiDemo.war  %DEPLOYING_PATH%" \
	copy /Y .\target\SpringRestApiDemo.war %DEPLOYING_PATH%
)

:done (
	echo "try to copy .\target/SpringRestApiDemo.war  %DEPLOYING_PATH%" \
	copy /Y .\target\SpringRestApiDemo.war %DEPLOYING_PATH_TOMCAT%
)