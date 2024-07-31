package dev.patika.Library.Management.System.dao;

import dev.patika.Library.Management.System.entities.BookBorrowing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookBorrowingRepo extends JpaRepository<BookBorrowing,Integer> {
}
