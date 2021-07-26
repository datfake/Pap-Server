package com.pap.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Courier.class)
public abstract class Courier_ extends com.pap.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Courier, String> password;
	public static volatile SingularAttribute<Courier, String> phone;
	public static volatile SingularAttribute<Courier, String> imageUrl;
	public static volatile SingularAttribute<Courier, String> fullName;
	public static volatile SingularAttribute<Courier, String> id;
	public static volatile SingularAttribute<Courier, String> email;
	public static volatile SingularAttribute<Courier, Boolean> activated;

	public static final String PASSWORD = "password";
	public static final String PHONE = "phone";
	public static final String IMAGE_URL = "imageUrl";
	public static final String FULL_NAME = "fullName";
	public static final String ID = "id";
	public static final String EMAIL = "email";
	public static final String ACTIVATED = "activated";

}

