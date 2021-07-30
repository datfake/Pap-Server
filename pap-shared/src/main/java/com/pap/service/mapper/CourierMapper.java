package com.pap.service.mapper;

import com.pap.domain.Courier;
import com.pap.service.dto.CourierDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Mapper for the entity {@link Courier} and its DTO called {@link CourierDTO}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class CourierMapper {

    public List<CourierDTO> usersToUserDTOs(List<Courier> couriers) {
        return couriers.stream()
            .filter(Objects::nonNull)
            .map(this::userToUserDTO)
            .collect(Collectors.toList());
    }

    public CourierDTO userToUserDTO(Courier courier) {
        return new CourierDTO(courier);
    }

    public List<Courier> userDTOsToUsers(List<CourierDTO> courierDTOS) {
        return courierDTOS.stream()
            .filter(Objects::nonNull)
            .map(this::userDTOToUser)
            .collect(Collectors.toList());
    }

    public Courier userDTOToUser(CourierDTO courierDTO) {
        if (courierDTO == null) {
            return null;
        } else {
            Courier courier = new Courier();
            courier.setId(courierDTO.getId());
            courier.setPhone(courierDTO.getPhone());
            courier.setEmail(courierDTO.getEmail());
            courier.setImageUrl(courierDTO.getImageUrl());
            courier.setActivated(courierDTO.isActivated());
            courier.setLicensePlate(courierDTO.getLicensePlate());
            courier.setSoCMND(courierDTO.getSoCMND());
            return courier;
        }
    }

    public Courier userFromId(String id) {
        if (id == null) {
            return null;
        }
        Courier courier = new Courier();
        courier.setId(id);
        return courier;
    }
}
