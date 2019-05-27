package it.unibg.nextraining.repository;

import it.unibg.nextraining.domain.Allenamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Allenamento entity.
 */
@Repository
public interface AllenamentoRepository extends JpaRepository<Allenamento, Long> {

    @Query(value = "select distinct allenamento from Allenamento allenamento left join fetch allenamento.calciatores",
        countQuery = "select count(distinct allenamento) from Allenamento allenamento")
    Page<Allenamento> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct allenamento from Allenamento allenamento left join fetch allenamento.calciatores")
    List<Allenamento> findAllWithEagerRelationships();

    @Query("select allenamento from Allenamento allenamento left join fetch allenamento.calciatores where allenamento.id =:id")
    Optional<Allenamento> findOneWithEagerRelationships(@Param("id") Long id);

}
