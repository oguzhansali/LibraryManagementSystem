package dev.patika.Library.Management.System.business.abstracts;

import dev.patika.Library.Management.System.entities.Category;
import dev.patika.Library.Management.System.entities.Publisher;
import org.springframework.data.domain.Page;

public interface ICategoryService {
    Category save(Category category);
    Category get(int id);
    Page<Category> cursor(int page, int pageSie);
    Category update(Category category);
    boolean delete(int id);
}
