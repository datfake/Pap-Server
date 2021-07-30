package com.pap.service;

import com.pap.config.Constants;
import com.pap.domain.ManagerRestaurant;
import com.pap.exception.InvalidPasswordException;
import com.pap.exception.NumberPhoneAlreadyUsedException;
import com.pap.repository.ManagerRestaurantRepository;
import com.pap.security.SecurityUtils;
import com.pap.service.dto.ManagerRestaurantDTO;
import io.github.jhipster.security.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class ManagerRestaurantService {

    private final Logger log = LoggerFactory.getLogger(ManagerRestaurantService.class);

    private final ManagerRestaurantRepository managerRestaurantRepository;

    private final PasswordEncoder passwordEncoder;

    private final CacheManager cacheManager;

    public ManagerRestaurantService(ManagerRestaurantRepository managerRestaurantRepository, PasswordEncoder passwordEncoder, CacheManager cacheManager) {
        this.managerRestaurantRepository = managerRestaurantRepository;
        this.passwordEncoder = passwordEncoder;
        this.cacheManager = cacheManager;
    }

    public Optional<ManagerRestaurant> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        /*return managerRestaurantRepository.findOneByResetKey(key)
            .filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400)))
            .map(user -> {
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setResetKey(null);
                user.setResetDate(null);
                this.clearUserCaches(user);
                return user;
            });*/
        return null;
    }

    public Optional<ManagerRestaurant> requestPasswordReset(String phone) {
        return managerRestaurantRepository.findOneByPhone(phone)
            .filter(ManagerRestaurant::isActivated)
            .map(user -> {
                // reset pass mac dinh o day. t chua tim duoc phuong an phu hop
                this.clearUserCaches(user);
                return user;
            });
    }

    public ManagerRestaurant registerUser(ManagerRestaurantDTO managerRestaurantDTO, String password) {
        managerRestaurantRepository.findOneByPhone(managerRestaurantDTO.getPhone()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new NumberPhoneAlreadyUsedException();
            }
        });
        ManagerRestaurant newManagerRestaurant = new ManagerRestaurant();
        String encryptedPassword = passwordEncoder.encode(password);
        newManagerRestaurant.setPhone(managerRestaurantDTO.getPhone());
        // new ManagerRestaurant gets initially a generated password
        newManagerRestaurant.setPassword(encryptedPassword);
        newManagerRestaurant.setFullName(managerRestaurantDTO.getFullName());
        if (managerRestaurantDTO.getEmail() != null) {
            newManagerRestaurant.setEmail(managerRestaurantDTO.getEmail().toLowerCase());
        }
        newManagerRestaurant.setAddress(managerRestaurantDTO.getAddress());
        newManagerRestaurant.setContent(managerRestaurantDTO.getContent());
        newManagerRestaurant.setSummary(managerRestaurantDTO.getSummary());
        newManagerRestaurant.setSoDKKD(managerRestaurantDTO.getSoDKKD());
        newManagerRestaurant.setNameRestaurant(managerRestaurantDTO.getNameRestaurant());
        newManagerRestaurant.setImageUrl(managerRestaurantDTO.getImageUrl());
        newManagerRestaurant.setActivated(true);
        managerRestaurantRepository.save(newManagerRestaurant);
        this.clearUserCaches(newManagerRestaurant);
        log.debug("Created Information for ManagerRestaurant: {}", newManagerRestaurant);
        return newManagerRestaurant;
    }

    private boolean removeNonActivatedUser(ManagerRestaurant existingManagerRestaurant) {
        if (existingManagerRestaurant.isActivated()) {
            return false;
        }
        managerRestaurantRepository.delete(existingManagerRestaurant);
        managerRestaurantRepository.flush();
        this.clearUserCaches(existingManagerRestaurant);
        return true;
    }

    public ManagerRestaurant createUser(ManagerRestaurantDTO managerRestaurantDTO) {
        ManagerRestaurant managerRestaurant = new ManagerRestaurant();
        managerRestaurant.setPhone(managerRestaurantDTO.getPhone());
        managerRestaurant.setFullName(managerRestaurantDTO.getFullName());
        if (managerRestaurantDTO.getEmail() != null) {
            managerRestaurant.setEmail(managerRestaurantDTO.getEmail().toLowerCase());
        }
        managerRestaurant.setImageUrl(managerRestaurantDTO.getImageUrl());
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        managerRestaurant.setPassword(encryptedPassword);
        managerRestaurant.setActivated(true);
        managerRestaurantRepository.save(managerRestaurant);
        this.clearUserCaches(managerRestaurant);
        log.debug("Created Information for ManagerRestaurant: {}", managerRestaurant);
        return managerRestaurant;
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param managerRestaurantDTO user to update.
     * @return updated user.
     */
    public Optional<ManagerRestaurantDTO> updateUser(ManagerRestaurantDTO managerRestaurantDTO) {
        return Optional.of(managerRestaurantRepository
            .findById(managerRestaurantDTO.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(user -> {
                this.clearUserCaches(user);
                user.setPhone(managerRestaurantDTO.getPhone());
                user.setFullName(managerRestaurantDTO.getFullName());
                if (managerRestaurantDTO.getEmail() != null) {
                    user.setEmail(managerRestaurantDTO.getEmail().toLowerCase());
                }
                user.setImageUrl(managerRestaurantDTO.getImageUrl());
                user.setActivated(managerRestaurantDTO.isActivated());
                this.clearUserCaches(user);
                log.debug("Changed Information for ManagerRestaurant: {}", user);
                return user;
            })
            .map(ManagerRestaurantDTO::new);
    }

    public void deleteUser(String login) {
        managerRestaurantRepository.findOneByPhone(login).ifPresent(user -> {
            managerRestaurantRepository.delete(user);
            this.clearUserCaches(user);
            log.debug("Deleted ManagerRestaurant: {}", user);
        });
    }

    public void updateUser(String nameRestaurant, String summary, String content, String address, String email, String imageUrl) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(managerRestaurantRepository::findOneByPhone)
            .ifPresent(user -> {
                user.setNameRestaurant(nameRestaurant);
                user.setSummary(summary);
                user.setContent(content);
                user.setAddress(address);
                if (email != null) {
                    user.setEmail(email.toLowerCase());
                }
                user.setImageUrl(imageUrl);
                this.clearUserCaches(user);
                log.debug("Changed Information for ManagerRestaurant: {}", user);
            });
    }


    @Transactional
    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(managerRestaurantRepository::findOneByPhone)
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


    private void clearUserCaches(ManagerRestaurant managerRestaurant) {
        Objects.requireNonNull(cacheManager.getCache(ManagerRestaurantRepository.USERS_BY_PHONE_CACHE)).evict(managerRestaurant.getPhone());
        if (managerRestaurant.getEmail() != null) {
            Objects.requireNonNull(cacheManager.getCache(ManagerRestaurantRepository.USERS_BY_EMAIL_CACHE)).evict(managerRestaurant.getEmail());
        }
    }
}
