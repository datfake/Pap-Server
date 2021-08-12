package com.pap.service;

import com.pap.domain.CategoryItem;
import com.pap.domain.Item;
import com.pap.repository.CategoryItemRepository;
import com.pap.repository.ItemRepository;
import com.pap.service.dto.ItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class ItemService {

    private final Logger log = LoggerFactory.getLogger(ItemService.class);

    private final ItemRepository itemRepository;

    private final CategoryItemRepository categoryItemRepository;


    public ItemService(CategoryItemRepository categoryItemRepository, ItemRepository itemRepository) {
        this.categoryItemRepository = categoryItemRepository;
        this.itemRepository = itemRepository;
    }

    public Item createItem(ItemDTO itemDTO) {
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setSummary(itemDTO.getSummary());
        item.setContent(itemDTO.getContent());
        item.setImageUrl(itemDTO.getImageUrl());
        item.setPrice(itemDTO.getPrice());
        item.setQuantity(itemDTO.getQuantity());
        item.setCountOrdered(itemDTO.getCountOrdered());
        item.setRestaurantEmail(itemDTO.getRestaurantEmail());
        Optional<CategoryItem> categoryItem =  categoryItemRepository.findById(itemDTO.getCategoryItemId());
        item.setCategoryItem(categoryItem.get());
        itemRepository.save(item);
        log.debug("Created Information for Item: {}", item);
        return item;
    }

    public Optional<ItemDTO> updateItem(ItemDTO itemDTO) {
        return Optional.of(itemRepository
                .findById(itemDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(item -> {
                    item.setName(itemDTO.getName());
                    item.setSummary(itemDTO.getSummary());
                    item.setContent(itemDTO.getContent());
                    item.setImageUrl(itemDTO.getImageUrl());
                    item.setPrice(itemDTO.getPrice());
                    item.setQuantity(itemDTO.getQuantity());
                    item.setCountOrdered(itemDTO.getCountOrdered());
                    item.setLastModifiedDate(LocalDateTime.now());
                    log.debug("Changed Information for Item: {}", item);
                    return item;
                })
                .map(ItemDTO::new);
    }
}
