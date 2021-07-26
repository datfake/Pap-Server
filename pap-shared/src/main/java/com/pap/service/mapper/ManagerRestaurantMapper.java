package com.pap.service.mapper;

import com.pap.domain.ManagerRestaurant;
import com.pap.service.dto.ManagerRestaurantDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Mapper for the entity {@link ManagerRestaurant} and its DTO called {@link ManagerRestaurantDTO}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class ManagerRestaurantMapper {

    public List<ManagerRestaurantDTO> usersToUserDTOs(List<ManagerRestaurant> managerRestaurants) {
        return managerRestaurants.stream()
            .filter(Objects::nonNull)
            .map(this::userToUserDTO)
            .collect(Collectors.toList());
    }

    public ManagerRestaurantDTO userToUserDTO(ManagerRestaurant managerRestaurant) {
        return new ManagerRestaurantDTO(managerRestaurant);
    }

    public List<ManagerRestaurant> userDTOsToUsers(List<ManagerRestaurantDTO> managerRestaurantDTOS) {
        return managerRestaurantDTOS.stream()
            .filter(Objects::nonNull)
            .map(this::userDTOToUser)
            .collect(Collectors.toList());
    }

    public ManagerRestaurant userDTOToUser(ManagerRestaurantDTO managerRestaurantDTO) {
        if (managerRestaurantDTO == null) {
            return null;
        } else {
            ManagerRestaurant managerRestaurant = new ManagerRestaurant();
            managerRestaurant.setId(managerRestaurantDTO.getId());
            managerRestaurant.setPhone(managerRestaurantDTO.getPhone());
            managerRestaurant.setEmail(managerRestaurantDTO.getEmail());
            managerRestaurant.setImageUrl(managerRestaurantDTO.getImageUrl());
            managerRestaurant.setActivated(managerRestaurantDTO.isActivated());
            return managerRestaurant;
        }
    }

    public ManagerRestaurant userFromId(String id) {
        if (id == null) {
            return null;
        }
        ManagerRestaurant managerRestaurant = new ManagerRestaurant();
        managerRestaurant.setId(id);
        return managerRestaurant;
    }
}
