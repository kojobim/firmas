package com.bim.seguridad.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Orden.class)
public abstract class Orden_ {

	public static volatile SingularAttribute<Orden, Usuario> llaveId;
	public static volatile SingularAttribute<Orden, String> numero;
	public static volatile SingularAttribute<Orden, Long> id;
	public static volatile SingularAttribute<Orden, String> firma;
	public static volatile SingularAttribute<Orden, Integer> llavaId;
	public static volatile SingularAttribute<Orden, String> primaryKey;

	public static final String LLAVE_ID = "llaveId";
	public static final String NUMERO = "numero";
	public static final String ID = "id";
	public static final String FIRMA = "firma";
	public static final String LLAVA_ID = "llavaId";
	public static final String PRIMARY_KEY = "primaryKey";

}

