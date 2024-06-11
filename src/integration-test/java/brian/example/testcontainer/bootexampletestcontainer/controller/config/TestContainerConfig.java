package brian.example.testcontainer.bootexampletestcontainer.controller.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
public class TestContainerConfig
{
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest");

    @Bean
    public PostgreSQLContainer<?> postgreSQLContainer(){
        return postgreSQLContainer;
    }
}
