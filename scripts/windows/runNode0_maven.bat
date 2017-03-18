TITLE Balanced_Node0
cd ..\..\site
call mvn clean install
cd target
DEL ..\site-1.0.jar
DEL ..\site-node-0.jar
copy site-1.0.jar ..
cd ..
ren  site-1.0.jar site-node-0.jar
java -jar site-node-0.jar