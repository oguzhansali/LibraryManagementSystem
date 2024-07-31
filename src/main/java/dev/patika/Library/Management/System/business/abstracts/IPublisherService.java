package dev.patika.Library.Management.System.business.abstracts;

import dev.patika.Library.Management.System.entities.Book;
import dev.patika.Library.Management.System.entities.Publisher;
import org.springframework.data.domain.Page;

public interface IPublisherService {
    Publisher save(Publisher publisher);
    Publisher get(int id);
    Page<Publisher> cursor(int page, int pageSie);
    Publisher update(Publisher publisher);
    boolean delete(int id);
}
