package com.pap.web.rest;

import com.pap.config.Constants;
import com.pap.domain.ManagerRestaurant;
import com.pap.service.ManagerRestaurantService;
import com.pap.service.dto.ManagerRestaurantDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

@RestController
@RequestMapping("/api")
public class DataTestContoller {

    private final ManagerRestaurantService managerRestaurantService;

    public DataTestContoller(ManagerRestaurantService managerRestaurantService) {
        this.managerRestaurantService = managerRestaurantService;
    }

    @PostMapping("/add-data-test")
    public ResponseEntity getAllRestaurantByCategoryId(@PathVariable Integer categoryId) {

        ManagerRestaurant managerRestaurant1 = managerRestaurantService.createManagerRestaurant(new ManagerRestaurantDTO(
                null,"09865253634", "Nguyễn Duy Khánh", "khanhnguyen1025@gmail.com", "Cá chiên Mong A", "", "", "", "75 Mong A, Phu Vang", 3.5f, false, 10, true, "", true,
                "", Constants.TypeBusiness.CANHAN, "1873623634", "", "" ,"", "", "", LocalDate.now(), "6726632788754", "TP Bank", "NGUYEN DUY KHANH", "Hue", Constants.RoleManagerRestaurant.CHUCUAHANG,
            new HashSet<>(Arrays.asList("COM", "TRASUA")), "admin", LocalDateTime.now(), null, LocalDateTime.now())
        );

        ManagerRestaurant managerRestaurant2 = managerRestaurantService.createManagerRestaurant(new ManagerRestaurantDTO(
                null,"09865243634", "Nguyễn Công Hiển", "conghien@gmail.com", "Cháo lòng chợ láng", "", "", "", "37 Tây Thành, Phu Vang", 3.5f, false, 10, true, "", true,
                "", Constants.TypeBusiness.CANHAN, "1873623634", "", "" ,"", "", "", LocalDate.now(), "6726632788754", "TP Bank", "NGUYEN DUY KHANH", "Hue", Constants.RoleManagerRestaurant.CHUCUAHANG,
                new HashSet<>(Arrays.asList("COM", "TRASUA")), "admin", LocalDateTime.now(), null, LocalDateTime.now())
        );

        ManagerRestaurant managerRestaurant3 = managerRestaurantService.createManagerRestaurant(new ManagerRestaurantDTO(
                null,"09861253631", "Nguyễn Duy Khánh 2", "kanhnguyen1025@gmail.com", "Cá chiên Mong A 1", "", "", "", "75 Mong A, Phu Vang", 3.5f, false, 10, true, "", true,
                "", Constants.TypeBusiness.CANHAN, "1873623634", "", "" ,"", "", "", LocalDate.now(), "6726632788754", "TP Bank", "NGUYEN DUY KHANH", "Hue", Constants.RoleManagerRestaurant.CHUCUAHANG,
                new HashSet<>(Arrays.asList("COM")), "admin", LocalDateTime.now(), null, LocalDateTime.now())
        );

        ManagerRestaurant managerRestaurant4 = managerRestaurantService.createManagerRestaurant(new ManagerRestaurantDTO(
                null,"09863253631", "Nguyễn Duy Khánh 3", "hanhnguyen1025@gmail.com", "Cá chiên Mong A 2", "", "", "", "75 Mong A, Phu Vang", 3.5f, false, 10, true, "", true,
                "", Constants.TypeBusiness.CANHAN, "1873623634", "", "" ,"", "", "", LocalDate.now(), "6726632788754", "TP Bank", "NGUYEN DUY KHANH", "Hue", Constants.RoleManagerRestaurant.CHUCUAHANG,
                new HashSet<>(Arrays.asList("COM", "TRASUA")), "admin", LocalDateTime.now(), null, LocalDateTime.now())
        );

        return new ResponseEntity("Thêm data test thành công", HttpStatus.CREATED);
    }
}
