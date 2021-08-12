package com.pap.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String PHONE_REGEX = "(84|0[3|5|7|8|9])+([0-9]{8})\\b";

    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "en";
    public static final String ANONYMOUS_USER = "anonymoususer";

    public enum CourierStatus {
        OFFLINE, ONLINE, BUSY
    }

    public enum OrderStatus {
        DANGCHON, DAHUY, CHUACOSHIP, DANGLAM, DAGIAO, DANGGIAO
    }

    public enum TypeBusiness {
        CANHAN, HOKINHDOANH, DOANHNGHIEP
    }

    public enum RoleManagerRestaurant {
        CHUCUAHANG, GIAMDOC, QUANLY, NGUOIUYQUYEN
    }

    public enum TypeUser {
        ADMIN, QUANLYQUANAN, COURIER, CUSTOMER
    }

    private Constants() {
    }
}
