package dev.patika.Library.Management.System.dao;

import dev.patika.Library.Management.System.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author,Integer> {
}
