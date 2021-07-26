package com.pap.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ManagerRestaurant.class)
public abstract class ManagerRestaurant_ extends com.pap.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<ManagerRestaurant, String> password;
	public static volatile SingularAttribute<ManagerRestaurant, String> phone;
	public static volatile SingularAttribute<ManagerRestaurant, String> imageUrl;
	public static volatile SingularAttribute<ManagerRestaurant, String> fullName;
	public static volatile SingularAttribute<ManagerRestaurant, String> id;
	public static volatile SingularAttribute<ManagerRestaurant, String> email;
	public static volatile SingularAttribute<ManagerRestaurant, Boolean> activated;

	public static final String PASSWORD = "password";
	public static final String PHONE = "phone";
	public static final String IMAGE_URL = "imageUrl";
	public static final String FULL_NAME = "fullName";
	public static final String ID = "id";
	public static final String EMAIL = "email";
	public static final String ACTIVATED = "activated";

}

