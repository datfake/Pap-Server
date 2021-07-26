package com.pap.repository;

import com.pap.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the {@link Customer} entity.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    String USERS_BY_PHONE_CACHE = "customersByPhone";

    String USERS_BY_EMAIL_CACHE = "customersByEmail";

/*    List<Customer> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);*/

    Optional<Customer> findOneByEmailIgnoreCase(String email);

    Optional<Customer> findOneByPhone(String phone);

    Page<Customer> findAllByPhoneNot(Pageable pageable, String phone);
}
