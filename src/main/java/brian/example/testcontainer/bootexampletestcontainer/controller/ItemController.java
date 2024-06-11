package brian.example.testcontainer.bootexampletestcontainer.controller;

import brian.example.testcontainer.bootexampletestcontainer.dto.ItemResponse;
import brian.example.testcontainer.bootexampletestcontainer.entity.Item;
import brian.example.testcontainer.bootexampletestcontainer.service.ItemService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class ItemController {

    private ItemService service;

    @PostMapping("/items")
    public Item postItem(@RequestBody Item item){
        log.info("POST /items has been called");
        return service.save(item);
    }

    @GetMapping("/items")
    public ResponseEntity<ItemResponse> getAll(){
        log.info("GET /items has been called");
        List<Item> allItems = service.getAllItem();
        if( allItems.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(new ItemResponse(allItems));
    }
}
