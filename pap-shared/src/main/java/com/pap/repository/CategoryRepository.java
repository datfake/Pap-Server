package com.pap.repository;

import com.pap.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Category} entity.
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findByCode(String code);
}
