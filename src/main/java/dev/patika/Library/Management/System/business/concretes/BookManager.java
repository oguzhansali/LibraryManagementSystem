package dev.patika.Library.Management.System.business.concretes;

import dev.patika.Library.Management.System.business.abstracts.IBookService;
import dev.patika.Library.Management.System.core.exception.NotFoundException;
import dev.patika.Library.Management.System.core.utillies.Msg;
import dev.patika.Library.Management.System.dao.AuthorRepo;
import dev.patika.Library.Management.System.dao.BookRepo;
import dev.patika.Library.Management.System.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookManager implements IBookService {
    private final BookRepo bookRepo;
    private final AuthorRepo authorRepo;

    public BookManager(BookRepo bookRepo, AuthorRepo authorRepo) {
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
    }

    //Yeni bir kitap kaydeder
    @Override
    public Book save(Book book) {
        //Author nesnesini kontrol eder
        if (book.getAuthor()==null || book.getPublisher() == null|| book.getCategory()==null ||!this.authorRepo.existsById(book.getAuthor().getId())){
            throw  new NotFoundException(Msg.NOT_FOUND);
        }
        return bookRepo.save(book);
    }
    //Belirli bir hayvan bilgisi gelir
    @Override
    public Book get(int id) {
        return bookRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    //Book sayfa olarak gelir
    @Override
    public Page<Book> cursor(int page, int pageSie) {
        Pageable pageable = PageRequest.of(page, pageSie);
        return this.bookRepo.findAll(pageable);
    }

    //Belirli bir kitab bilgisini günceller
    @Override
    public Book update(Book book) {
        this.get(book.getId());
        return this.bookRepo.save(book);
    }

    @Override
    public boolean delete(int id) {
        //Belirli bir id ye sahip kitabı getirir ve siler
        Book book = this.get(id);
        this.bookRepo.delete(book);
        return true;
    }
}
