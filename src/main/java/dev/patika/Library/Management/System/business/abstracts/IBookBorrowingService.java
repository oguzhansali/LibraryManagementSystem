package dev.patika.Library.Management.System.business.abstracts;

import dev.patika.Library.Management.System.entities.Author;
import dev.patika.Library.Management.System.entities.BookBorrowing;
import org.springframework.data.domain.Page;

public interface IBookBorrowingService {
    BookBorrowing save(BookBorrowing bookBorrowing);
    BookBorrowing get(int id);
    Page<BookBorrowing> cursor(int page, int pageSie);
    BookBorrowing update(BookBorrowing bookBorrowing);
    boolean delete(int id);
}
