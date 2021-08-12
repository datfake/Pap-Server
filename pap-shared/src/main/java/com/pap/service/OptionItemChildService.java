package com.pap.service;

import com.pap.domain.OptionItem;
import com.pap.domain.OptionItemChild;
import com.pap.repository.OptionItemChildRepository;
import com.pap.repository.OptionItemRepository;
import com.pap.service.dto.OptionItemChildDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class OptionItemChildService {

    private final Logger log = LoggerFactory.getLogger(OptionItemChildService.class);

    private final OptionItemChildRepository optionItemChildRepository;

    private final OptionItemRepository optionItemRepository;


    public OptionItemChildService(OptionItemChildRepository optionItemChildRepository, OptionItemRepository optionItemRepository) {
        this.optionItemChildRepository = optionItemChildRepository;
        this.optionItemRepository = optionItemRepository;
    }

    public OptionItemChild createOptionItemChild(OptionItemChildDTO optionItemChildDTO) {
        OptionItemChild optionItemChild = new OptionItemChild();
        optionItemChild.setName(optionItemChildDTO.getName());
        optionItemChild.setPrice(optionItemChildDTO.getPrice());
        optionItemChild.setRestaurantEmail(optionItemChildDTO.getRestaurantEmail());
        Optional<OptionItem> optionItem =  optionItemRepository.findById(optionItemChildDTO.getOptionItemId());
        optionItemChild.setOptionItem(optionItem.get());
        optionItemChildRepository.save(optionItemChild);
        log.debug("Created Information for optionItemChild: {}", optionItemChild);
        return optionItemChild;
    }

    public Optional<OptionItemChildDTO> updateOptionItemChild(OptionItemChildDTO optionItemChildDTO) {
        return Optional.of(optionItemChildRepository
                .findById(optionItemChildDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(optionItemChild -> {
                    optionItemChild.setName(optionItemChildDTO.getName());
                    optionItemChild.setPrice(optionItemChildDTO.getPrice());
                    optionItemChild.setLastModifiedDate(LocalDateTime.now());
                    log.debug("Changed Information for optionItemChild: {}", optionItemChild);
                    return optionItemChild;
                })
                .map(OptionItemChildDTO::new);
    }
}
