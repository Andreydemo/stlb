echo "--------------STLB------------"
echo "--------------Client Install----------"
set SCRIPTS_PATH=scripts/windows
set SCRIPTS_UP_TO_ROOT=../..
cd %SCRIPTS_UP_TO_ROOT%/load_balancer_client
call mvn clean install
cd ../%SCRIPTS_PATH%
call runNode0.bat
TIMEOUT 20
cd ../%SCRIPTS_PATH%
call runNode1.bat
TIMEOUT 30
echo "--------------Balancer----------"
cd ../%SCRIPTS_PATH%
start cmd /c "runBalancer_maven.bat"


