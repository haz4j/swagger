# Swagger 4 JSON-RPC

### About
This project is implementation of [Swagger](https://swagger.io/) for [JSON-RPC](https://www.jsonrpc.org/) with usage of [jsonrpc4j](https://github.com/briandilley/jsonrpc4j)

### Usage
How to add swagger magic to your typical jsonrpc4j application?

pom.xml: add swagger annotations and swagger4jsonrpc
```xml
    <dependencies>
        <!--spring boot, jsonrpc4j, etc-->
        ...

        <!--add this to use annotation @Api-->
        <dependency>
            <groupId>io.swagger</groupId>
            <artifactId>swagger-annotations</artifactId>
            <version>1.5.21</version>
        </dependency>

        <!--add this to generate swagger-ui.html page-->
        <dependency>
            <groupId>com.github.haz4j</groupId>
            <artifactId>swagger4jsonrpc</artifactId>
            <version>0.0.1.RELEASE</version>
        </dependency>
    </dependencies>
```
Configuration: add @EnableSwagger4JsonRpc and bean Docket

```java
@SpringBootApplication
@Configuration
@EnableSwagger4JsonRpc //add this to enable swagger
public class Config {

    //add this to customize swagger 
    @Bean
    public Docket docket(){
        return Docket.builder().build();
    }

    public static void main(String[] args) {
        SpringApplication.run(Config.class, args);
    }

    @Bean
    @Autowired
    public static AutoJsonRpcServiceImplExporter autoJsonRpcServiceImplExporter() {
        return new AutoJsonRpcServiceImplExporter();
    }
}
```

Interface: add @Api
```java
@JsonRpcService("/v1/api")
@Api(tags = "api", value = "api") //add this to let swagger know that you want to see it
//on swagger-ui page
public interface ApiInterface {
    Integer save(@JsonRpcParam(value = "value") Integer value);
}
```

Implementation: do nothing :)
```java
@Service
@AutoJsonRpcServiceImpl
public class Impl implements ApiInterface {
    @Override
    public Integer save(Integer value){
        return value;
    }
}
```

Launch your spring boot app and check out [http://app-path/swagger-ui.html](http://localhost:8080/swagger-ui.html)
 
![swagger-ui.html](resources/screenshot.png?raw=true)

