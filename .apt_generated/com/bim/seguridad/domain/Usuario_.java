package com.bim.seguridad.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Usuario.class)
public abstract class Usuario_ {

	public static volatile SingularAttribute<Usuario, Sesion> sesion;
	public static volatile SingularAttribute<Usuario, Llaves> llaves;
	public static volatile SingularAttribute<Usuario, String> contrasenia;
	public static volatile SingularAttribute<Usuario, Long> id;
	public static volatile SingularAttribute<Usuario, String> nombre;

	public static final String SESION = "sesion";
	public static final String LLAVES = "llaves";
	public static final String CONTRASENIA = "contrasenia";
	public static final String ID = "id";
	public static final String NOMBRE = "nombre";

}

