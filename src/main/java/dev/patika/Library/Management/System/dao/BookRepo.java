package dev.patika.Library.Management.System.dao;

import dev.patika.Library.Management.System.entities.Author;
import dev.patika.Library.Management.System.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepo extends JpaRepository<Book,Integer> {
    List<Book> findByAuthorId(Integer authorId);
    void deleteByAuthorId(int authorId);
    void deleteByPublisherId(int publisherId);



}
