package dev.patika.Library.Management.System.business.concretes;

import dev.patika.Library.Management.System.business.abstracts.IAuthorService;
import dev.patika.Library.Management.System.core.exception.NotFoundException;
import dev.patika.Library.Management.System.core.utillies.Msg;
import dev.patika.Library.Management.System.dao.AuthorRepo;
import dev.patika.Library.Management.System.dao.BookRepo;
import dev.patika.Library.Management.System.entities.Author;
import dev.patika.Library.Management.System.entities.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorManager implements IAuthorService {
    private final AuthorRepo authorRepo;
    private final BookRepo bookRepo;

    public AuthorManager(AuthorRepo authorRepo,BookRepo bookRepo) {
        this.authorRepo=authorRepo;
        this.bookRepo=bookRepo;
    }

    @Override
    public Author save(Author author) {
        return authorRepo.save(author);
    }

    @Override
    public Author get(int id) {
        return authorRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public Page<Author> cursor(int page, int pageSie) {
        // Sayfalama için Pageable nesnesi oluşturur
        // ve müşteri verilerinin bir sayfasını getirir
        Pageable pageable = PageRequest.of(page, pageSie);
        return this.authorRepo.findAll(pageable);
    }

    @Override
    public Author update(Author author) {
        this.get(author.getId());
        return this.authorRepo.save(author);
    }

    @Transactional
    @Override
    public boolean delete(int id) {
       Author author = authorRepo.findById(id)
               .orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));

        bookRepo.deleteByAuthorId(id);
        authorRepo.delete(author);
        return true;
    }
}
