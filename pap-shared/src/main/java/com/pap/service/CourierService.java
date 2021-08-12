package com.pap.service;

import com.pap.config.Constants;
import com.pap.domain.Courier;
import com.pap.exception.InvalidPasswordException;
import com.pap.exception.NumberPhoneAlreadyUsedException;
import com.pap.repository.CourierRepository;
import com.pap.security.SecurityUtils;
import com.pap.service.dto.CourierDTO;
import com.pap.service.dto.CourierVM;
import io.netty.util.AsyncMapping;
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

    private boolean removeNonActivatedUser(Courier existingCourier) {
        if (existingCourier.isActivated()) {
            return false;
        }
        courierRepository.delete(existingCourier);
        courierRepository.flush();
        this.clearUserCaches(existingCourier);
        return true;
    }

    public Courier createCourier(CourierVM courierVM) {
        Courier courier = new Courier();
        courier.setPhone(courierVM.getPhone());
        courier.setFullName(courierVM.getFullName());
        if (courierVM.getEmail() != null) {
            courier.setEmail(courierVM.getEmail().toLowerCase());
        }
        courier.setAddress(courierVM.getAddress());
        courier.setSoCMND(courierVM.getSoCMND());
        courier.setImageFirstCCCD(courierVM.getImageFirstCMND());
        courier.setImageLastCMND(courierVM.getImageLastCMND());
        courier.setSoCCCD(courierVM.getSoCCCD());
        courier.setImageFirstCCCD(courierVM.getImageFirstCCCD());
        courier.setImageLastCMND(courierVM.getImageLastCCCD());
        courier.setDateCMND(courierVM.getDateCMND());
        courier.setBankNumber(courierVM.getBankNumber());
        courier.setNameBank(courierVM.getNameBank());
        courier.setFullNameBank(courierVM.getFullNameBank());
        courier.setBranchBank(courierVM.getBranchBank());
        courier.setAvatar(courierVM.getAvatar());
        courier.setLicensePlate(courierVM.getLicensePlate());
        courier.setImageGPLX(courierVM.getImageGPLX());
        courier.setImageCavet(courierVM.getImageCavet());
        courier.setImageMotorbike(courierVM.getImageMotorbike());
        String encryptedPassword = passwordEncoder.encode(courierVM.getPassword());
        courier.setPassword(encryptedPassword);
        courier.setActivated(true);
        courierRepository.save(courier);
        this.clearUserCaches(courier);
        log.debug("Created Information for Courier: {}", courier);
        return courier;
    }

    public Optional<CourierDTO> updateCourier(CourierDTO courierDTO) {
        return Optional.of(courierRepository
            .findById(courierDTO.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(courier -> {
                this.clearUserCaches(courier);
                courier.setPhone(courierDTO.getPhone());
                courier.setFullName(courierDTO.getFullName());
                if (courierDTO.getEmail() != null) {
                    courier.setEmail(courierDTO.getEmail().toLowerCase());
                }
                courier.setAddress(courierDTO.getAddress());
                courier.setSoCMND(courierDTO.getSoCMND());
                courier.setImageFirstCCCD(courierDTO.getImageFirstCMND());
                courier.setImageLastCMND(courierDTO.getImageLastCMND());
                courier.setSoCCCD(courierDTO.getSoCCCD());
                courier.setImageFirstCCCD(courierDTO.getImageFirstCCCD());
                courier.setImageLastCMND(courierDTO.getImageLastCCCD());
                courier.setDateCMND(courierDTO.getDateCMND());
                courier.setBankNumber(courierDTO.getBankNumber());
                courier.setNameBank(courierDTO.getNameBank());
                courier.setFullNameBank(courierDTO.getFullNameBank());
                courier.setBranchBank(courierDTO.getBranchBank());
                courier.setAvatar(courierDTO.getAvatar());
                courier.setLicensePlate(courierDTO.getLicensePlate());
                courier.setImageGPLX(courierDTO.getImageGPLX());
                courier.setImageCavet(courierDTO.getImageCavet());
                courier.setImageMotorbike(courierDTO.getImageMotorbike());
                courier.setActivated(courierDTO.isActivated());
                this.clearUserCaches(courier);
                log.debug("Changed Information for Courier: {}", courier);
                return courier;
            })
            .map(CourierDTO::new);
    }

    public void deleteAccountCourier(String phone) {
        courierRepository.findOneByPhone(phone).ifPresent(user -> {
            courierRepository.delete(user);
            this.clearUserCaches(user);
            log.debug("Deleted Courier: {}", user);
        });
    }

    public void updateUser(String email, String imageUrl) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(courierRepository::findOneByPhone)
            .ifPresent(user -> {
                if (email != null) {
                    user.setEmail(email.toLowerCase());
                }
                user.setAvatar(imageUrl);
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

    @Transactional(readOnly = true)
    public Optional<Courier> getCourierByPhone(String phone) {
        return courierRepository.findOneByPhone(phone);
    }
}
