package com.pap.service.mapper;

import com.pap.domain.ManagerRestaurant;
import com.pap.service.dto.ManagerRestaurantDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
            managerRestaurant.setNameRestaurant(managerRestaurantDTO.getNameRestaurant());
            managerRestaurant.setSummary(managerRestaurantDTO.getSummary());
            managerRestaurant.setContent(managerRestaurantDTO.getContent());
            managerRestaurant.setSoDKKD(managerRestaurantDTO.getSoDKKD());
            managerRestaurant.setAddress(managerRestaurantDTO.getAddress());
            managerRestaurant.setRate(managerRestaurantDTO.getRate());
            managerRestaurant.setPartner(managerRestaurantDTO.isPartner());
            managerRestaurant.setSharing(managerRestaurantDTO.getSharing());
            managerRestaurant.setImageRestaurant(managerRestaurantDTO.getImageRestaurant());
            managerRestaurant.setAvatar(managerRestaurantDTO.getAvatar());
            managerRestaurant.setTypeBusiness(managerRestaurantDTO.getTypeBusiness());
            managerRestaurant.setSoCMND(managerRestaurantDTO.getSoCMND());
            managerRestaurant.setImageFirstCMND(managerRestaurantDTO.getImageFirstCMND());
            managerRestaurant.setImageLastCMND(managerRestaurantDTO.getImageLastCMND());
            managerRestaurant.setSoCCCD(managerRestaurantDTO.getSoCCCD());
            managerRestaurant.setImageFirstCCCD(managerRestaurantDTO.getImageFirstCCCD());
            managerRestaurant.setImageLastCCCD(managerRestaurantDTO.getImageLastCCCD());
            managerRestaurant.setDateCMND(managerRestaurantDTO.getDateCMND());
            managerRestaurant.setBankNumber(managerRestaurantDTO.getBankNumber());
            managerRestaurant.setNameBank(managerRestaurantDTO.getNameBank());
            managerRestaurant.setFullNameBank(managerRestaurantDTO.getFullNameBank());
            managerRestaurant.setBranchBank(managerRestaurantDTO.getBranchBank());
            managerRestaurant.setRoleManagerRestaurant(managerRestaurantDTO.getRoleManagerRestaurant());
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
