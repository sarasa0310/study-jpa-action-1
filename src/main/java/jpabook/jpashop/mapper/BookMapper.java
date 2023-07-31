package jpabook.jpashop.mapper;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.dto.BookForm;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper extends GenericMapper<BookForm, Book>{
}
