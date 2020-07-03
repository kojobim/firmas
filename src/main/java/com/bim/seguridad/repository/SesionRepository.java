package com.bim.seguridad.repository;

import com.bim.seguridad.domain.Sesion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Sesion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {

}
