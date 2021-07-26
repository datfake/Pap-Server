package com.pap.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Customer.class)
public abstract class Customer_ extends com.pap.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Customer, String> phone;
	public static volatile SingularAttribute<Customer, String> imageUrl;
	public static volatile SingularAttribute<Customer, String> fullName;
	public static volatile SingularAttribute<Customer, String> id;
	public static volatile SingularAttribute<Customer, String> email;
	public static volatile SingularAttribute<Customer, Boolean> activated;

	public static final String PHONE = "phone";
	public static final String IMAGE_URL = "imageUrl";
	public static final String FULL_NAME = "fullName";
	public static final String ID = "id";
	public static final String EMAIL = "email";
	public static final String ACTIVATED = "activated";

}

