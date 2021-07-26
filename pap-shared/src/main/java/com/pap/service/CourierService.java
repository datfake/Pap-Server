package com.pap.service;

import com.pap.config.Constants;
import com.pap.domain.Courier;
import com.pap.exception.InvalidPasswordException;
import com.pap.exception.NumberPhoneAlreadyUsedException;
import com.pap.repository.CourierRepository;
import com.pap.security.SecurityUtils;
import com.pap.service.dto.CourierDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class CourierService {

    private final Logger log = LoggerFactory.getLogger(CourierService.class);

    private final CourierRepository courierRepository;

    private final CacheManager cacheManager;

    private final PasswordEncoder passwordEncoder;

    public CourierService(CourierRepository courierRepository, CacheManager cacheManager, PasswordEncoder passwordEncoder) {
        this.courierRepository = courierRepository;
        this.cacheManager = cacheManager;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Courier> requestPasswordReset(String phone) {
        return courierRepository.findOneByPhone(phone)
            .filter(Courier::isActivated)
            .map(user -> {
                // reset pass mac dinh o day. t chua tim duoc phuong an phu hop
                this.clearUserCaches(user);
                return user;
            });
    }

    public Courier registerUser(CourierDTO courierDTO, String password) {
        courierRepository.findOneByPhone(courierDTO.getPhone()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new NumberPhoneAlreadyUsedException();
            }
        });
        Courier newCourier = new Courier();
        String encryptedPassword = passwordEncoder.encode(password);
        newCourier.setPhone(courierDTO.getPhone());
        // new ManagerRestaurant gets initially a generated password
        newCourier.setPassword(encryptedPassword);
        newCourier.setFullName(courierDTO.getFullName());
        if (courierDTO.getEmail() != null) {
            newCourier.setEmail(courierDTO.getEmail().toLowerCase());
        }
        newCourier.setImageUrl(courierDTO.getImageUrl());
        newCourier.setActivated(true);
        courierRepository.save(newCourier);
        this.clearUserCaches(newCourier);
        log.debug("Created Information for Courier: {}", newCourier);
        return newCourier;
    }

    private boolean removeNonActivatedUser(Courier existingCourier) {
        if (existingCourier.isActivated()) {
            return false;
        }
        courierRepository.delete(existingCourier);
        courierRepository.flush();
        this.clearUserCaches(existingCourier);
        return true;
    }

    public Courier createUser(CourierDTO courierDTO) {
        Courier courier = new Courier();
        courier.setPhone(courierDTO.getPhone());
        courier.setFullName(courierDTO.getFullName());
        if (courierDTO.getEmail() != null) {
            courier.setEmail(courierDTO.getEmail().toLowerCase());
        }
        courier.setImageUrl(courierDTO.getImageUrl());
        courier.setActivated(true);
        courierRepository.save(courier);
        this.clearUserCaches(courier);
        log.debug("Created Information for Courier: {}", courier);
        return courier;
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param courierDTO user to update.
     * @return updated user.
     */
    public Optional<CourierDTO> updateUser(CourierDTO courierDTO) {
        return Optional.of(courierRepository
            .findById(courierDTO.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(user -> {
                this.clearUserCaches(user);
                user.setPhone(courierDTO.getPhone());
                user.setFullName(courierDTO.getFullName());
                if (courierDTO.getEmail() != null) {
                    user.setEmail(courierDTO.getEmail().toLowerCase());
                }
                user.setImageUrl(courierDTO.getImageUrl());
                user.setActivated(courierDTO.isActivated());
                this.clearUserCaches(user);
                log.debug("Changed Information for Courier: {}", user);
                return user;
            })
            .map(CourierDTO::new);
    }

    public void deleteUser(String login) {
        courierRepository.findOneByPhone(login).ifPresent(user -> {
            courierRepository.delete(user);
            this.clearUserCaches(user);
            log.debug("Deleted Courier: {}", user);
        });
    }

    public void updateUser(String fullName, String email, String imageUrl) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(courierRepository::findOneByPhone)
            .ifPresent(user -> {
                user.setFullName(fullName);
                if (email != null) {
                    user.setEmail(email.toLowerCase());
                }
                user.setImageUrl(imageUrl);
                this.clearUserCaches(user);
                log.debug("Changed Information for Courier: {}", user);
            });
    }

    @Transactional(readOnly = true)
    public Page<CourierDTO> getAllManagedUsers(Pageable pageable) {
        return courierRepository.findAllByPhoneNot(pageable, Constants.ANONYMOUS_USER).map(CourierDTO::new);
    }

    @Transactional
    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(courierRepository::findOneByPhone)
            .ifPresent(user -> {
                String currentEncryptedPassword = user.getPassword();
                if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                    throw new InvalidPasswordException();
                }
                String encryptedPassword = passwordEncoder.encode(newPassword);
                user.setPassword(encryptedPassword);
                this.clearUserCaches(user);
                log.debug("Changed password for ManagerRestaurant: {}", user);
            });
    }

    private void clearUserCaches(Courier courier) {
        Objects.requireNonNull(cacheManager.getCache(CourierRepository.USERS_BY_PHONE_CACHE)).evict(courier.getPhone());
        if (courier.getEmail() != null) {
            Objects.requireNonNull(cacheManager.getCache(CourierRepository.USERS_BY_EMAIL_CACHE)).evict(courier.getEmail());
        }
    }
}
