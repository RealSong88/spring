package hello.itemservice.domain.item;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ItemRepositoryTest {

	ItemRepository itemRepository = new ItemRepository();
	
	@AfterEach
	void afterEach() {
		itemRepository.clearStore();
	}
	
	@Test
	void save() {
		//given
		Item item = new Item("itemA", 10000, 10);
		
		//when
		Item saveditem = itemRepository.save(item);
		
		//then
		System.out.println("아이템 : " + itemRepository.findAll().toString() + ", 아이템id : " + item.getId() );
		Item findItem = itemRepository.findById(item.getId());
		
		itemRepository.delete(item.getId());
		System.out.println("아이템 : " + itemRepository.findAll().toString());
		assertThat(findItem).isEqualTo(saveditem);
	}
	
	@Test
	void findAll() {
		//given
		Item item1 = new Item("item1", 10000, 10);
		Item item2 = new Item("item2", 10000, 10);
		
		itemRepository.save(item1);
		itemRepository.save(item2);
		
		//when
		List<Item> result = itemRepository.findAll();
		
		//then
		assertThat(result.size()).isEqualTo(2);
		assertThat(result).contains(item1, item2);
		
	}
	
	@Test
	void updateItem() {
		//given
		Item item = new Item("item1", 10000, 10);
		
		Item savedItem = itemRepository.save(item);
		Long itemId = savedItem.getId();
		
		//when
		Item updateParam = new Item("item2", 2000, 30);
		itemRepository.update(itemId, updateParam);
		
		Item findItem = itemRepository.findById(itemId);
		
		System.out.println("itemName=" + findItem.getItemName() + " price=" + findItem.getPrice());
		
		assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
		assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
		assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
	}
	
}
