echo "-----node1-----"
echo "copy configs"
echo "clientConfig.properties"
cd ..
type node1.properties > ../site/src/main/resources/config/clientConfig.properties
echo "application.properties"
type node1_app.properties > ../site/src/main/resources/config/application.properties


start cmd /c "cd /d windows && runNode1_maven.bat"
