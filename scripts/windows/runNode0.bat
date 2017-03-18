echo "-----node0-----"
echo "copy configs"
echo "clientConfig.properties"
cd ..
type node0.properties > ../site/src/main/resources/config/clientConfig.properties
echo "application.properties"
type node0_app.properties > ../site/src/main/resources/config/application.properties


start cmd /c "cd /d windows && runNode0_maven.bat"
