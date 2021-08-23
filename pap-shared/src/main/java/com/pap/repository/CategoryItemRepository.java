package com.pap.repository;

import com.pap.domain.CategoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryItemRepository extends JpaRepository<CategoryItem, String> {
    Page<CategoryItem> findByRestaurantId(Pageable pageable, String restaurantId);
}
