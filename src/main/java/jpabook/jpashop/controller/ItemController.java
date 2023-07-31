package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.dto.BookForm;
import jpabook.jpashop.mapper.BookMapper;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final BookMapper bookMapper;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());

        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {
        Book book = bookMapper.toEntity(form);
        itemService.saveItem(book);

        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();

        model.addAttribute("items", items);

        return "items/itemList";
    }

    @GetMapping("/items/{item-id}/edit")
    public String updateItemForm(@PathVariable("item-id") Long itemId, Model model) {
        Book book = (Book) itemService.findOneItem(itemId);
        BookForm form = bookMapper.toDto(book);

        model.addAttribute("form", form);

        return "items/updateItemForm";
    }

    @PostMapping("/items/{item-id}/edit")
    public String updateItem(BookForm form) {
        itemService.updateItem(form);

        return "redirect:/items";
    }

}
