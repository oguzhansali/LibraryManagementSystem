package dev.patika.Library.Management.System.business.concretes;

import dev.patika.Library.Management.System.business.abstracts.IPublisherService;
import dev.patika.Library.Management.System.core.exception.NotFoundException;
import dev.patika.Library.Management.System.core.utillies.Msg;
import dev.patika.Library.Management.System.dao.BookRepo;
import dev.patika.Library.Management.System.dao.PublisherRepo;
import dev.patika.Library.Management.System.entities.Publisher;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PublisherManager implements IPublisherService {
    private final PublisherRepo publisherRepo;
    private final BookRepo bookRepo;

    public PublisherManager(PublisherRepo publisherRepo, BookRepo bookRepo) {
        this.publisherRepo = publisherRepo;
        this.bookRepo = bookRepo;
    }

    @Override
    public Publisher save(Publisher publisher) {
        return publisherRepo.save(publisher);
    }

    @Override
    public Publisher get(int id) {
        return publisherRepo.findById(id).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public Page<Publisher> cursor(int page, int pageSie) {
        Pageable pageable = PageRequest.of(page, pageSie);
        return this.publisherRepo.findAll(pageable);
    }

    @Override
    public Publisher update(Publisher publisher) {
        this.get(publisher.getId());
        return this.publisherRepo.save(publisher);
    }

    @Transactional
    @Override
    public boolean delete(int id) {
        Publisher publisher = publisherRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
        bookRepo.deleteByPublisherId(id);
        publisherRepo.delete(publisher);
        return false;
    }
}
