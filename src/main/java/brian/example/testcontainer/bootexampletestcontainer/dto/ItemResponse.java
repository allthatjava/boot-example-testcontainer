package brian.example.testcontainer.bootexampletestcontainer.dto;

import brian.example.testcontainer.bootexampletestcontainer.entity.Item;
import lombok.Data;

import java.util.List;

@Data
public class ItemResponse {
    private List<Item> items;

    public ItemResponse(List<Item> items) {
        this.items = items;
    }
}
