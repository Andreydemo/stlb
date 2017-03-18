TITLE Balanced_Node1
cd ..\..\site
call mvn clean install
cd target
DEL ..\site-1.0.jar
DEL ..\site-node-1.jar
copy site-1.0.jar ..
cd ..
ren  site-1.0.jar site-node-1.jar
java -jar site-node-1.jar