package com.pap.service.mapper;

import com.pap.domain.Customer;
import com.pap.service.dto.CustomerDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Mapper for the entity {@link Customer} and its DTO called {@link CustomerDTO}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class CustomerMapper {

    public List<CustomerDTO> usersToUserDTOs(List<Customer> customers) {
        return customers.stream()
            .filter(Objects::nonNull)
            .map(this::userToUserDTO)
            .collect(Collectors.toList());
    }

    public CustomerDTO userToUserDTO(Customer customer) {
        return new CustomerDTO(customer);
    }

    public List<Customer> userDTOsToUsers(List<CustomerDTO> customerDTOS) {
        return customerDTOS.stream()
            .filter(Objects::nonNull)
            .map(this::userDTOToUser)
            .collect(Collectors.toList());
    }

    public Customer userDTOToUser(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            return null;
        } else {
            Customer customer = new Customer();
            customer.setId(customerDTO.getId());
            customer.setPhone(customerDTO.getPhone());
            customer.setEmail(customerDTO.getEmail());
            customer.setAvatar(customerDTO.getAvatar());
            customer.setActivated(customerDTO.isActivated());
            return customer;
        }
    }

    public Customer userFromId(String id) {
        if (id == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }
}
