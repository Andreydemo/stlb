echo "-----node1-----"
echo "copy configs"
echo "clientConfig.properties"
cat node1.properties > site/src/main/resources/config/clientConfig.properties
echo "application.properties"
cat node1_app.xml > site/src/main/resources/config/application.properties

osascript -e 'tell app "Terminal"
    do script "sh development/projects/stlb/runNode1_maven.sh ; exit;"
end tell'
