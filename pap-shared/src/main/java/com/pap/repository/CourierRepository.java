package com.pap.repository;

import com.pap.domain.Courier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the {@link Courier} entity.
 */
@Repository
public interface CourierRepository extends JpaRepository<Courier, String> {

    String USERS_BY_PHONE_CACHE = "couriersByPhone";

    String USERS_BY_EMAIL_CACHE = "couriersByEmail";

/*    List<Courier> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);*/

    Optional<Courier> findOneByEmailIgnoreCase(String email);

    Optional<Courier> findOneByPhone(String phone);

    Page<Courier> findAllByPhoneNot(Pageable pageable, String phone);
}
