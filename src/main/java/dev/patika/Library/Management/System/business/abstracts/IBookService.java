package dev.patika.Library.Management.System.business.abstracts;

import dev.patika.Library.Management.System.entities.Author;
import dev.patika.Library.Management.System.entities.Book;
import org.springframework.data.domain.Page;

public interface IBookService {
    Book save(Book book);
    Book get(int id);
    Page<Book> cursor(int page, int pageSie);
    Book update(Book book);
    boolean delete(int id);
}
