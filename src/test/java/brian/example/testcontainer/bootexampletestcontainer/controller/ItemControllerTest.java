package brian.example.testcontainer.bootexampletestcontainer.controller;

import brian.example.testcontainer.bootexampletestcontainer.dto.ItemResponse;
import brian.example.testcontainer.bootexampletestcontainer.entity.Item;
import brian.example.testcontainer.bootexampletestcontainer.service.ItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class ItemControllerTest {

    private ItemController controller;

    @MockBean
    private ItemService service;

    @BeforeEach
    public void setup(){
        controller = new ItemController(service);
    }

    @Test
    public void postItemTest() throws Exception {
        Item item = new Item();
        item.setName("Galaxy");
        item.setDescription("Samsung's smartphone");
        item.setPrice(new BigDecimal("799.99"));

        when(service.save(item)).thenReturn(new Item(1,"Galaxy", "Samsung's smartphone", new BigDecimal("799.99")));

        Item returnedItem = controller.postItem(item);
        assertEquals(returnedItem.getId(), 1);
    }

    @Test
    public void getAll_with_valid_data() throws Exception {

        List<Item> expected = Arrays.asList(
                new Item(1,"iPhone", "Apple's smartphone", new BigDecimal("999.99")),
                new Item(2,"Galaxy", "Samsung's smartphone", new BigDecimal("799.99"))
        );

        when(service.getAllItem()).thenReturn(expected);

        ResponseEntity<ItemResponse> returnedItems = controller.getAll();

        assertEquals( 2, returnedItems.getBody().getItems().size());
    }

    @Test
    public void getAll_with_not_found() throws Exception {

        List<Item> expected = new ArrayList<>();

        when(service.getAllItem()).thenReturn(expected);

        ResponseEntity<ItemResponse> result = controller.getAll();
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}
