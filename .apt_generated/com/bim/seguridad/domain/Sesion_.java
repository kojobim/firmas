package com.bim.seguridad.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Sesion.class)
public abstract class Sesion_ {

	public static volatile SingularAttribute<Sesion, Usuario> usuario;
	public static volatile SingularAttribute<Sesion, Long> id;
	public static volatile SingularAttribute<Sesion, Boolean> activo;

	public static final String USUARIO = "usuario";
	public static final String ID = "id";
	public static final String ACTIVO = "activo";

}

