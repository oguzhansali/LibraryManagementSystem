package dev.patika.Library.Management.System.dao;

import dev.patika.Library.Management.System.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Integer> {
}
