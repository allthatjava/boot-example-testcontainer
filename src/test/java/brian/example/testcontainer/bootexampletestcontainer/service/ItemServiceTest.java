package brian.example.testcontainer.bootexampletestcontainer.service;

import brian.example.testcontainer.bootexampletestcontainer.entity.Item;
import brian.example.testcontainer.bootexampletestcontainer.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ItemServiceTest {
    private ItemService service;

    @MockBean
    private ItemRepository repo;

    @BeforeEach
    public void init(){
        this.service = new ItemService(repo);
    }

    @Test
    public void saveTest(){
        Item item = new Item();
        item.setName("Galaxy");
        item.setDescription("Samsung's smartphone");
        item.setPrice(new BigDecimal("799.99"));

        Item expected = new Item(1,"Galaxy", "Samsung's smartphone", new BigDecimal("799.99"));

        when(repo.save(item)).thenReturn(expected);

        Item actual = service.save(item);
        assertEquals( expected.toString(), actual.toString());
    }

    @Test
    public void getAllItemTest(){
        Item item = new Item();
        item.setName("Galaxy");
        item.setDescription("Samsung's smartphone");
        item.setPrice(new BigDecimal("799.99"));

        List<Item> expected = Arrays.asList(
                new Item(1,"Galaxy", "Samsung's smartphone", new BigDecimal("799.99")),
                new Item(1,"Galaxy", "Samsung's smartphone", new BigDecimal("799.99"))
        );

        when(repo.findAll()).thenReturn(expected);

        List<Item> actual = service.getAllItem();
        assertEquals( expected.size(), actual.size());
    }
}
