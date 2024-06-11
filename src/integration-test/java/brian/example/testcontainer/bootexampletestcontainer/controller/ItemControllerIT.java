package brian.example.testcontainer.bootexampletestcontainer.controller;

import brian.example.testcontainer.bootexampletestcontainer.controller.config.TestContainerConfig;
import brian.example.testcontainer.bootexampletestcontainer.entity.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestContainerConfig.class)
public class ItemControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", TestContainerConfig.postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", TestContainerConfig.postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", TestContainerConfig.postgreSQLContainer::getPassword);
    }

    @BeforeAll
    static void beforeAll(){
        TestContainerConfig.postgreSQLContainer.start();
    }
    @AfterAll
    static void afterAll(){
        TestContainerConfig.postgreSQLContainer.stop();
    }

    @Test
    public void postItemAndReadItBack() throws Exception {
        // Write an item test to test container
        Item item = new Item();
        item.setName("Galaxy");
        item.setDescription("Samsung's smartphone");
        item.setPrice(new BigDecimal("799.99"));

        mockMvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( new ObjectMapper().writeValueAsString(item) )
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());

        // Read all test from test container
        getAllItemsTest();
    }

    public void getAllItemsTest() throws Exception {
        /* expected response body
        {
            "items": [
                    {
                        "id": 2,
                            "name": "Galaxy",
                            "description": "Samsung's smartphone",
                            "price": 799.99
                    }
            ]
        }
         */
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/items")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].id").value(1))
                .andExpect(jsonPath("$.items[0].name").value("Galaxy"));
    }
}
