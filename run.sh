echo "--------------STLB------------"
echo "--------------Client Install----------"
cd load_balancer_client
mvn clean install
cd ..
sh runNode0.sh
sleep 10
sh runNode1.sh
sleep 10
echo "--------------Balancer----------"
osascript -e 'tell app "Terminal"
    do script "sh development/projects/stlb/runBalancer_maven.sh ; exit ; "
end tell'


