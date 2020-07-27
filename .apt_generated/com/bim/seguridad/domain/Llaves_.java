package com.bim.seguridad.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Llaves.class)
public abstract class Llaves_ {

	public static volatile SingularAttribute<Llaves, String> publica;
	public static volatile SingularAttribute<Llaves, Usuario> usuario;
	public static volatile SingularAttribute<Llaves, Long> id;
	public static volatile SingularAttribute<Llaves, String> privada;

	public static final String PUBLICA = "publica";
	public static final String USUARIO = "usuario";
	public static final String ID = "id";
	public static final String PRIVADA = "privada";

}

