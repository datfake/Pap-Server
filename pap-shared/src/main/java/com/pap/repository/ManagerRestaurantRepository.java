package com.pap.repository;

import com.pap.domain.ManagerRestaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the {@link ManagerRestaurant} entity.
 */
@Repository
public interface ManagerRestaurantRepository extends JpaRepository<ManagerRestaurant, String> {

    String USERS_BY_PHONE_CACHE = "managerRestaurantsByPhone";

    String USERS_BY_EMAIL_CACHE = "managerRestaurantsByEmail";

/*    List<ManagerRestaurant> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);*/

    Optional<ManagerRestaurant> findOneByEmailIgnoreCase(String email);

    Optional<ManagerRestaurant> findOneByPhone(String phone);

    Page<ManagerRestaurant> findAllByPhoneNot(Pageable pageable, String phone);
}
