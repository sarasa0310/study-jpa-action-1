package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.dto.BookForm;
import jpabook.jpashop.mapper.BookMapper;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;
    private final BookMapper bookMapper;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOneItem(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    @Transactional
    public void updateItem(BookForm form) {
        Book item = (Book) itemRepository.findOne(form.getId());

        bookMapper.updateFromDto(form, item);

//        item.change(form.getName(), form.getPrice(), form.getStockQuantity());
    }

}
