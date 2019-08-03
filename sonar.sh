#mvn clean compile -DskipTests 
#或者 
#mvn verify 运行任何检查，验证包是否有效且达到质量标准。
#命令2种方式
#1、mvn clean verify sonar:sonar
#2、mvn clean install mvn sonar:sonar
#3、mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.6.0.1398:sonar  指定运行版本
echo -e "\033[40;37m 编译阶段 \033[0m"
#mvn clean compile -Dmaven.test.skip=true
echo -e "\033[40;37m 扫描阶段 \033[0m"
#mvn test sonar:sonar -Dsonar.host.url=http://localhost:9000/report/ARCH -Dsonar.login=admin -Dsonar.password=admin
mvn clean verify sonar:sonar -Dsonar.host.url=http://localhost:9000/report/ARCH -Dsonar.login=admin -Dsonar.password=admin -Dmaven.test.skip=false
echo -e "\033[40;37m 上传数据 \033[0m"