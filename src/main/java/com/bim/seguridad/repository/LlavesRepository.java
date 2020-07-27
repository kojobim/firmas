package com.bim.seguridad.repository;

import com.bim.seguridad.domain.Llaves;
import com.bim.seguridad.domain.Usuario;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Llaves entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LlavesRepository extends JpaRepository<Llaves, Long> {
	Llaves findByUsuario(Usuario usuario);
	
}
