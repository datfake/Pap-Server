package com.pap.service;

import com.pap.domain.CategoryItem;
import com.pap.domain.ManagerRestaurant;
import com.pap.repository.CategoryItemRepository;
import com.pap.repository.ManagerRestaurantRepository;
import com.pap.service.dto.CategoryItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class CategoryItemService {

    private final Logger log = LoggerFactory.getLogger(CategoryItemService.class);

    private final CategoryItemRepository categoryItemRepository;

    private final ManagerRestaurantRepository managerRestaurantRepository;

    public CategoryItemService(CategoryItemRepository categoryItemRepository, ManagerRestaurantRepository managerRestaurantRepository) {
        this.categoryItemRepository = categoryItemRepository;
        this.managerRestaurantRepository = managerRestaurantRepository;
    }

    public CategoryItem createCategoryItem(CategoryItemDTO categoryItemDTO) {
        CategoryItem categoryItem = new CategoryItem();
        categoryItem.setName(categoryItemDTO.getName());
        Optional<ManagerRestaurant> restaurant =  managerRestaurantRepository.findOneByEmailIgnoreCase(categoryItemDTO.getRestaurantEmail());
        categoryItem.setRestaurant(restaurant.get());
        categoryItemRepository.save(categoryItem);
        log.debug("Created Information for CategoryItem: {}", categoryItem);
        return categoryItem;
    }

    public Optional<CategoryItemDTO> updateCategoryItem(CategoryItemDTO categoryItemDTO) {
        return Optional.of(categoryItemRepository
                .findById(categoryItemDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(categoryItem -> {
                    categoryItem.setName(categoryItemDTO.getName());
                    categoryItem.setLastModifiedDate(LocalDateTime.now());
                    log.debug("Changed Information for CategoryItem: {}", categoryItem);
                    return categoryItem;
                })
                .map(CategoryItemDTO::new);
    }
}
