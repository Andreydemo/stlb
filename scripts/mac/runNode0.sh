echo "-----node0-----"
echo "copy configs"
echo "clientConfig.properties"
cat ../node0.properties > ../../site/src/main/resources/config/clientConfig.properties
echo "application.properties"
cat ../node0_app.properties > ../../site/src/main/resources/config/application.properties

osascript -e 'tell app "Terminal"
    do script "sh development/projects/stlb/scripts/mac/runNode0_maven.sh ; exit;"
end tell' 
