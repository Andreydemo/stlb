echo "--------------STLB------------"
echo "--------------Client Install----------"
export SCRIPTS_PATH=scripts/mac
export SCRIPTS_UP_TO_ROOT=../..
cd $SCRIPTS_UP_TO_ROOT/load_balancer_client
mvn clean install
cd ../$SCRIPTS_PATH
sh runNode0.sh
sleep 10
sh runNode1.sh
sleep 20
echo "--------------Balancer----------"
osascript -e 'tell app "Terminal"
    do script "sh ~development/projects/stlb/scripts/mac/runBalancer_maven.sh ; exit ; "
end tell'


