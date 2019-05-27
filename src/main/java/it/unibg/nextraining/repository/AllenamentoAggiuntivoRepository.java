package it.unibg.nextraining.repository;

import it.unibg.nextraining.domain.AllenamentoAggiuntivo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the AllenamentoAggiuntivo entity.
 */
@Repository
public interface AllenamentoAggiuntivoRepository extends JpaRepository<AllenamentoAggiuntivo, Long> {

    @Query(value = "select distinct allenamentoAggiuntivo from AllenamentoAggiuntivo allenamentoAggiuntivo left join fetch allenamentoAggiuntivo.calciatores",
        countQuery = "select count(distinct allenamentoAggiuntivo) from AllenamentoAggiuntivo allenamentoAggiuntivo")
    Page<AllenamentoAggiuntivo> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct allenamentoAggiuntivo from AllenamentoAggiuntivo allenamentoAggiuntivo left join fetch allenamentoAggiuntivo.calciatores")
    List<AllenamentoAggiuntivo> findAllWithEagerRelationships();

    @Query("select allenamentoAggiuntivo from AllenamentoAggiuntivo allenamentoAggiuntivo left join fetch allenamentoAggiuntivo.calciatores where allenamentoAggiuntivo.id =:id")
    Optional<AllenamentoAggiuntivo> findOneWithEagerRelationships(@Param("id") Long id);

}
