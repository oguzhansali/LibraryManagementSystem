package dev.patika.Library.Management.System.business.concretes;

import dev.patika.Library.Management.System.business.abstracts.ICategoryService;
import dev.patika.Library.Management.System.core.exception.NotFoundException;
import dev.patika.Library.Management.System.core.utillies.Msg;
import dev.patika.Library.Management.System.dao.BookRepo;
import dev.patika.Library.Management.System.dao.CategoryRepo;
import dev.patika.Library.Management.System.entities.Book;
import dev.patika.Library.Management.System.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryManager implements ICategoryService {
    private final CategoryRepo categoryRepo;
    private final BookRepo bookRepo;

    public CategoryManager(CategoryRepo categoryRepo, BookRepo bookRepo) {
        this.categoryRepo = categoryRepo;
        this.bookRepo = bookRepo;
    }

    @Override
    public Category save(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public Category get(int id) {
        return categoryRepo.findById(id).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public Page<Category> cursor(int page, int pageSie) {
        Pageable pageable = PageRequest.of(page, pageSie);
        return this.categoryRepo.findAll(pageable);
    }

    @Override
    public Category update(Category category) {
        this.get(category.getId());
        return this.categoryRepo.save(category);
    }


    @Override
    public boolean delete(int id) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
        categoryRepo.delete(category);
        return false;
    }
}
