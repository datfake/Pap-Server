package com.pap.service;

import com.pap.domain.Item;
import com.pap.domain.OptionItem;
import com.pap.repository.ItemRepository;
import com.pap.repository.OptionItemRepository;
import com.pap.service.dto.OptionItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class OptionItemService {

    private final Logger log = LoggerFactory.getLogger(OptionItemService.class);

    private final ItemRepository itemRepository;

    private final OptionItemRepository optionItemRepository;


    public OptionItemService(ItemRepository itemRepository, OptionItemRepository optionItemRepository) {
        this.itemRepository = itemRepository;
        this.optionItemRepository = optionItemRepository;
    }

    public OptionItem createOptionItem(OptionItemDTO optionItemDTO) {
        OptionItem optionItem = new OptionItem();
        optionItem.setName(optionItemDTO.getName());
        optionItem.setRestaurantEmail(optionItemDTO.getRestaurantEmail());
        Optional<Item> item =  itemRepository.findById(optionItemDTO.getItemId());
        optionItem.setItem(item.get());
        optionItemRepository.save(optionItem);
        log.debug("Created Information for optionItem: {}", optionItem);
        return optionItem;
    }

    public Optional<OptionItemDTO> updateOptionItem(OptionItemDTO optionItemDTO) {
        return Optional.of(optionItemRepository
                .findById(optionItemDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(optionItem -> {
                    optionItem.setName(optionItemDTO.getName());
                    optionItem.setLastModifiedDate(LocalDateTime.now());
                    log.debug("Changed Information for optionItem: {}", optionItem);
                    return optionItem;
                })
                .map(OptionItemDTO::new);
    }
}
