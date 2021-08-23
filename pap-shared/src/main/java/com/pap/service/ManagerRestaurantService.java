package com.pap.service;

import com.pap.config.Constants;
import com.pap.domain.Category;
import com.pap.domain.ManagerRestaurant;
import com.pap.exception.InvalidPasswordException;
import com.pap.exception.NumberPhoneAlreadyUsedException;
import com.pap.repository.CategoryRepository;
import com.pap.repository.ManagerRestaurantRepository;
import com.pap.security.SecurityUtils;
import com.pap.service.dto.ManagerRestaurantDTO;
import com.pap.service.dto.RestaurantDTO;
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

@Service
@Transactional
public class ManagerRestaurantService {

    private final Logger log = LoggerFactory.getLogger(ManagerRestaurantService.class);

    private final ManagerRestaurantRepository managerRestaurantRepository;

    private final CategoryRepository categoryRepository;

    private final PasswordEncoder passwordEncoder;

    private final CacheManager cacheManager;

    public ManagerRestaurantService(ManagerRestaurantRepository managerRestaurantRepository, CategoryRepository categoryRepository, PasswordEncoder passwordEncoder, CacheManager cacheManager) {
        this.managerRestaurantRepository = managerRestaurantRepository;
        this.categoryRepository = categoryRepository;
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
        newManagerRestaurant.setAvatar(managerRestaurantDTO.getAvatar());
        newManagerRestaurant.setActivated(true);
        managerRestaurantRepository.save(newManagerRestaurant);
        this.clearUserCaches(newManagerRestaurant);
        log.debug("Created Information for ManagerRestaurant: {}", newManagerRestaurant);
        return newManagerRestaurant;
    }

    @Transactional(readOnly = true)
    public Page<ManagerRestaurantDTO> getAllManagedUsers(Pageable pageable) {
        return managerRestaurantRepository.findAllByPhoneNot(pageable, Constants.ANONYMOUS_USER).map(ManagerRestaurantDTO::new);
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

    public ManagerRestaurant createManagerRestaurant(ManagerRestaurantDTO managerRestaurantVM) {
        ManagerRestaurant managerRestaurant = new ManagerRestaurant();
        managerRestaurant.setPhone(managerRestaurantVM.getPhone());
        managerRestaurant.setFullName(managerRestaurantVM.getFullName());
        if (managerRestaurantVM.getEmail() != null) {
            managerRestaurant.setEmail(managerRestaurantVM.getEmail().toLowerCase());
        }
        managerRestaurant.setNameRestaurant(managerRestaurantVM.getNameRestaurant());
        managerRestaurant.setSummary(managerRestaurantVM.getSummary());
        managerRestaurant.setContent(managerRestaurantVM.getContent());
        managerRestaurant.setSoDKKD(managerRestaurantVM.getSoDKKD());
        managerRestaurant.setAddress(managerRestaurantVM.getAddress());
        managerRestaurant.setPartner(managerRestaurantVM.isPartner());
        managerRestaurant.setSharing(managerRestaurantVM.getSharing());
        managerRestaurant.setImageRestaurant(managerRestaurantVM.getImageRestaurant());
        managerRestaurant.setAvatar(managerRestaurantVM.getAvatar());
        managerRestaurant.setTypeBusiness(managerRestaurantVM.getTypeBusiness());
        managerRestaurant.setSoCMND(managerRestaurantVM.getSoCMND());
        managerRestaurant.setImageFirstCMND(managerRestaurantVM.getImageFirstCMND());
        managerRestaurant.setImageLastCMND(managerRestaurantVM.getImageLastCMND());
        managerRestaurant.setSoCCCD(managerRestaurantVM.getSoCCCD());
        managerRestaurant.setImageFirstCCCD(managerRestaurantVM.getImageFirstCCCD());
        managerRestaurant.setImageLastCCCD(managerRestaurantVM.getImageLastCCCD());
        managerRestaurant.setDateCMND(managerRestaurantVM.getDateCMND());
        managerRestaurant.setBankNumber(managerRestaurantVM.getBankNumber());
        managerRestaurant.setNameBank(managerRestaurantVM.getNameBank());
        managerRestaurant.setFullNameBank(managerRestaurantVM.getFullNameBank());
        managerRestaurant.setBranchBank(managerRestaurantVM.getBranchBank());
        managerRestaurant.setRoleManagerRestaurant(managerRestaurantVM.getRoleManagerRestaurant());
        String encryptedPassword = passwordEncoder.encode("12345678");
        managerRestaurant.setPassword(encryptedPassword);
        managerRestaurant.setActivated(true);
        managerRestaurantRepository.save(managerRestaurant);
        for(String code:managerRestaurantVM.getCategories()) {
            Category category = categoryRepository.findByCode(code);
            category.getRestaurants().add(managerRestaurant);
            categoryRepository.save(category);
        }
        this.clearUserCaches(managerRestaurant);
        log.debug("Created Information for ManagerRestaurant: {}", managerRestaurant);
        return managerRestaurant;
    }

    public Optional<ManagerRestaurantDTO> updateManagerRestaurant(ManagerRestaurantDTO managerRestaurantDTO) {
        return Optional.of(managerRestaurantRepository
            .findById(managerRestaurantDTO.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(managerRestaurant -> {
                this.clearUserCaches(managerRestaurant);
                managerRestaurant.setPhone(managerRestaurantDTO.getPhone());
                managerRestaurant.setFullName(managerRestaurantDTO.getFullName());
                if (managerRestaurantDTO.getEmail() != null) {
                    managerRestaurant.setEmail(managerRestaurantDTO.getEmail().toLowerCase());
                }
                managerRestaurant.setNameRestaurant(managerRestaurantDTO.getNameRestaurant());
                managerRestaurant.setSummary(managerRestaurantDTO.getSummary());
                managerRestaurant.setContent(managerRestaurantDTO.getContent());
                managerRestaurant.setSoDKKD(managerRestaurantDTO.getSoDKKD());
                managerRestaurant.setAddress(managerRestaurantDTO.getAddress());
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
                this.clearUserCaches(managerRestaurant);
                log.debug("Changed Information for ManagerRestaurant: {}", managerRestaurant);
                return managerRestaurant;
            })
            .map(ManagerRestaurantDTO::new);
    }

    public void deleteManagerRestaurant(String phone) {
        managerRestaurantRepository.findOneByPhone(phone).ifPresent(user -> {
            managerRestaurantRepository.delete(user);
            this.clearUserCaches(user);
            log.debug("Deleted ManagerRestaurant: {}", user);
        });
    }

    public void updateManagerRestaurant(String nameRestaurant, String summary, String content, String address, String email, String avatar) {
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
                user.setAvatar(avatar);
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

    public Optional<ManagerRestaurant> getAccountRestaurantByPhone(String phone) {
        return managerRestaurantRepository.findOneByPhone(phone);
    }

    @Transactional(readOnly = true)
    public Page<RestaurantDTO> getAllRestaurantsByCategoryId(Pageable pageable, Integer categoryId) {
        return managerRestaurantRepository.findByCategoriesId(pageable, categoryId).map(RestaurantDTO::new);
    }
}
