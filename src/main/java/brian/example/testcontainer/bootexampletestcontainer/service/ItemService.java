package brian.example.testcontainer.bootexampletestcontainer.service;

import brian.example.testcontainer.bootexampletestcontainer.entity.Item;
import brian.example.testcontainer.bootexampletestcontainer.repository.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ItemService {

    private final ItemRepository repo;

    public Item save(Item item){
        log.info("repo.save has been called");
        return repo.save(item);
    }

    public List<Item> getAllItem(){
        log.info("repo.findAll has been called");
        return repo.findAll();
    }
}
