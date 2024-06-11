# Test Container example

### Table of Contents
- [Unit Test](#unit-test)
- [Integration Test](#integration-test)
- [Run the maven command](#run-maven-command)

<a name="unit-test"></a>
### Unit Test
Source code: `src/test`
* Typical Unit Test with MockMvc(Controller classes) and Mockito(Service classes)

<a name="integration-test"></a>
### Integration Test
Source code: `src/integration-test`
* Uses Test Container technique
* Following dependencies are required for PostgreSQL database test container
```xml

    <!-- Test Container related dependencies - start -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-testcontainers</artifactId>
    </dependency>
    <dependency>
        <groupId>org.testcontainers</groupId>
        <artifactId>junit-jupiter</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.testcontainers</groupId>
        <artifactId>postgresql</artifactId>
        <scope>test</scope>
    </dependency>
    <!-- Test Container related dependencies - end --> 
```
* To separate unit test and integration test, following plugin options are required on pom.xml
```xml
<!-- Integration Test setup - start -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-failsafe-plugin</artifactId>
    <version>2.22.2</version>
    <executions>
        <execution>
            <goals>
                <goal>integration-test</goal>
                <goal>verify</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <includes>
            <include>**/*IT.java</include>
        </includes>
    </configuration>
</plugin>
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>build-helper-maven-plugin</artifactId>
    <version>1.9.1</version>
    <executions>
        <execution>
            <id>add-test-source</id>
            <phase>process-resources</phase>
            <goals>
                <goal>add-test-source</goal>
            </goals>
            <configuration>
                <sources>
                    <source>src/integration-test/java</source>
                </sources>
            </configuration>
        </execution>
    </executions>
</plugin>
<!-- Integration Test setup - end -->
```
* TestConfiguration is optional. This can be setup in each test classes but, for the convenience, we will use @TestConfiguration
```java
@TestConfiguration
public class TestContainerConfig
{
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest");

    @Bean
    public PostgreSQLContainer<?> postgreSQLContainer(){
        return postgreSQLContainer;
    }
}
```
* Actual Integration Test class setup
```java
@SpringBootTest
@AutoConfigureMockMvc
@Import(TestContainerConfig.class)
public class ItemControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", TestContainerConfig.postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", TestContainerConfig.postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", TestContainerConfig.postgreSQLContainer::getPassword);
    }

    @BeforeAll
    static void beforeAll() {
        TestContainerConfig.postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        TestContainerConfig.postgreSQLContainer.stop();
    }

    @Test
    public void postItemAndReadItBack() throws Exception {
        ...
    }
}
```
Use `@DynamicPropertySource` to register datasource related properties  
and start and stop the container before all and after all test

<a name="run-maven-command"></a>
### Run the maven command
You must run the Docker Environment(Docker Desktop) before running the maven command since the test container uses Docker environment.
* If you want skip the integration test run with following option on maven command. Otherwise, both Unit Test and Integration Test will be executed.
`mvn clean install -DskipITs=true`