package brian.example.testcontainer.bootexampletestcontainer.repository;

import brian.example.testcontainer.bootexampletestcontainer.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

}
