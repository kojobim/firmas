package com.bim.seguridad.repository;

import com.bim.seguridad.domain.Orden;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Orden entity.
 */
@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {

    @Query(value = "select distinct orden from Orden orden left join fetch orden.llaves",
        countQuery = "select count(distinct orden) from Orden orden")
    Page<Orden> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct orden from Orden orden left join fetch orden.llaves")
    List<Orden> findAllWithEagerRelationships();

    @Query("select orden from Orden orden left join fetch orden.llaves where orden.id =:id")
    Optional<Orden> findOneWithEagerRelationships(@Param("id") Long id);

}
