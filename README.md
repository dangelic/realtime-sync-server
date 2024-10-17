### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.4/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.3.4/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#web)
* [WebSocket](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#messaging.websockets)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#using.devtools)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)

## Initialize my dev environment (Mac AArch64)

### Local Java
* Install JDK21: `brew install openjdk@21`
* Add path: `echo 'export PATH="/opt/homebrew/opt/openjdk@21/bin:$PATH"' >> ~/.zshrc`
* Reload shell `source ~/.zshrc`
* Make compiler find it: `export CPPFLAGS="-I/opt/homebrew/opt/openjdk@21/include"`
* Symlink for wrappers to find it: `sudo ln -sfn /opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-21.jdk`

### Maven
* Install Maven wrapper files: `mvn -N io.takari:maven:wrapper`
* Start `./mvnw spring-boot:run`
