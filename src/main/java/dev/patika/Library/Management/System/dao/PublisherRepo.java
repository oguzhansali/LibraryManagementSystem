package dev.patika.Library.Management.System.dao;

import dev.patika.Library.Management.System.entities.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepo extends JpaRepository<Publisher,Integer> {
}
